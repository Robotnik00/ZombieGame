package Actions;

import Engine.IGameEngine;
import GameObjects.GameObject;

public class CollidablePhysics extends Physics
{

	public CollidablePhysics(GameObject obj, IGameEngine eng) 
	{
		super(obj, eng);
		// TODO Auto-generated constructor stub
	}
	
	private native void processCollisions();
	
}
