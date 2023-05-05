import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Node class
 *
 * @author Harshal
 */
public class Node extends JButton implements ActionListener {

    // Variables
    private int row;
    private int col;
    private Chips chips;
    private Game game;

    /**
     * Node constructor
     *
     * @param row
     * @param col
     */
    public Node(int row, int col, Game game) {
        this.row = row;
        this.col = col;
        this.game = game;

        //Setting the initial JButton node to white and don't set it to focusable
        setBackground(Color.WHITE);
        setOpaque(true);
        setFocusable(false);
        addActionListener(this);

        //  Ensuring it works cross-platform.
        try {
            UIManager.setLookAndFeel((UIManager.getCrossPlatformLookAndFeelClassName()));
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    // Getters and Setters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Chips getChips() {
        return chips;
    }

    public void setChips(Chips chips) {
        // If the chip is a black chip then we use the black circle image
        if (chips == Chips.BLACKCHIP) {
            try {
                setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/BlackCircle.png"))));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        // If the chip is a white chip then we use the white circle image
        if (chips == Chips.WHITECHIP) {
            try {
                setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/WhiteCircle.png"))));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        this.chips = chips;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // Whenever the user places a chip, we go through a few conditions and then make the AI play.
        // If the game is executing and if it is not a tie then the player can play
        if (game.executing && !game.tie) {
            // If the AI hasn't won and if the column the user is trying to add chip to has an empty row then insert chip.
            if (!game.aiWins && game.isEmptyRow(game.getNodeArray(), this.col)) {
                game.insertAtLowestRow(game.getNodeArray(), this.col, Chips.WHITECHIP);
            }
            // Once the White chip is added, then check if the player has won. If not and if it is the AI's turn then
            // the AI can make a move.
            if (!game.playerWins && !game.playerTurn) {
                game.nextTurn();
            }
        }
    }
}
