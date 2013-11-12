package Actions;

import Engine.IGameEngine;
import GameObjects.GameObject;
/**
*
* A physics object that is controlled by some outside forces
*/

// controls objects using forces so when outside events influence the object 
// it will still behave appropriately.
//Example: if an object collides with something than its velocity will change.
//		   if the object was controlled by setting velocity, than when user 
//         pushes a button it will set the velocity again. overriding the 
//         collision's change.
public abstract class PhysicsObjectController extends CollidablePhysics
{
	public PhysicsObjectController(GameObject obj,GameObject universe, IGameEngine eng) 
	{
		super(obj, universe, eng);
	}
}
