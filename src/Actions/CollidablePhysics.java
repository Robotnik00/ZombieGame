package Actions;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;

import Utility.CollisionDetection;

import Engine.IGameEngine;
import GameObjects.GameObject;


// not rly sure if collisions should be actions yet or if we should do an event queue of some sort.
// if we use events than i think we can make collision detection take half as long 
// but w/e its only a constant factor
public class CollidablePhysics extends Physics
{

	public CollidablePhysics(GameObject obj, GameObject universe, IGameEngine eng) 
	{
		super(obj, eng);
		this.universe = universe;
	}
	
	@Override
	public void performAction()
	{
		super.performAction();
		GameObject[] collisions = CollisionDetection.getCollisions(obj, universe);
		if(collisions.length > 0)
		{
			Vector2f velocity = obj.getTranslationalVelocity();
			velocity.negate();
			obj.setTranslationalVelocity(velocity);
		}
		
	}
	

	
	GameObject universe;
}
