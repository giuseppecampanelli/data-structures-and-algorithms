# Right Magnetic Cave
RightMagnetic Cave is a 1-player game consisting of a row of squares of any sizes each containing
an integer.

### Rules
* The circle on the initial square is a marker that can move to other squares along the row.
* At each step in the game, you may move the marker the number of squares indicated by the integer in the square it currently occupies divided by 2 if the number is even, and moved (n/2+1) if the number is odd.
* The marker may move either left or right along the row but may not move past either end.
* For example, the only legal first move is to move the marker four squares (8/2) to the right because there is no room to move four spaces to the left.
* The goal of the game is to move the marker to the magnetic cave, the “0” at the far end of the row.

### Note
* Though the RightMagnetic Cave game is solvable, actually with more than one solution — some configurations of this form may be impossible.

### Task
In this programming assignment, you will design using pseudo code and implement in java code two versions of the RightMagnetic game.
* The first version will be completely based on recursion.
* The second one will be based on a stack, queue, list or vector.
Your solution takes a starting position of the marker along with the list of squares. Your solution should return true if it is possible to solve the game from the starting configuration and false if it is impossible. Your solution should also work for any size of the game’s row, and a random value in each square. You may assume all the integers in the row are positive except for the last entry, the goal square. At the end of the game, the values of the elements in the list must be the same after calling your solution as they are beforehand, (that is, if you change them during processing, you need to change them back).