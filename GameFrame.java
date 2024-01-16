import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements KeyListener{

    public static String NAME = "BATTLESHIPS";

    private Scanner scanner = new Scanner(System.in);
    private String ip;
    private int port;

    GamePanel gamePanel;

    public GameFrame() {
        super(NAME);

        getIpAndPort();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        getContentPane().add(gamePanel);

        addKeyListener(this);
        pack();
        setVisible(true);
    }

    public static void main(String[] args)
    {
        GameFrame game = new GameFrame();
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

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.handleKeyInput(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}