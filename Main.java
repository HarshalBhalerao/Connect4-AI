import javax.swing.*;

/**
 * Main class
 *
 * @author Harshal
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        // Set the title of the game
        frame.setTitle("Connect 4: Human vs AI using Minimax Algorithm");

        // Exit from the game by clicking the close button on top right
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the game to not resize
        frame.setResizable(false);

        // Add the game to the game frame and ensure that the window is sized properly
        // to fit the prefered size and its subcomponents
        frame.add(new Game());
        frame.pack();

        // Spawn the game frame in the middle of the computer screen
        frame.setLocationRelativeTo(null);

        // Make the game frame visible
        frame.setVisible(true);
    }
}