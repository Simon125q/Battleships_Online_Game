import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class GameClient extends Thread {

    private String ip;
    private int port;
    private Socket socket = null;
    private InputStreamReader reader = null;
    private OutputStreamWriter writer = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;

    GameClient(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        initClient();
    }

    public void run() {
        String msg = "Ping";  
        try {
            sendMessage(msg);
            while (true) {
                receiveMessage();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            try {
                closeClient();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        
    }

    public void sendData(boolean shipsPlaced, boolean isAlive, boolean isHit, int TargetX, int TargetY) {

    }

    private void initClient() throws IOException {
        socket = new Socket(ip, port);

        reader = new InputStreamReader(socket.getInputStream());
        writer = new OutputStreamWriter(socket.getOutputStream());

        bufferedReader = new BufferedReader(reader);
        bufferedWriter = new BufferedWriter(writer);
    }

    private void closeClient() throws IOException {
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
        System.out.println("Server: " + recivedMsg);
        if (recivedMsg.equalsIgnoreCase("Pong")) {
            sendMessage("Ping");
        }
    }

}
