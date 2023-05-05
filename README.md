# Connect 4 Game: Player vs AI

## Description of the logic and design of the game
### User Input
In order to play this game, the user would need to use their mouse. The game controls are simple, you 
are the white chip player and you always go first. In order to drop the chip in a specific row, you will
need to click to click a specific column, it does not matter what row you click.

### Game performance
The game performance on my computer is instantaneous, in fact I have added delay to make it seem like the
computer is waiting and then making a move. In case the game is slow on your computer, then you can change the depth of the minimax 
algorithm to 2 or 1. Remember having a lower depth means the computer would go that many rows deep. You can change the depth at line 380 
of file `Game.java` in case you have performance issues.

### Logic and design of the game
The logic of the game is simple, connect 4 chips vertically, horizontally or diagonally to win the game.

The algorithm implemented is Minimax algorithm with alpha-beta pruning, this algorithm performs 
recursion and makes a move with the highest score.

The game has logic implemented which constantly checks if the AI or player has won the game by checking all the nodes vertically, 
horizontally and diagonally. If someone wins the game then a JOptionPane GUI pops up saying who won or whether it a tie.

The game rules and how it functions:
- Connect 4 chips vertically, horizontally or diagonally to win the game.
- Player (White chip) goes first and then the AI (black chip).
- Once the player has made a move, they have to wait for the AI to make a move. It is a turn based game.
- When a column is filled, no more chips could be added to it as it has 0 rows available.
- If you win the game, the player or the AI won't be able to make any moves. The execution of the game is brought to an end. 

The most important component of this game is the Node array, we are using it to manage the game logic. Everytime we execute minimax 
algorithm, we create a clone of this array as we don't want to cause any unwanted changes to it.

### Description of Minimax Algorithm
The way the minimax algorithm works in this game is as follows:
- Method bestMove() is the method which is responsible for making the move for the AI.
- This method will first place a black chip in every valid column.
- Every single move is considered as a subtree which keeps expanding when we call the minimax algorithm.
- In Minimax algorithm, we have the maximizing player (Black chip) and the minimizing player (White chip).
- The maximizing player plays a move which maximizes its score and the opposite counts for the minimizing player. 
- As mentioned before this is a tree structure algorithm and this has subtrees. In order to minimize redundant calculations and save 
processing time, we do alpha-beta pruning. For example, we have a sub-tree and if it is the maximizing player's turn, it will need to 
pick the subtree with the highest score. It will first go down the left subtree and it will getting the best score from there. If the 
score from left subtree is more than what's required then it won't make its way down the right subtree. Thus we are pruning that right 
subtree. The similar thing holds true for the minimizing algorithm, except for the fact that it selects the subtree with the lowest score.
- In order to get accurate results and to play the best possible move. I have scored every possible combination of black and white chips. 
For example, the player has placed 3 white chips diagonally, the AI needs to make a move which would avoid the player from winning. The 
best move in this case would be to place a black chip in that specific spot to avoid 4 to connect. In this case, I have set that 
position's score to -1000, if the AI does not make that move, then it will lose 1000 points. And the AI always picks the spot which 
maximizes its score. Thus, the AI would place a black chip at that position causing the player to not win. Similarly, there are various 
other scenarios like 2 white chips in a row and 2 empty rows or 3 black chips in a row and 1 empty row, each with different scores.

### File Structure
- `Chips.java`: A enum class which contains two chips.
- `Main.java`: The class which gets compiled and executed. This class will set the JFrame components.
- `Game.java`: This class has the main logic of the game and contains the minimax algorithm.
- `Node.java`: We are using JButtons for our game as they look neat. Also, we are implementing ActionListener because the player would be 
clicking a specific column to place their chip. This click would cause the methods in `Game.java` to execute which would make the AI play 
its move after the player. We are also setting the image for both black and white chip in this class. This class keeps track of a single 
node's position and image as well as calls the methods in other class. 

## Instructions on how to compile/run the game
I have provided all the files in this repo `Connect4-AI`.  I successfully ran my code in Powershell.
If you are running this in WSL then you need Windows X server configured.
- Clone this repository.
- Navigate to the folder `Connect4-AI`.
- To compile the project in java, run command: `javac .\Main.java`
- To execute the project in java, run command: `java Main`
- To run .jar file, run command: `java -jar .\Connect4.jar`
- In case the jar file returns an error (like java.lang.UnsupportedClassVersionError), you can create a new jar file with this command:
    ```
    jar -cfvm Connect4.jar .\Manifest.txt *.class images\*.png
    ```
  
## Bugs in the game
I haven't found any so far. Game performance depending on the kind of 
hardware you use could be a very minor issue or inconvinence. If it is a old PC then the recursion can cause a bit of delay in AI's 
response. 

## Screenshot of the game 
<img width="523" alt="Connect4" src="https://user-images.githubusercontent.com/70837272/236326066-2503037c-0d92-462e-abad-a157e63cb0ee.PNG">
