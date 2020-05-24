package interfaces.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import logger.Logger;
import pythonProgramms.FritzBox;
import pythonProgramms.Sonos;
import fritzBox.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/")
public class NormalEndpoint {

    Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session){
        System.out.println("logged in");
    }

    @OnClose
    public void onClose(Session session, CloseReason reason){
        System.out.println("Session " + session.getId() + " closed caused by " + reason);
    }

    @OnMessage
    public String onMessage(String message, Session session){

        Map<String, String> errorMap = new HashMap<>();

        Map<String, Object> msg;
        System.out.println(message);
        try{
            msg = gson.fromJson(message, Map.class);
        }catch (JsonSyntaxException e){
            errorMap.put("error", "You have a mistake in your JSON-syntax");
            return gson.toJson(errorMap);
        }





        if(msg.containsKey("service")){
            System.out.println("service");
            return service(msg);
        }
        if(msg.containsKey("action")){
            System.out.println("action");
            return action(msg);
        }

        System.out.println("Error");

        errorMap.put("error", "you must specify either an action or a service");
        return gson.toJson(errorMap);
    }

    private String action(Map received){

        Map<String, Object> ret = new HashMap<>();

        if(!(received.get("action") instanceof String)){
            ret.put("error", "action must be a string");
            ret.put("state", false);
            return gson.toJson(ret);
        }

        String action = received.get("action").toString();

        if(action.equals("exit")){
            //System.exit(1);
            ret.put("error", "that action isn't implementet yet");
            ret.put("state", false);
            return gson.toJson(ret);
        }

        ret.put("state", false);
        ret.put("error", "unknown action");
        return gson.toJson(ret);
    }

    private String service(Map received){

        Map<String, String> errorMap = new HashMap<>();

        //errors
        if(!(received.get("service") instanceof String)){
            errorMap.put("error", "service must be a string");
            return gson.toJson(errorMap);
        }
        if(!received.containsKey("data") || !(received.get("data") instanceof Map)){
            errorMap.put("error", "the JSON must contain a Map/dict with the identifier data");
            return gson.toJson(errorMap);
        }
        if(!((Map) received.get("data")).containsKey("action") || !(((Map) received.get("data")).get("action") instanceof String)) {
            errorMap.put("error", "data must contain a string action");
            return gson.toJson(errorMap);
        }


        // After checked on general errors
        String cmd = received.get("service").toString();
        Map<String, Object> data = (Map<String, Object>) received.get("data");

        if(cmd.equals("sonos")){
            return sonos(data);
        }
        if(cmd.equalsIgnoreCase("fritzbox")){
            return fritzBox(data);
        }

        return "null";
    }


    private String sonos(Map<String, Object> data){
        String action = data.get("action").toString();
        Sonos sonos = new Sonos();

        Map<String, Object> ret = new HashMap<>();

        // without Parameter
        if(action.equals("get_volume")){
            ret.put("state", true);
            ret.put("volume", sonos.getVolume());
            return gson.toJson(ret);
        }

        //proves if the parameter volume is send
        if(!data.containsKey("volume") || !(data.get("volume") instanceof Double)){
            ret.put("error", "data must contain an integer volume");
            System.out.println(data.get("volume"));
            return gson.toJson(ret);
        }

        //TODO timeout setzen?

        int volume = ((Double) data.get("volume")).intValue();

        // commands with volume
        if(action.equals("louder")){
            sonos.louder(volume);
            ret.put("state", true);
        }
        if(action.equals("quieter")){
            sonos.quieter(volume);
            ret.put("state", true);
        }
        if (action.equals("set_volume")){
            sonos.setVolume(volume);
            ret.put("state", true);
        }

        if(ret.containsKey("state")){
            volume = sonos.getVolume();
            Logger.log("WebSocket.Sonos", "Info", "New Volume: " + volume);
            ret.put("volume", volume);
        }else {
            ret.put("state", false);
            ret.put("error", "unknown action for the service sonos");
        }



        return gson.toJson(ret);
    }


    private String fritzBox(Map<String, Object> data){
        String action = data.get("action").toString();
        FritzBox fritzBox = new FritzBox();

        Map<String, Object> ret = new HashMap<>();

        if(action.equalsIgnoreCase("get_devices")){
            if(FritzBox.storedDevices == null){
                fritzBox.getKnownDevices();
            }
            ret.put("state", true);
            ret.put("devices", Collections.singletonList(FritzBox.storedDevices));
        }
        if(action.equalsIgnoreCase("get_online_devices")){
            if(FritzBox.storedDevices == null){
                fritzBox.getKnownDevices();
            }
            ret.put("state", true);
            ret.put("devices", fritzBox.getOnlineDevices((FritzBoxDevice[]) FritzBox.storedDevices.toArray()));
        }
        if(action.equalsIgnoreCase("get_state")){
            if(FritzBox.storedState == null){
                ret.put("fritzStates", Arrays.asList(fritzBox.getWLanState()));
            }else {
                ret.put("fritzStates", Collections.singletonList(FritzBox.storedState));
            }
            ret.put("state", true);
        }

        if(action.equalsIgnoreCase("reload_devices")){
            ret.put("state", true);
            ret.put("devices", Arrays.asList(fritzBox.getKnownDevices()));
        }
        if(action.equalsIgnoreCase("reload_online_devices")){
            ret.put("state", true);
            ret.put("devices", Arrays.asList(fritzBox.getOnlineDevices(fritzBox.getKnownDevices())));
        }
        if(action.equalsIgnoreCase("reload_state")){
            ret.put("state", true);
            ret.put("fritzStates", Arrays.asList(fritzBox.getWLanState()));
        }

        if(!ret.containsKey("state")){
            ret.put("state", false);
            ret.put("error", "unknow action for service fritzbox");
        }

        return gson.toJson(ret);
    }


}
