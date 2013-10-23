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

WORK IN PROGRESS:
* Drawing primitive shapes (points, lines, rectangles). (jacob)
* Collisions with OBB trees. (Jack)
* Add scaling transform functionality to scene graph (jack)

WHAT NEEDS TO BE DONE:
* Sound back-end. (jacob)
* Most game logic [expand on this].
* Mouse/KeyBoard callback class to register events(like in awt package: KeyListener/MouseListener ect..) (jack)
* Action to change object's orientation based on mouse loc 
* Action to control object based on keys pressed.(already have one, but it needs to be revisited)

=====
Download the LWJGL runtimes, follow the instructions for setting it up with Eclipse.
http://lwjgl.org/wiki/index.php?title=Setting_Up_LWJGL_with_Eclipse

=====
How to use git / basic source control workflow:

1) Before you start working on the code, check for new commits in the code!
	* Eclipse: right-click the project under the "Git Repositories" and click "pull"
	
2) Work on the code.

3) BEFORE YOUR PUSH YOUR CHANGES, do step one again! 
	* Always pull the latest code before you push, 
		this will merge changes and make your code fully up-to-date, and avoid nasty merge conflicts.
	
4) Push
	* Command line: 
		git push origin master
	* Eclipse plugin: 
		1. Open the "Git Staging" tab (part of the Git Repository Exploring "perspective")
		2. Drag the files from the "Unstaged" to "Staged" list (don't add .classpath and .project)
		3. Write your commit message! Don't leave us guessing what you did:
			(I always write at least one sentence to sum up the changes, 
				maybe more details on what was changed in each file.)
		4. Commit and Push
			It may ask for your GitHub credentials the first time.

=====
If you are using the git plugin for Eclipse and it won't push your changes:

	Add these line to the end of the .git/config file:
	[branch "master"]
    	remote = origin
    	merge = refs/heads/master	
or
	If all else fails, do it from the command line: 
		git push origin master
			
