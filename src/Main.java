import Display.GamePanel;

import javax.swing.*;

/**
 * This class runs the Program
 */
public class Main {
    static JFrame window;
    static GamePanel gp;

    public Main() {

    }

    /**
     * main method to run the GUI
     * @param args
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Farming Simulator");
        window.setResizable(false);
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gp.startGameThread();

    }
}
