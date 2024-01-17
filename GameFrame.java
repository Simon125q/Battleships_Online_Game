import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame implements KeyListener, Settings {

    public static String NAME = "BATTLESHIPS";

    private Scanner scanner = new Scanner(System.in);

    private GameServer server;
    private GameClient client;
    private String ip = "localhost";
    private int port = 1331;
    private boolean isServer;
    
    public boolean myTurn;

    GamePanel gamePanel;

    public GameFrame() {
        super(NAME);
        try {
            int serverChoice = serverDecisionMessage();
            //getIpAndPort();
            initConnection(serverChoice);
            
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);

            gamePanel = new GamePanel();
            getContentPane().add(gamePanel);

            addKeyListener(this);
            pack();
            setVisible(true);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        GameFrame game = new GameFrame();
    }

    private int serverDecisionMessage() {
        String[] options = new String[] {"Join Game", "Create Server"};
        String message = "Select if you want to join already existing server or create your own";
        int serverChoice = JOptionPane.showOptionDialog(null, message,
                "Server choice",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);   
        System.out.println(serverChoice);
        return serverChoice;
    }

    private void getIpAndPort() {
        System.out.println("Please input the IP: ");
		ip = scanner.nextLine();
		System.out.println("Please input the port: ");
		port = scanner.nextInt();
        while (port < 1 || port > 65535) {
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}
    }

    private void initConnection(int serverChoice) throws IOException{
        if (serverChoice == JOIN) {
            client = new GameClient(ip, port);
            client.start();
            isServer = false;
            myTurn = false;
        }
        else {
            server = new GameServer(port);
            server.start();
            isServer = true;
            myTurn = true;
        }
    }

    public void sendData(boolean shipsPlaced, boolean isAlive, boolean isHit, int TargetX, int TargetY) {
        if (isServer) {
            server.sendData(shipsPlaced, isAlive, isHit, TargetX, TargetY);
        }
        else {
            client.sendData(shipsPlaced, isAlive, isHit, TargetX, TargetY);
        }
        myTurn = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.handleKeyInput(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}