package interfaces.websocket;

import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebSocketServer {

    public static void runServer(int port){
        Server server = new Server("localhost", port, "/", NormalEndpoint.class);

        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true){

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            server.stop();
        }
    }


}
