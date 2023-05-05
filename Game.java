import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Game class
 *
 * @author Harshal
 */
public class Game extends JPanel {

    // Board should have 7 columns and 6 rows
    public static int row = 6;
    public static int col = 7;

    // Size of each cell
    public static int cellSize = 100;

    // Calculate the width and height of the board
    public static int width = cellSize * col;
    public static int height = cellSize * row;

    // Node array
    public static Node[][] node = new Node[row][col];

    // Get random numbers
    public Random random;

    // Keep track of the number of available spaces.
    public int availableSpaces;

    // Boolean variable executing to keep track of whether the program is getting executed.
    public boolean executing;

    // Boolean variables to check the state of the gameplay
    public boolean playerWins;
    public boolean aiWins;
    public boolean tie;

    // Boolean variable to check whose turn it is.
    public boolean playerTurn;

    /**
     * Game constructor
     */
    public Game() {

        // Set the size of the screen
        this.setPreferredSize(new Dimension(width, height));

        // Make the background of the screen black
        this.setBackground(Color.black);

        // Set the layout of the screen to grid with specific number of rows and cols.
        this.setLayout(new GridLayout(row, col));

        // Take control of the game as soon as the screen spawns
        this.setFocusable(true);

        // Calling the initialize method below
        initialize();
    }

    /**
     * getNodeArray method returns the main node array.
     *
     * @return
     */
    public Node[][] getNodeArray() {
        return node;
    }

