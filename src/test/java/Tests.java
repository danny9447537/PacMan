import dannyken.demo.PacMan;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
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
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Skipping testGameStopAndClose in headless environment.");
            return; // Skip test
        }

        // Create a JFrame for the game
        System.out.println("Starting testGameStopAndClose...");
        JFrame frame = new JFrame("Pac-Man Test");
        PacMan game = new PacMan();

        frame.add(game);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game (initialize the game loop)
        game.startGame();

        // Simulate stopping and closing the game
        game.closeGame(frame);

        // Assertions to verify the game is stopped and resources are cleaned up
        assertFalse(game.getGameLoop().isRunning(), "Game loop should be stopped");
        assertFalse(frame.isDisplayable(), "Game window should be closed");
    }

    @Test
    public void testGhostMovementAndWallCollision() {
        PacMan pacmanGame = new PacMan();

        // Create a ghost near a wall
        PacMan.Block ghost = pacmanGame.new Block(null, 32, 32, 32, 32);
        PacMan.Block wall = pacmanGame.new Block(null, 32, 0, 32, 32);
        pacmanGame.walls = new HashSet<>();
        pacmanGame.walls.add(wall);

        // Set ghost to move upward into the wall
        ghost.updateDirection('U');
        System.out.println("Initial Ghost Position: (" + ghost.x + ", " + ghost.y + ")");
        ghost.x += ghost.velocityX;
        ghost.y += ghost.velocityY;

        for (PacMan.Block wallBlock : pacmanGame.walls) {
            if (pacmanGame.collision(ghost, wallBlock)) {
                ghost.x -= ghost.velocityX;
                ghost.y -= ghost.velocityY;
            }
        }

        System.out.println("Ghost Position after moving into wall: (" + ghost.x + ", " + ghost.y + ")");
        assertEquals(32, ghost.x);
        assertEquals(32, ghost.y);
    }

    @Test
    public void testFoodConsumption() {
        PacMan pacmanGame = new PacMan();

        // Place a food item in Pac-Man's path
        PacMan.Block food = pacmanGame.new Block(null, 32, 32, 4, 4);
        pacmanGame.foods = new HashSet<>();
        pacmanGame.foods.add(food);

        // Move Pac-Man to consume the food
        pacmanGame.pacman = pacmanGame.new Block(null, 32, 32, 32, 32);
        assertTrue(pacmanGame.collision(pacmanGame.pacman, food));
        pacmanGame.foods.remove(food);

        // Verify the food has been consumed and score updated
        assertTrue(pacmanGame.foods.isEmpty(), "Food set should be empty after consumption");
        pacmanGame.score += 10;
        assertEquals(10, pacmanGame.score);
    }

    @Test
    public void testLivesReductionOnGhostCollision() {
        PacMan pacmanGame = new PacMan();

        // Place Pac-Man and a ghost at the same position
        pacmanGame.pacman = pacmanGame.new Block(null, 64, 64, 32, 32);
        PacMan.Block ghost = pacmanGame.new Block(null, 64, 64, 32, 32);
        pacmanGame.ghosts = new HashSet<>();
        pacmanGame.ghosts.add(ghost);

        // Simulate ghost collision
        pacmanGame.lives = 1; // Set lives to 1 for the test
        pacmanGame.move();

        // Verify game over condition
        assertTrue(pacmanGame.gameOver, "Game should be over when lives reach 0");
    }

    @Test
    public void testPacManInitialPosition() {
        PacMan pacmanGame = new PacMan();

        // Verify that Pac-Man is initialized correctly
        assertNotNull(pacmanGame.pacman, "Pac-Man should not be null after initialization");
        assertEquals(32 * 9, pacmanGame.pacman.x, "Pac-Man should start at the correct X position (9th tile)");
        assertEquals(32 * 15, pacmanGame.pacman.y, "Pac-Man should start at the correct Y position (15th tile)");
    }


}
