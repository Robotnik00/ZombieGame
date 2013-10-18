package Actions;

import Engine.IGameEngine;
import GameObjects.GameObject;

// controls PhysicsObjects by applying forces to them
public abstract class PhysicsObjectController extends CollidablePhysics
{
	public PhysicsObjectController(GameObject obj,GameObject universe, IGameEngine eng) 
	{
		super(obj, universe, eng);
		// TODO Auto-generated constructor stub
	}
}
