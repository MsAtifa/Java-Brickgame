# COMP2042_CW_szyam1
## BrickGame

The main goal of Brick Breaker is to eliminate all the bricks on the screen by using a bouncing ball and controlling a paddle at the bottom with left and right arrow keys. The paddle's role is to deflect the ball and prevent it from falling off the screen. Players earn points for breaking each brick. If the ball hits the star block, it enters a golden period where hitting the bottom doesn't result in a score loss. Hitting the chocolate block provides a chance to score +3 by claiming the bonus. The heart block grants extra lives to continue playing, as losing all hearts results in a game over. The game consists of 8 levels and is themed around Hello Kitty.
![main page](https://github.com/MsAtifa/COMP2042_CW_szyam1/assets/152516802/2acb1145-1126-47d9-b6c3-8991825da71e)


### •Compilation Instructions:-
1.Open your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
2. Import the project into the IDE.
3. Build the project using the IDE's build or compile option.
4. To run the application, find and execute the main class.
(Note: I have worked in IntelliJ IDEA using Maven as the building tool)

### • Implemented and Working Properly:-
-The **GameEngine** is equipped with a 'toggle pause' method that is employed in the main file. It serves to either resume the game or even the game could be restarted based on the button selected in the pause menu, which becomes visible upon pressing the 'P' key.
![image](https://github.com/MsAtifa/COMP2042_CW_szyam1/assets/152516802/bcdcd882-5890-4e1b-994f-485c1c593d75)


-Additionally, improvements were made to the scoring system to address glitches in the display of scores and hearts. The implementation of JavaFX's FadeTransition contributes to a visually appealing animation, ensuring smooth transitions as score labels fade in and out. The use of Platform.runLater ensures that UI updates occur on the JavaFX Application Thread, preserving thread safety and preventing potential concurrency issues.Moreover, the showMessage method introduces a delay using a separate thread before initiating the fade-out animation. This ensures messages are displayed for a specific duration, offering clear communication to the player without abrupt removal of information.

-Furthermore, a "Play Again" button has been implemented in the showWin methid in score class, appearing upon completion of all 8 levels. Clicking this button resets all game components. The overall appearance of the game, including fonts and colors of texts and buttons, has been revamped for a more polished and cohesive visual design. Adjusted the golden period time as well in the main file and changed its background.
![playagain button](https://github.com/MsAtifa/COMP2042_CW_szyam1/assets/152516802/51478626-9857-4e8c-b322-35df5fcab205)
![blue bg page](https://github.com/MsAtifa/COMP2042_CW_szyam1/assets/152516802/7cef8cfa-ce42-4ab3-8bf7-4992392114fe)



### • Implemented but Not Working Properly:- 
Attempted to expand the game beyond 18 levels resulted in noticeable glitches after level 12. Issues arose with the proper counting of hearts, sudden game overs when the ball hit the bottom, and malfunctioning behavior of the gold ball. Subsequently, I addressed these challenges by refining the game engine and opting to maintain the number of levels at 8. This adjustment was made to ensure the seamless functioning of all game components.

### • Features Not Implemented:-
The game only occupies half of the Java application screen, and I couldn't manage to make it go fullscreen.

### • New Java Classes:-
-The **PauseMenu** class is a JavaFX component designed for creating a simple pause menu in a game. It utilizes the JavaFX Alert class to display a confirmation dialog with options for resuming or restarting the game.It includes buttons for "Resume," "Restart," and "Cancel." Upon user interaction, the chosen option is processed via a provided Consumer<String> choiceHandler. The pause menu pops up if the 'p' key is clicked and the game gets paused before the pause menu pops up. This is the only additional class added, the rest of the important methods were just refactored in the main file.

-The **setPhysicsToBall** method has been logically split into several distinct methods within the codebase. Each method is responsible for handling a specific aspect of the ball's physics and interactions. Here's a breakdown of how the original method was subdivided:

**1.updateBallPosition:** Manages the ball's position based on its velocity (vX and vY). It accounts for both vertical (goDownBall) and horizontal (goRightBall) movements.

**2.handleTopAndBottomBoundaries:** Checks if the ball has reached the top or bottom boundaries of the game scene. If so, it appropriately adjusts flags and handles the game state, including potential game over scenarios.

**3.handleGameOver:** Decrements the heart count, displays a negative score using the Score class, and triggers a game over message when the heart count reaches zero. It also stops the game engine using the engine.stop() method.

**4.handleBallCollisionWithBreak:** Detects if the ball collides with the break object, triggering the handleBreakCollision method if true.

**5.handleBreakCollision:** Determines the collision details with the break object, including resetting collision flags, updating movement directions, and calculating the ball's new velocity based on its relation to the center of the break object.

**6.handleBallCollisionWithWalls:** Checks for collisions with the left and right walls of the game scene. It adjusts collision flags and movement directions accordingly.

**7.handleBallCollisionWithBlocks:** Manages ball collisions with blocks, setting the appropriate flags to control ball movement based on the type of block collision.

**8.checkDestroyedCount:** Verifies if the number of destroyed blocks matches the total number of blocks in the game. If true, it indicates the completion of the level, and the nextLevel method is called.

-The **onUpdate** method in the code is vital for handling game visuals and collisions.The functionality within onUpdate has been split into two methods:

**1.updateLabelsAndShapes Method:** Updates visual elements like score and heart labels, along with the positions of game shapes. This method maintains code cleanliness, focusing on high-level visual updates and improving readability.

**2.handleBallBlockCollisions Method:** Manages collisions between the ball and game blocks, updating scores and handling block visibility. 

-The **onPhysicsUpdate** method handles various physics and game state updates. To enhance clarity and maintainability, the logic has been separated into two methods:

**1.GoldStatus Method:** Manages the gold status of the ball, handling its appearance and duration.

**2.updateChocos Method:** Specifically handles the update of bonus items (chocos) and their interactions with the break object. 

I tried making the above metioned methods which is located in the main file into different classes but it kept giving mulitthreading issues , so instead i refactored and split the methods in the main file itself for better organization and readabitlity.


### • Modified Java Classes:-
-The changes made to the **Score** class involve refactoring the code to improve readability, maintainability, and consistency. Here's an explanation of the modifications:

**1. Introduction of animateLabel Method:** A new private method animateLabel is introduced to encapsulate the common logic for animating a label. This method uses JavaFX's FadeTransition to smoothly fade out a label over a specified duration.

**2. Lambda Expressions and Method References:** The use of lambda expressions and method references has been incorporated to streamline the code. For example, the Platform.runLater calls are simplified using lambda expressions.
   
**3. Button Event Handling:** In the showGameOver and showWin methods, the event handling for the restart button is now implemented using lambda expressions, making the code more concise.

-The refactoring of the **GameEngine** class involves several changes aimed at improving code readability, maintainability, and introducing better concurrency handling. Here's an explanation of the key modifications:

**1. Concurrency Handling with ScheduledExecutorService:** The original code used basic threads for updating, physics calculations, and time counting. In the refactored version, a ScheduledExecutorService is employed for better concurrency control. It simplifies the scheduling of tasks with fixed-rate execution, making the code more robust and avoiding potential issues with thread interruptions.
 
**2. Introduction of Platform.runLater:** The use of Platform.runLater from JavaFX is introduced to ensure that UI-related updates are performed on the JavaFX Application Thread. This is crucial for avoiding concurrency issues and ensuring that UI modifications are done in a thread-safe manner.
   
**3. Graceful Shutdown with ScheduledExecutorService:** The stop method now shuts down the ScheduledExecutorService gracefully, allowing ongoing tasks to finish before termination. This is achieved using scheduler.shutdown() and handling the potential case where termination takes longer than a specified timeout with scheduler.awaitTermination.

**4. Initialization in the start Method:** The start method now explicitly initializes the game before starting the update and physics calculation tasks. This separation of concerns enhances the clarity of the code.

**5.Removal of Unnecessary Time Thread:** The time counting logic is now incorporated directly into the physicsCalculation task, eliminating the need for a separate time thread. This simplifies the code and avoids potential synchronization issues.

### • Unexpected Problems-
During gameplay, if the paddle does not move constantly and the ball hits the bottom, the game abruptly ends. Similarly, when using the gold ball, it may exhibit glitches near the bottom, temporarily exiting the lower boundary and reappearing if there is no continuous movement of the paddle. Furthermore, the ball hits only the top, bottom, left or right side of the block, so sometimes it can pass through the blocks.
