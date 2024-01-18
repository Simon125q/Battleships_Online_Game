import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame implements KeyListener, Settings {

    private Scanner scanner = new Scanner(System.in);
    private GameServer server;
    private GameClient client;
    private String ip = "localhost";
    private int port = 1331;
    private boolean isServer;

    public Position lastShot = null;
    public boolean myTurn;
    public boolean opponentPlacedShips = false;

    private GamePanel gamePanel;

    public GameFrame() {
        super(NAME);
        try {
            serverDecisionMessage();

            getIpAndPort();
            initConnection();
            
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);

            gamePanel = new GamePanel(this);
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

    private void serverDecisionMessage() {
        String[] options = new String[] {"Join Game", "Create Server"};
        String message = "Select if you want to join already existing server or create your own";
        int serverChoice = JOptionPane.showOptionDialog(null, message,
                "Server choice",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);   
    
        if (serverChoice == JOIN) {
            isServer = false;
        }
        else {
            isServer = true;
        }
    }

    private void getIpAndPort() {
        if (!isServer) {
            System.out.println("Please input the IP: ");
            ip = scanner.nextLine();
        }
		System.out.println("Please input the port: ");
		port = scanner.nextInt();
        while (port < 1 || port > 65535) {
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}
    }

    private void initConnection() throws IOException{
        if (!isServer) {
            client = new GameClient(ip, port, this);
            client.start();
            isServer = false;
            myTurn = false;
        }
        else {
            server = new GameServer(port, this);
            server.start();
            isServer = true;
            myTurn = true;
        }
    }

    public void sendData(boolean shipsPlaced, boolean isAlive, boolean isHit, int targetX, int targetY) {
        if (isServer) {
            server.sendData(shipsPlaced, isAlive, isHit, targetX, targetY);
        }
        else {
            client.sendData(shipsPlaced, isAlive, isHit, targetX, targetY);
        }
    }

    public void getOpponentUpdate(boolean shipsPlaced, boolean isAlive, boolean isHit, int targetX, int targetY) {
        boolean didHeHit;
        if (shipsPlaced && !opponentPlacedShips) {
            opponentPlacedShips = true;
            if (gamePanel.gamePhase == SHOOTING && myTurn) {
                gamePanel.infoBar.update(MY_TURN);
                gamePanel.repaint();
            }
            
        }
        else if (targetX != -1 && targetY != -1){
            didHeHit = gamePanel.opponentFired(targetX, targetY);
            sendData(true, true, didHeHit, -1, -1);
            myTurn = true;
            gamePanel.infoBar.update(MY_TURN);
        }
        else {
            if (!isAlive) {
                displayEndScreen("Congratulations! You Won");
            }
            else if (lastShot != null)
                gamePanel.checkMyShot(lastShot, isHit);
        }
        //System.out.println(shipsPlaced + " " + isAlive + " " + isHit + " " + targetX + " " + targetY);
    }

    public void displayGameLost() {
        displayEndScreen("Game Over!");
    }

    private void displayEndScreen(String msg) {
        String[] options = new String[] {"Restart", "Exit"};
    
        int choice = JOptionPane.showOptionDialog(null, msg,
                "Game ended",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);   
        if (choice == 0) {
            gamePanel.reset();
            lastShot = null;
        }
        else if (choice == 1) {
            System.exit(1);
        }
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