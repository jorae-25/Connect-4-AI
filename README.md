# Connect-4-AI
Connect 4 game against an AI which always plays optimally using the alpha beta pruning algorithm.

User is prompted to enter the size of the board in addition to how many in a row are required to win. After the alpha beta
pruning algorithm is ran on the board, the user will be told whether the AI is guranteed to win no matter what
play they make or if the game will end in a tie with optimal play on both sides. After each play, the updated game
board is printed out.

Example run:

Run part A, B, or C? B

Include debugging info? (y/n) n

Enter Rows: 4

Enter Columns: 4

Enter number in a row to win: 4

Transposition table has 7115 states.

The tree was pruned 9004 times

Neither player has a guaranteed win; game will end in tie with perfect play on both sides

who plays first? 1=human, 2=computer: 1


. . . . 
. . . . 
. . . . 
. . . . 
0 1 2 3 


Minimax value for this state: 0, optimal move: 0

It is MAX's turn!

you choose move: 0


. . . . 
. . . . 
. . . . 
X . . . 
0 1 2 3 


Minimax value for this state: 0, optimal move: 0

It is MIN's turn!

you chooses move: 0


. . . . 
. . . . 
O . . . 
X . . . 
0 1 2 3 


Minimax value for this state: 0, optimal move: 0

It is MAX's turn!

you choose move: 1


. . . . 
. . . . 
O . . . 
X X . . 
0 1 2 3 


This is a state that was previously pruned; re-running alpha beta from here.

Minimax value for this state: 0, optimal move: 0

It is MIN's turn!

you chooses move: 0


. . . . 
O . . . 
O . . . 
X X . . 
0 1 2 3 
Minimax value for this state: 0, optimal move: 0

It is MAX's turn!

you choose move: 
