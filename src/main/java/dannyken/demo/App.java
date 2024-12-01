package dannyken.demo;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int rowCount = 21;
        int colCount = 19;
        int tileSize = 32;
        int boardWidth = colCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame();
        frame.setTitle("Pac Man  // Ver 1.1");
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);
    }
    /*
    This is a test message for jenkins automation
     */
    /*
    This is a second test message for jenkins automation.
     */
    /*
    this is a third test message for jenkins automation.
     */
}