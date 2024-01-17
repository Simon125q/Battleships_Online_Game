import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends Thread {

    private int port;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private InputStreamReader reader = null;
    private OutputStreamWriter writer = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;

    GameServer(int port) throws IOException {
        this.port = port;
        
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
                ioe.printStackTrace();
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

    public void sendData(boolean shipsPlaced, boolean isAlive, boolean isHit, int TargetX, int TargetY) {

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
        System.out.println("Client: " + recivedMsg);
        if (recivedMsg.equalsIgnoreCase("Ping")) {
            sendMessage("Pong");
        }
    }
}