    /**
     * initialize method would initialize other game components.
     */
    public void initialize() {
        // Add cell nodes to the game
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                node[i][j] = new Node(i, j, this);
                this.add(node[i][j]);
            }
        }

        // Initiating random variable
        random = new Random();

        // Total available spaces is 7 x 6 = 42. Useful for checking if the game is a tie.
        availableSpaces = (row * col);

        // Setting executing to true as our game execution is going to begin
        executing = true;

        // Player plays the first move
        playerTurn = true;
    }

    /**
     * insertAtLowestRow method takes the col and searches for the lowest row which isn't occupied by a chip.
     * Then inserts the chip at that specific row and column.
     *
     * @param node
     * @param currentCol
     * @param chips
     */
    public void insertAtLowestRow(Node[][] node, int currentCol, Chips chips) {
        int lowestRow = 0;
        // Iterate through the row
        for (int i = 0; i < row; i++) {
            if (node[i][currentCol].getChips() == Chips.BLACKCHIP ||
                    node[i][currentCol].getChips() == Chips.WHITECHIP) {
                // Get the first row which has a black or a white chip occupying it.
                // Then insert the chip in the row above it
                if (i - 1 >= 0) {
                    lowestRow = i - 1;
                }
                break;
            } else {
                // If the column is completely empty then consider the lowest row to be the bottom most row.
                lowestRow = i;
            }
        }
        // If the lowestRow is greater than or equal to zero (valid row).
        // Then insert the chip at that position
        if (lowestRow >= 0) {
            node[lowestRow][currentCol].setChips(chips);
            availableSpaces--; // Reduce the number of available spaces.
            playerTurn = false;
        }
    }

    /**
     * isEmptyRow method: This method checks whether a top most row in a column is empty or not.
     *
     * @param node
     * @param currentCol
     * @return boolean
     */
    public boolean isEmptyRow(Node[][] node, int currentCol) {
        boolean isEmpty = true;
        if (node[0][currentCol].getChips() == Chips.BLACKCHIP
                || node[0][currentCol].getChips() == Chips.WHITECHIP) {
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * getEmptyRow method would get the empty row in a specific column.
     *
     * @param node
     * @param currentCol
     * @return boolean
     */
    public int getEmptyRow(Node[][] node, int currentCol) {
        int emptyRow = 0;
        for (int i = 0; i < row; i++) {
            if (node[i][currentCol].getChips() == Chips.BLACKCHIP ||
                    node[i][currentCol].getChips() == Chips.WHITECHIP) {
                // Get that empty row only when i - 1 is greater than or equal to zero.
                // Also the empty row will be the one above the current top most row which has a chip
                if (i - 1 >= 0) {
                    emptyRow = i - 1;
                }
                break;
            } else {
                // If the column is completely empty then consider the lowest row to be the bottom most row.
                emptyRow = i;
            }
        }
        return emptyRow;
    }

    /**
     * fourConnectedVertically method would return if four chips are connected vertically
     *
     * @param node
     * @param chips
     * @return boolean
     */
    private boolean fourConnectedVertically(Node[][] node, Chips chips) {
        for (int c = 0; c < col; c++) {
            for (int r = 0; r < row - 3; r++) {
                if (node[r][c].getChips() == chips && node[r + 1][c].getChips() == chips
                        && node[r + 2][c].getChips() == chips && node[r + 3][c].getChips() == chips) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * fourConnectedHorizontal method would return if four chips are connected horizontal.
     *
     * @param node
     * @param chips
     * @return boolean
     */
    private boolean fourConnectedHorizontally(Node[][] node, Chips chips) {
        for (int c = 0; c < col - 3; c++) {
            for (int r = 0; r < row; r++) {
                if (node[r][c].getChips() == chips && node[r][c + 1].getChips() == chips
                        && node[r][c + 2].getChips() == chips && node[r][c + 3].getChips() == chips) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * fourConnectedDiagonally method would return if four chips are connected diagonally.
     *
     * @param node
     * @param chips
     * @return boolean
     */
    private boolean fourConnectedDiagonally(Node[][] node, Chips chips) {
        for (int c = 0; c < col - 3; c++) {
            for (int r = 0; r < row - 3; r++) {
                if (node[r][c].getChips() == chips && node[r + 1][c + 1].getChips() == chips
                        && node[r + 2][c + 2].getChips() == chips && node[r + 3][c + 3].getChips() == chips) {
                    return true;
                }
            }
        }
        for (int c = 0; c < col - 3; c++) {
            for (int r = 3; r < row; r++) {
                if (node[r][c].getChips() == chips && node[r - 1][c + 1].getChips() == chips
                        && node[r - 2][c + 2].getChips() == chips && node[r - 3][c + 3].getChips() == chips) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checkWinner method checks who have won the game or whether it is a tie. This method is mainly used in the minimax algorithm
     * in the terminal condition to check the outcome of every move in the subtree.
     *
     * @param node
     * @param chips
     * @return boolean
     */
    public boolean checkWinner(Node[][] node, Chips chips) {
        boolean result = false;
        // If we have connected either vertically, horizontally or diagonally.
        boolean winner = fourConnectedVertically(node, chips) || fourConnectedHorizontally(node, chips) || fourConnectedDiagonally(node, chips);
        if (winner) {
            result = true;
        }
        // If availableSpaces is 0 and if there is no winner then it is a tie
        if (availableSpaces == 0 && !winner) {
            // Only for tie
            result = false;
        }
        return result;
    }

    /**
     * cloneArray method would make a deep copy of an array.
     *
     * @param nodeArray
     * @return Node[][] (Node Array)
     */
    public Node[][] cloneArray(Node[][] nodeArray) {
        Node[][] target = new Node[row][col];
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = 0; j < nodeArray[i].length; j++) {
                target[i][j] = new Node(i, j, this);
                target[i][j].setRow(nodeArray[i][j].getRow());
                target[i][j].setCol(nodeArray[i][j].getCol());
                target[i][j].setChips(nodeArray[i][j].getChips());
            }
        }
        return target;
    }

    /**
     * validColumns method gets us the columns in the node array in which a chip could be added.
     *
     * @param node
     * @return ArrayList
     */
    public ArrayList<Integer> validColumns(Node[][] node) {
        // We get valid node positions on which connect chips could be placed
        ArrayList<Integer> valid_col = new ArrayList<Integer>();
        // Iterate through all the columns
        for (int c = 0; c < col; c++) {
            // We check if each column's top most row contains a chip.
            // If yes then it cannot be used as a valid col to insert more chips.
            if (node[0][c].getChips() != Chips.BLACKCHIP && node[0][c].getChips() != Chips.WHITECHIP) {
                valid_col.add(c); // If valid then add it to arrayList
            }
        }
        return valid_col;
    }

    /**
     * isWinner method is the main method which keeps track of the state of the game. Whenever it is a win for either player or AI.
     * Or if it is a tie, then a message dialog would pop up saying who has won or whether it is a tie.
     */
    private void isWinner() {
        // If player won
        if (this.checkWinner(node, Chips.WHITECHIP)) {
            JOptionPane.showMessageDialog(null, "Player Wins");
            playerWins = true;
            executing = false;
        }
        // If AI won
        if (this.checkWinner(node, Chips.BLACKCHIP)) {
            JOptionPane.showMessageDialog(null, "AI Wins");
            aiWins = true;
            executing = false;
        }
        // If it is a tie.
        if (availableSpaces == 0) {
            JOptionPane.showMessageDialog(null, "Tie");
            tie = true;
            executing = false;
        }
    }

    /**
     * nextTurn method makes the AI play a move when the player has played a move.
     */
    public void nextTurn() {
        // Check if the player has won by the move they made.
        isWinner();

        // Add a bit of time interval
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Make the AI add a chip as a countermove
        aiAddChip();

        // Add a bit of time interval.
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // And check if the move made by AI has caused it to win the game.
        isWinner();
    }


    /**
     * bestMove method calls the minimax algorithm. It does so by getting a valid column from the validColList ArrayList and
     * placing a black chip in that column's available row, and then it calls the minimax algorithm. It does this for all the
     * valid columns one by one and returns the best column to move to by getting the column with the bestScore.
     *
     * @return bestCol
     */
    public int bestMove() {
        // Get the valid col nodes.
        ArrayList<Integer> validColList = validColumns(node);

        // The best score variable
        double bestScore = Double.NEGATIVE_INFINITY;

        // Get one of the valid columns from the validColList.
        int bestCol = validColList.get(random.nextInt(validColList.size()));

        for (int c = 0; c < validColList.size(); c++) {
            // Get a deep copy of the node array
            Node[][] nodeCopy = this.cloneArray(node);
            // Now insert the Black chip in the nodeCopy array
            insertAtLowestRow(nodeCopy, validColList.get(c), Chips.BLACKCHIP);

            // Call minimax
            double[] score = this.minimax(nodeCopy, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);

            // Get the column with the bestScore
            if (score[0] > bestScore) {
                bestScore = score[0];
                bestCol = validColList.get(c);
            }
        }
        return bestCol;
    }

    /**
     * Minimax Algorithm
     *
     * @param nodeCopy
     * @param depth
     * @param alpha
     * @param beta
     * @param isMaximizing
     * @return double[] array which consists of bestScore and bestCol
     */
    public double[] minimax(Node[][] nodeCopy, int depth, double alpha, double beta, boolean isMaximizing) {
        // Our result will consist of the best possible score and column.
        double[] result = new double[2];

        // isAIWinner checks whether the AI is the winner
        boolean isAIWinner = this.checkWinner(nodeCopy, Chips.BLACKCHIP);
        // isPlayerWinner checks whether the player is the winner
        boolean isPlayerWinner = this.checkWinner(nodeCopy, Chips.WHITECHIP);

        // First we check the terminal condition
        if (depth == 0 || isAIWinner || isPlayerWinner) {
            // If either of the player or AI is the winner then do the following.
            if (isPlayerWinner || isAIWinner) {
                // Here we check if the winner is the user
                if (isPlayerWinner) {
                    // If player wins then we return -infinity as our best score
                    // This is because we don't want AI to pick this subtree where player wins.
                    result[0] = Double.NEGATIVE_INFINITY;
                    // Here the column is 0, but it does not matter what the number is over here.
                    // It does not impact our minimax algorithm.
                    result[1] = 0;
                    return result;
                }
                // Here we check if the winner is the AI.
                if (isAIWinner) {
                    // Here best score is +infinity because we want the AI to pick this subtree as a possibility to win the game.
                    // And play the best move.
                    result[0] = Double.POSITIVE_INFINITY;
                    // Same as above for the best column.
                    result[1] = 0;
                    return result;
                }
            } else {
                // When we have reached depth 0 and if it is not a player or AI win
                // Then, we will score those respective subtrees. In case we don't get a winner, we will
                // use the highest score subtree and make the AI play a clever move.
                result[0] = scoreNode(nodeCopy, Chips.BLACKCHIP);
                result[1] = 0;
                return result;
            }
        }

        // If it is the maximizing player
        if (isMaximizing) {
            double bestScore = Double.NEGATIVE_INFINITY;
            double bestCol = 0;

            // We iterate through all the possible columns.
            for (int validCol = 0; validCol < col; validCol++) {
                // Filter out all which aren't valid (which are filled)
                if (nodeCopy[0][validCol].getChips() != Chips.BLACKCHIP && nodeCopy[0][validCol].getChips() != Chips.WHITECHIP) {
                    // Get the bottom-most empty row in that specific column.
                    int validRow = getEmptyRow(nodeCopy, validCol);
                    // Add a black chip (AI's turn) in that specific column.
                    nodeCopy[validRow][validCol].setChips(Chips.BLACKCHIP);
                    // Recursively call minimax to get minimizing players move and eventually score that move.
                    double[] resultFromMethod = this.minimax(nodeCopy, depth - 1, alpha, beta, false);
                    // Undo that move
                    nodeCopy[validRow][validCol].setChips(null);

                    // Get the bestScore and bestCol.
                    if (resultFromMethod[0] > bestScore) {
                        bestScore = resultFromMethod[0];
                        bestCol = resultFromMethod[1];
                    }

                    // alpha-beta pruning
                    // Set our alpha to the bestScore possible
                    alpha = Math.max(alpha, bestScore);
                    // If we already got a score greater than any other possible scores in other subtrees
                    // then we ignore those other subtrees to save computational time and resources.
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            // Return our results.
            double[] resultMiniMax = {bestScore, bestCol};
            return resultMiniMax;
        } else {
            // After maximizing player's turn, it is minimizing player's turn.
            double bestScore = Double.POSITIVE_INFINITY;
            double bestCol = 0;

            // We do the same as above but now we play as the player, here we want to play a countermove
            // which gets us the minimum score.
            for (int validCol = 0; validCol < col; validCol++) {
                if (nodeCopy[0][validCol].getChips() != Chips.BLACKCHIP && nodeCopy[0][validCol].getChips() != Chips.WHITECHIP) {
                    // Get a valid row
                    int validRow = getEmptyRow(nodeCopy, validCol);
                    // Play the move as the player
                    nodeCopy[validRow][validCol].setChips(Chips.WHITECHIP);
                    // Recursively call minimax to get the maximizing player's turn and eventually get the minimum score.
                    double[] resultFromMethod = this.minimax(nodeCopy, depth - 1, alpha, beta, true);
                    // Undo that move
                    nodeCopy[validRow][validCol].setChips(null);

                    // Get a minimum score and best column based on that score.
                    if (resultFromMethod[0] < bestScore) {
                        bestScore = resultFromMethod[0];
                        bestCol = resultFromMethod[1];
                    }

                    // alpha-beta pruning
                    // Our beta is the minimum score
                    // If we already a minimum score, then we avoid accessing the other subtrees.
                    // as we know we cannot get a lower score than this.
                    beta = Math.min(beta, bestScore);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            // Return our results
            double[] resultMiniMax = {bestScore, bestCol};
            return resultMiniMax;
        }
    }

    /**
     * aiAddChip method: This method add a chip to a specific column based on the optimal move from minimax algorithm.
     */
    public void aiAddChip() {
        int bestCol = this.bestMove();
        // If the position is 0, 0 and if a chip exists then do nothing
        if (this.getEmptyRow(node, bestCol) == 0 && bestCol == 0
                && (node[this.getEmptyRow(node, bestCol)][bestCol].getChips() == Chips.BLACKCHIP
                || node[this.getEmptyRow(node, bestCol)][bestCol].getChips() == Chips.WHITECHIP)) {

        } else {
            // If it is ok to insert a chip at that specific column then insert.
            this.insertAtLowestRow(node, bestCol, Chips.BLACKCHIP);
            availableSpaces--; // Reduce the number of available spaces when the AI's chip occupies the position.
            playerTurn = true; // Now it is the player's turn.
        }
    }

    /**
     * countChips method takes an array of size 4. And counts the number of chips in consecutive order.
     *
     * @param rowCol
     * @param chips
     * @return currCount
     */
    public int countChips(Node[] rowCol, Chips chips) {
        // currCount counts the current chip under consideration
        int currCount = 0;
        // previousCount keeps the highest consecutive count of that chip in case we encounter another chip.
        int previousCount = 0;

        // Iterate through this array
        for (int i = 0; i < rowCol.length; i++) {
            // If this is the chip we are looking for then increment its current count.
            if (rowCol[i].getChips() == chips) {
                currCount++;
                // If our currentCount is greater than the previous count then update
                if (currCount > previousCount) {
                    previousCount = currCount;
                }
            } else {
                // If we encounter another chip then we reset current count.
                currCount = 0;
            }
        }
        // If our current count is lesser than previous count then we return previous count
        if (currCount < previousCount) {
            return previousCount;
        }
        // Else we return the current count.
        return currCount;
    }


    /**
     * scoreRowOrCol method: Based on the chips we counted in the previous method, we will get their respective score.
     *
     * @param rowCol
     * @param chips
     * @return
     */
    public int scoreRowOrCol(Node[] rowCol, Chips chips) {
        // Depending on the chip, we also manage the score of the opponent's move
        Chips opponent;
        if (chips == Chips.WHITECHIP) {
            opponent = Chips.BLACKCHIP;
        } else {
            opponent = Chips.WHITECHIP;
        }

        int score = 0;

        // Get the count of every chip and empty space.
        int chipCount = this.countChips(rowCol, chips);
        int emptySpace = this.countChips(rowCol, null);
        int oppChipCount = this.countChips(rowCol, opponent);


        // Favourite chip's move would result in the score getting incremented.
        if (chipCount == 4) {
            score += 10000;
        } else if (chipCount == 3 && emptySpace == 1) {
            score += 70;
        } else if (chipCount == 2 && emptySpace == 2) {
            score += 50;
        } else if (chipCount == 1 && emptySpace == 3) {
            score += 20;
        }

        // Opponent chip's move would result in the score getting decremented.
        if (oppChipCount == 4) {
            score -= 10000;
        } else if (oppChipCount == 3 && emptySpace == 1) {
            score -= 70;
        } else if (oppChipCount == 2 && emptySpace == 2) {
            score -= 50;
        } else if (oppChipCount == 1 && emptySpace == 3) {
            score -= 20;
        }
        return score;
    }

    /**
     * scoreNode method would score a node by checking its  horizontal, vertical and diagonal surrounding.
     * Each of these would get scored based on where certain chips are placed. All the score that we
     * get from horizontal, vertical and diagonal checks, would get added together. This will give us a rating of that node.
     * If the score is high then that node should be picked as it would be the best move.
     *
     * @param node
     * @param chips
     * @return
     */
    public int scoreNode(Node[][] node, Chips chips) {
        int score = 0;
        // Horizontal
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col - 3; c++) {
                Node[] rowCol = {node[r][c], node[r][c + 1], node[r][c + 2], node[r][c + 3]};
                score += this.scoreRowOrCol(rowCol, chips);
            }
        }

        // Vertical
        for (int r = 0; r < row - 3; r++) {
            for (int c = 0; c < col; c++) {
                Node[] rowCol = {node[r][c], node[r + 1][c], node[r + 2][c], node[r + 3][c]};
                score += this.scoreRowOrCol(rowCol, chips);
            }
        }

        // Diagonal
        for (int r = 0; r < row - 3; r++) {
            for (int c = 0; c < col - 3; c++) {
                Node[] rowCol = {node[r][c], node[r + 1][c + 1], node[r + 2][c + 2], node[r + 3][c + 3]};
                score += this.scoreRowOrCol(rowCol, chips);
            }
        }

        // Diagonal
        for (int r = 3; r < row; r++) {
            for (int c = 0; c < col - 3; c++) {
                Node[] rowCol = {node[r][c], node[r - 1][c + 1], node[r - 2][c + 2], node[r - 3][c + 3]};
                score += this.scoreRowOrCol(rowCol, chips);
            }
        }

        return score; // Return the total score.
    }
}
