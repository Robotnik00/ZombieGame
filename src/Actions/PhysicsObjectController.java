package Actions;

import Engine.IGameEngine;
import GameObjects.GameObject;
/**
*
* A physics object that is controlled by some outside forces
*/
public abstract class PhysicsObjectController extends CollidablePhysics
{
	public PhysicsObjectController(GameObject obj,GameObject universe, IGameEngine eng) 
	{
		super(obj, universe, eng);
		// TODO Auto-generated constructor stub
	}
}
