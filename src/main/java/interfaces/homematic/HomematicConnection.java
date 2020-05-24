package interfaces.homematic;

import com.google.gson.Gson;
import logger.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HomematicConnection {

    Gson gson = new Gson();
    String sessionID;

    HttpClient client;

    URI uri;

    public HomematicConnection(String ip){
        uri = URI.create("http://" + ip + "/api/homematic.cgi");
        init();
    }

    public HomematicConnection(URI uri){
        this.uri = uri;
        init();
    }

    private void init(){
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public Map request(String req){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(req))
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return gson.fromJson(response.body(), Map.class);
        }catch (InterruptedException e){
            Logger.logError("HomematicConnection", "The program got interrupted by waiting of the " +
                    "response from the Server");
            e.printStackTrace();
        }catch (IOException e){
            Logger.logError("HomemticConnection", "IOException occurred when the program tried to " +
                    "connect to the server");
            e.printStackTrace();
        }
        return null;
    }


    public Map request(String method, Map<String, Object> params){
        Map<String, Object> request = new HashMap<>();
        request.put("method", method);
        request.put("id", UUID.randomUUID().hashCode());
        request.put("params", params);
        return request(gson.toJson(request));
    }

    public void login(String name, String password){
        Map<String,Object> params = new HashMap<>();
        params.put("username", name);
        params.put("password", password);
        try {
            sessionID = request("Session.login", params).get("result").toString();
            Logger.log("HomematicConnection", "Info", "Logged in successfully. ID: " + sessionID);
        }catch (NullPointerException e){
            Logger.logError( "HomematicConnection", "NullPointerException occurred on login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void logout(){
        if (sessionID == null) return;
        Map<String,Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        try {
            sessionID = request("Session.logout", params).get("result").toString();
            sessionID = null;
            Logger.log("HomematicConnection", "Info", "Logged out successfully.");
        }catch (NullPointerException e){
            Logger.logError( "HomematicConnection", "NullPointerException occurred on logout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List listDevices(){
        if (sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        return (List) request("Device.listAll", params).get("result");
    }

    public Map getEvents(){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        return request("Event.poll", params);
    }

    public Map getRooms(){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        return request("Room.getAll", params);
    }

    public Map getRoomIds(){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        return request("Room.listAll", params);
    }

    public Map getChannelValue(int channelId){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        params.put("id", channelId);
        return request("Channel.getValue", params);
    }

    public Map getProgramIds(int channelId){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        params.put("id", channelId);
        return request("Channel.listProgramIds", params);
    }

    public Map getProgramInfo(int programId){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        params.put("id", programId);
        return request("Program.get", params);
    }

    public Map listProgramInfo(){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        return request("Program.getAll", params);
    }

    public Map executeProgram(int programId){
        if(sessionID == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("_session_id_", sessionID);
        params.put("id", programId);
        return request("Program.execute", params);
    }


}
