package Actions;

import Engine.IGameEngine;
import GameObjects.GameObject;

// controls PhysicsObjects by applying forces to them
public abstract class PhysicsObjectController extends Physics
{
	public PhysicsObjectController(GameObject obj, IGameEngine eng, float deltaT) 
	{
		super(obj, eng, deltaT);
		// TODO Auto-generated constructor stub
	}
}
