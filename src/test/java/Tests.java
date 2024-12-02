import dannyken.demo.PacMan;
import org.junit.jupiter.api.Test;

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
    public void testResetPositions() {
        PacMan pacmanGame = new PacMan();

        pacmanGame.pacman = pacmanGame.new Block(null, 100, 100, 32, 32);
        pacmanGame.pacman.startX = 50;
        pacmanGame.pacman.startY = 50;

        PacMan.Block ghost = pacmanGame.new Block(null, 200, 200, 32, 32);
        ghost.startX = 150;
        ghost.startY = 150;
        pacmanGame.ghosts = new HashSet<>();
        pacmanGame.ghosts.add(ghost);

        pacmanGame.resetPositions();

        System.out.println("Pac-Man position after reset: (" + pacmanGame.pacman.x + ", " + pacmanGame.pacman.y + ")");
        System.out.println("Ghost position after reset: (" + ghost.x + ", " + ghost.y + ")");
        assertEquals(50, pacmanGame.pacman.x);
        assertEquals(50, pacmanGame.pacman.y);
        assertEquals(150, ghost.x);
        assertEquals(150, ghost.y);
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
}
