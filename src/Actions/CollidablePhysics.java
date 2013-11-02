package Actions;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;

import Utility.CollisionDetection;

import Engine.IGameEngine;
import GameObjects.GameObject;


/**
*
* processes collisions and responds to them. 
* 
*/
// What needs to be done here:
//  - respond to collisions that are cause by rotating objects.
//  - make it so the collisions result in accurate velocity based on momentum, instead of just negating it.
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
