Using GameObject:

A GameObject is a node in what is called a 'Scene graph'. GameObjects keep track of location, scale, and orientation 
relative to their parent node. This makes it easier to build complex objects. For example, if you have a Character and
you want to give him a weapon, normally you would have to write a function that updates the gun's location whenever 
the Chatacter moves. In a scene graph the guns position would be updated to the correct location automatically.


Notes on making and adding to scene graph
1. Make a single GameObject called universe for attaching all other GameObjects to. (see StartGame state)
2. DON'T ADD UNIVERSE TO ANY OTHER GAMEOBJECTS THIS WILL CREATE A CYCLE IN THE SCENE GRAPH (Scene Must Be a Tree)
3. Make classes that use GameObjects to create a more complex object(see ExampleGameObject in package GameObjects)

Actions
1. used to add executable code to a GameObject update() callback
2. implement the interface or extend from an already existing Action.
3. to use an action, initialize it then add it to the GameObject you want to add code to


Collisions:

Collisions are handled using an algorithm located in Utilities.CollisionDetection. In order to set up collisions 
correctly, set the root GameObject of the complex object you want to be collidable to true. then set the
BoundingBoxes of all of GameObjects you want to be consider in the collision detection.

Collision detection is the main thing that slows a game down. for our game it prob wont be an issue because its only
in 2D, however there are still methods to make it run faster. By setting the ProxemityBounds of a GameObject you tell 
the algorith not to consider the children of the GameObject for collisions unless an Object intersects with the ProxemityBounds. This can significantly improve speed of collision detection. Make sure that the ProxemityBounds encompasses all of the objects children's BoundingBoxes however, because otherwise those bounding boxes will be ignored in collision detection.
