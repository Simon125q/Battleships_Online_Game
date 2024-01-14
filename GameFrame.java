import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements KeyListener{

    public static String NAME = "BATTLESHIPS";

    GamePanel gamePanel;

    public GameFrame() {
        super(NAME);
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

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.handleKeyInput(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}