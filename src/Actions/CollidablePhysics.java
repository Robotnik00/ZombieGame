package Actions;

import GameObjects.GameObject;

public class CollidablePhysics extends Physics
{

	public CollidablePhysics(GameObject obj, float deltaT) 
	{
		super(obj, deltaT);
		// TODO Auto-generated constructor stub
	}
	
	private native void processCollisions();
	
}
