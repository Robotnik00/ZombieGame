ZombieGame
==========
Status of major features (keep this list updated!):

WHAT IS DONE (tested and working!):  
* Keyboard and Mouse input.  
* IGamestate management / game loop.  
* Loading and displaying textures (images).  
* Scaling, rotation, translation on textures.  
* Scene graph for representing objects in game (GameObject class)  
* Physics action that can be applied to GameObjects  
* Collisions using bounding volume hierarchy with AABBs   
* Alpha transparency, color blending, colorkey, drawing a portion of a texture.  
* input callbacks are finished.(hopefully without breaking anything anyone was doing) >.<

WORK IN PROGRESS:  
* Drawing primitive shapes (points, lines, rectangles). (jacob)  
* Collisions with OBB trees. (Jack)  
* GUI stuff.

WHAT NEEDS TO BE DONE:  
* Sound back-end. (jacob)  
* Most game logic [expand on this].  
* fix interpolator in gameobject  
* Action to control object based on keys pressed.(already have one, but it needs to be revisited)  

Bugs:
* i noticed flickering prob. its minor, but noticable. I thought it was my interpolator, so i turned it off, but its still there.(see StartGame)




=====  
Download the LWJGL runtimes, follow the instructions for setting it up with Eclipse.  
http://lwjgl.org/wiki/index.php?title=Setting_Up_LWJGL_with_Eclipse

=====  
Exporting the game to a jar file (building the game for distribution):  
This process will create a "fat" jar file which includes all of the stuff the game needs to run (minus resources), so you don't get missing library exceptions.   
You will need to download jarsplice: http://ninjacave.com/jarsplice  

From Eclipse:  
1) Right click the project, choose "Export..."  
2) Under the "java" folder, choose "jar file" (NOT runnable jar)  
3) Select the resources to export (by default, everything should be already checked). (You may want to uncheck any resources (graphical/sound/etc): for some reason the game wasn't able to find them when they were included with the jar, so maybe we shouldn't bother to include them).  
4) Choose a file name (eg ZombieGameTemp.jar), then choose "Finish" (don't need to click next).  
5) Open jarsplice.  
6) Choose "Add JARs", add the game's jar file (ZombieGameTemp.jar from step 4), and the lwjgl jar files (in the lwjgl/jar/ folder; I added all of them, but we only use lwjgl.jar and lwjgl_util.jar, so we may only need to select these two).  
7) Choose "Add Natives", add the lwjgl native runtime files for your operating system (or the target OS), located in lwjgl/native/<OS>).  
8) Choose "Main class", enter "Engine.GameEngine" (the class with "main").  
9) Choose "Create fat JAR", click the button at the top, and save the jar (as ZombieGame.jar, this is the final product).  
10) Copy all game resources (graphics, sounds, etc) to the directory of the jar, and the program should be ready to run.  

If you have java installed, you should be able to double-click the jar to run it. However, if any errors popup, it won't display them.

If anything goes wrong, run it from the command line to see the errors:
java -jar ZombieGame.jar

=====  
How to use git / basic source control workflow:

1) Before you start working on the code, check for new commits in the code!
In eclipse: right-click the project under the "Git Repositories" and click "pull."
	
2) Work on the code.

3) BEFORE YOUR PUSH YOUR CHANGES, do step one again! Always pull the latest code before you push, this will merge changes and make your code fully up-to-date, and avoid nasty merge conflicts.
	
4) Push:  
Command line: git push origin master  
or  
Eclipse plugin:   
	1. Open the "Git Staging" tab (part of the Git Repository Exploring "perspective")  
	2. Drag the files from the "Unstaged" to "Staged" list (don't add .classpath and .project)  
	3. Write your commit message! Don't leave us guessing what you did: (I always write at least one sentence to sum up the changes, maybe more details on what was changed in each file.)  
	4. Commit and Push: It may ask for your GitHub credentials the first time.  

=====  
If you are using the git plugin for Eclipse and it won't push your changes:

Add these line to the end of the .git/config file:  
	[branch "master"]  
    	remote = origin  
    	merge = refs/heads/master	  

Or if all else fails, do it from the command line:   
		git push origin master
			
