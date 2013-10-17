package Actions;

import Engine.IGameEngine;
import GameObjects.GameObject;

// controls PhysicsObjects by applying forces to them
public abstract class PhysicsObjectController extends CollidablePhysics
{
	public PhysicsObjectController(GameObject obj, IGameEngine eng) 
	{
		super(obj, eng);
		this.eng = eng;
		// TODO Auto-generated constructor stub
	}
	IGameEngine eng;
}
