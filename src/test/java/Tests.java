import dannyken.demo.PacMan;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    @Test
    public void testCollisionDetection() {
        PacMan pacmanGame = new PacMan();

        // Overlapping blocks
        PacMan.Block block1 = pacmanGame.new Block(null, 0, 0, 10, 10);
        PacMan.Block block2 = pacmanGame.new Block(null, 5, 5, 10, 10);

        boolean result1 = pacmanGame.collision(block1, block2);
        System.out.println("Testing collision for overlapping blocks: " + result1);
        assertTrue(result1); // Expecting collision to be true

        // Non-overlapping blocks
        PacMan.Block block3 = pacmanGame.new Block(null, 20, 20, 10, 10);
        boolean result2 = pacmanGame.collision(block1, block3);
        System.out.println("Testing collision for non-overlapping blocks: " + result2);
        assertFalse(result2); // Expecting collision to be false
    }

    @Test
    public void testUpdateDirectionWithCollisionHandling() {
        PacMan pacmanGame = new PacMan();

        // Create a block with no walls (simplified scenario)
        PacMan.Block block = pacmanGame.new Block(null, 32, 32, 32, 32);

        // Test moving up
        block.updateDirection('U');
        System.out.println("Direction after moving up: " + block.direction + ", Velocity: (" + block.velocityX + ", " + block.velocityY + ")");
        assertEquals('U', block.direction);

        // Add a wall directly above the block
        PacMan.Block wall = pacmanGame.new Block(null, 32, 0, 32, 32);
        pacmanGame.walls = new HashSet<>();
        pacmanGame.walls.add(wall);

        // Test moving up into the wall
        block.updateDirection('U');
        System.out.println("Direction after hitting a wall: " + block.direction + ", Velocity: (" + block.velocityX + ", " + block.velocityY + ")");
        assertEquals('U', block.direction); // Should revert back to the previous direction

        // Test moving down (valid move)
        block.updateDirection('D');
        System.out.println("Direction after moving down: " + block.direction + ", Velocity: (" + block.velocityX + ", " + block.velocityY + ")");
        assertEquals('D', block.direction); // Should update since there's no collision
    }

    @Test
    public void testGameStopAndClose() {
        System.out.println("Starting testGameStopAndClose...");

        // Create a JFrame for the game
        System.out.println("Creating JFrame for the game...");
        JFrame frame = new JFrame("Pac-Man Test");
        PacMan game = new PacMan();

        frame.add(game);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        System.out.println("Game window created and made visible.");

        // Start the game (initialize the game loop)
        System.out.println("Starting the game...");
        game.startGame();
        System.out.println("Game loop should now be running: " + game.getGameLoop().isRunning());

        // Simulate stopping and closing the game
        System.out.println("Stopping and closing the game...");
        game.closeGame(frame);

        // Assertions to verify the game is stopped and resources are cleaned up
        boolean isGameLoopRunning = game.getGameLoop().isRunning();
        System.out.println("Game loop running status after closeGame: " + isGameLoopRunning);

        boolean isFrameDisplayable = frame.isDisplayable();
        System.out.println("Frame displayable status after closeGame: " + isFrameDisplayable);

        assertFalse(isGameLoopRunning, "Game loop should be stopped");
        assertFalse(isFrameDisplayable, "Game window should be closed");

        System.out.println("testGameStopAndClose completed.");
    }


}
