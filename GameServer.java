import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer extends Thread {

    private int port;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private InputStreamReader reader = null;
    private OutputStreamWriter writer = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private GameFrame game;

    GameServer(int port, GameFrame game) throws IOException {
        this.port = port;
        this.game = game;
    }

    public void run() {
        while (true) {
            try {
                initServer();

                while (true) {
                    receiveMessage();
                }
                
            }
            catch (IOException ioe) {
                //ioe.printStackTrace();
                System.out.println("Client disconnected");
            }
            finally {
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                    closeServer();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void sendData(boolean shipsPlaced, boolean isAlive, boolean isHit, int targetX, int targetY) {
        String dataToSend = shipsPlaced + "_" + isAlive + "_" + isHit + "_" + targetX + "_" + targetY;
        try {
            sendMessage(dataToSend);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void initServer() throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();

        reader = new InputStreamReader(socket.getInputStream());
        writer = new OutputStreamWriter(socket.getOutputStream());

        bufferedReader = new BufferedReader(reader);
        bufferedWriter = new BufferedWriter(writer);
    }

    private void closeServer() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (reader != null) {
            reader.close();
        }
        if (writer != null) {
            writer.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }

    private void sendMessage(String msgToSend) throws IOException {
        bufferedWriter.write(msgToSend);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    private void receiveMessage() throws IOException{
        String recivedMsg = bufferedReader.readLine();
        
        if (recivedMsg.equalsIgnoreCase("Connect")) {
            System.out.println("Client connected");
            sendMessage("Connected");
        }
        else {
            System.out.println(recivedMsg);
            String[] msgData = recivedMsg.split("_");
            
            boolean shipsPlaced = Boolean.parseBoolean(msgData[0]);
            boolean isAlive = Boolean.parseBoolean(msgData[1]);
            boolean isHit = Boolean.parseBoolean(msgData[2]);
            int targetX = Integer.parseInt(msgData[3]);
            int targetY = Integer.parseInt(msgData[4]);
            
            game.getOpponentUpdate(shipsPlaced, isAlive, isHit, targetX, targetY);
        }
    }
}
