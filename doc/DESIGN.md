# Simulation Design Final
### Names - Jason Qiu jq39 Franklin Wu fyw4

## Team Roles and Responsibilities

 * Jason Qiu - worked on the power ups, ball, cheat keys, Game Piece, Game Launcher, and parts of Game

 * Franklin Wu - worked on the displays, block, paddle, readers, movement of the blocks, and parts of Game


## Design goals
* Develop a functioning game that appears on the screen in an application window.
* The game should have a movable paddle that is controlled by the user's left and right keys.
* A ball placed on the paddle should be able to be launched based off a mouse click.
* The components of the game - paddle, power ups, blocks, ball, blocks - should have some sort of
interaction with each other that changes the game in some way (with certain defined exceptions, such
as balls passing through falling power ups).
* The game should be able to load levels from data files that follow well defined rules and the game
should be able to progress from level to level.
* If a ball falls below the level, the player loses a life.
* A score and high score should be kept with the game ending if all lives are lost and the level is won
when all the blocks have been cleared off the screen.
* A block or power up that is not falling should lose 1 life when hit by a block.

#### What Features are Easy to Add
New features we wanted to make easier to add were new power ups and levels. Power ups can be easily implemented
by inheriting the PowerUp abstract class. Since we used reflection when dealing with determining what 
new power up would be generated, it is very easy for new PowerUps to be added to the game without changing
the original source code. Additionally, levels are very easy to add as long as they follow a certain guideline.
They should be named as *levelXX.txt* where *XX* is the number of the level. The first line should contain a movement -
"sideways", "none", and "descending." The next lines should contain numbers from 1-3 separated by spaces,
where the numbers represent the number of lives.


## High-level Design
The game is organized such that it is launched by a GameLauncher class and a ball, paddle, and 
a grid of GamePieces (blocks) is instantiated in a layout defined by a data file. 
The balls, paddles, and GamePieces (blocks and power ups) all interact and collide with each other with
exception to power ups that pass through the ball. The paddle is controlled by arrow keys and the ball
is launched with a mouse click. When a ball hits a block, it loses a life, and if it hits a power up
it begins falling. If the power up is hit by the paddle, its power up is activated for a certain amount
of time. The hits and interactions are checked for with every animation step in the Game class, and all
the shapes are duly updated based on the interactions. When a block disappears, there is a small chance
for a random power up to be generated in that position by Game. When the ball reaches below the screen, the
ball and paddle is reset by the Game.

#### Core Classes
* The GameLauncher class handles the JavaFx application (scenes, roots, etc.) and creates a 
Game object that runs the entire game in addition to making displays
that display lives, scores, levels, and high scores.
* The Game class contains the main behaviors of the game, such as keeping track of key presses, mouse clicks,
  and animation steps which update game pieces, ball, and paddle. 
* The ball and paddle classes keep track of positions and velocities and update their position for every
 animation step.
 * A GamePiece abstract class represents all blocks and power ups that are organized in
   a grid at the top of the level, which also keeps track of positions and velocities. 
 * Blocks and powerups implement the GamePiece abstract class and implement the updateStatus method
   which is different depending on the block or power up.
 * All displays implement the NumericDisplay abstract class which implement update and reset methods
   to update and reset the displays.
 * The BlockConfigurationReader is called by the Game Launcher and loads in corresponding levels represented
   by a grid of game pieces based on level numbers and data files. 

## Assumptions that Affect the Design
* The data files will be named similarly and in a consecutive order and they all have similar formats.
* Power Ups and Blocks are the only components that will be put in the game
* Blocks will have a max life of 3.

#### Features Affected by Assumptions
* Block Configuration Reader was more easily implemented because the files were all designed to have 
similar data and filenames and will be read in easily. 
* GamePiece was designed so that it is limited to Power Ups and Blocks and any other similar component
must have similar features for it to function correctly.
* This simplified a specific method in the block class which was limited to 3 lives and had predetermined
colors.

## Significant Differences between Original Plan and Final Design
* The original would have unbreakable blocks or bomb blocks, but this was not implemented.
Instead the blocks were made to be moveable.
* Originally the block class was supposedd to be abstract and be extendable for other types of blocks.
Instead a GamePiece class was made that was made to be extendable to PowerUp class and Block class.

## New Features HowTo
* To add new features, programmers can extend PowerUp, GamePiece, and NumericDisplay abstract classes to implement
new power ups, "block"-like components, and new text displays. To add power ups, they have to add the class
to the POWERUPS arraylist. The program must add the display to the root within the GameLauncher class.
This way, the program is designed such that power ups, game pieces, and displays will automatically
be incorporated within the game without modification to the source code.


#### Easy to Add Features
Features that are easy to add are power ups, new blocks, and displays. 

#### Other Features not yet Done
Features of the game that were not able to be completed by the deadline were adding more intense blocks
like BombBlocks. Additionally, we thought about adding a feature that swapped the blocks randomly
throughout the game, which would have added an element of chaos to the game.
