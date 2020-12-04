game
====

This project implements the game of Breakout.

Name: Franklin Wu, Jason Qiu

### Timeline

Start Date: Sept 12 2020

Finish Date: Sept 29 2020

Hours Spent: 30 each

### Resources Used
StackOverflow, JavaFX API


### Running the Program

Main class: GameLauncher

Data files needed: 

Files to read in blocks, which can be found under data/blockfiles.
Also need file to store high score, which can be found under data/highscore.

Key/Mouse inputs:

Mouse: Launches ball. To do so, click anywhere in the window. The position in which
you click the mouse will approximately direct the direction of the ball. This is not 
meant to be accurate because we did not wish for users to be able to pinpoint
send the ball into any potential gaps formed by missing blocks, which would
make the game much easier. Randomness is fun! 

Left Arrow Key: Moves paddle to the left.

Right Arrow Key: Moves paddle to the right.

Cheat keys:
R -> resets level
SPACE -> pauses game
L -> adds 1 life
P -> makes the first block into a random powerup
C -> clears the level
D -> clears the first block;
S -> resets high score;
F -> all remaining blocks on the screen lose one life();
G -> clear the first (bottom-most) row();
1 -> jump to level 1
2 -> jump to level 2
3 -> jump to level 3
4 -> jump to level 4
5 -> jump to level 5

Known Bugs:

1) Game Physics of the Ball + Block Collisions. 
The physics of our game is slightly off. Occasionally, when the ball approaches
a block in a particular way, it might pass right through the block without bouncing
off, leading to some weird pathing. We tried very hard to debug this, but ultimately
to no avail. There are a couple specific cases which demonstrate this bug. The
first case is that the ball is hitting the block exactly on the corner, causes 
the ball to bounce off weirdly or to phase right through the block. The second 
case is that when the ball hits two blocks at once, it could pass 
right through them. The third case is when the ball comes in at a diagonal angle to the
block's side, hits it, passes through the block, and bounces off of its bottom/top side.
Essentially, the block hits the block twice in one go, which is a bug. One potential causation
that we looked into and ruled out was that the speed of the ball was too fast. 
Initially, we thought that the ball speed could be too fast for the step function to process.
However, even after lowering the speed of the ball significantly, the bug still persisted.
We know that the root cause of this issue has to do with erroneous consecutive calls to 
the update velocity functions -- when debugging, we put in sysout statements
to print the velocity change, and sometimes it'd change twice in a single ball bounce.
Unfortunately, we have had no luck in identifying the cause beyond that.

2) Game Physics of the Ball + Paddle Collision
Similar to the first issue, the game physics for the ball and paddle collision are a little
wonky. If you happen to catch the ball just right with the side of the paddle, the ball
will zig-zag along the paddle before eventually releasing either into the screen or down into
the bottom of the screen. We know this is a corner case, but we were unable to resolve it 
suitably. The ball undergoes multiple velocity changes because, when it is caught with 
the side of the paddle, it becomes caught "inside" the paddle. 

Extra credit:

Places where we went above and beyond:
    1) Our program is able to read in any configuration of blocks of size x by y. It is not
    limited to any certain row by column numbers, and the size of the blocks will change accordingly
    to fit within the screen.
    2) Added images for our power-ups, so you know what power-up you're getting!
    3) On certain levels, the blocks move!
    4) We added extra cheat keys -- for a description, read above.
    5) Aesthetic colors to improve overall user experience.
    6) Within our program, we implemented Reflection, which was a very advanced topic
    for us. This allows our program to be easily extendable in the future, since Reflection
    does not require hardcoded classes to work and instead can grab any class that is a Power-Up.
    7) Extensive use of inheritance and polymorphism! (check out display and gamepiece)
    
    


### Notes/Assumptions

#####Assumptions
Data file assumptions:
1) blockfiles
- The first line of all blockfiles must describe the type of block movement for the level.
The possibilites are: "none", "sideways", and "descending".
- All blocks are represented only by the number of lives they have, ranging from 0-3.
- Blocks are delimited by spaces, and there must not be any empty rows or columns -- to
represent no block, the correct representation 0, which tells the program that the block
has 0 lives and thus no block will be shown there.

2) highscore
- the highscores file only contains one line which only contains one number: the all-time high score

#####Notes
For cheat keys, we defined "first block" as the first "alive" block in the bottom-most row, 
on the left-most side of that row.

### Impressions
Overall, the project was pretty enjoyable, definitely more so than the Data project in our
opinion. It was fun to create a game from scratch, and it definitely leveraged a lot of what
we learned on inheritance.

# brickbreaker
