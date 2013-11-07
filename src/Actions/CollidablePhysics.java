package Actions;

import java.util.ArrayList;

import org.lwjgl.examples.spaceinvaders.Game;
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
		
		for(int i = 0; i < collisions.length; i++)
		{
			collisions[i].setLocalX(collisions[i].getPrevX());
			collisions[i].setLocalY(collisions[i].getPrevY());
		}
		if(collisions.length > 0)
		{
			Vector2f velocity = collisions[0].getTranslationalVelocity();
			if(!collisions[0].isStatic()) // object is colliding with another object
			{
				collisions[0].setTranslationalVelocity(obj.getTranslationalVelocity());
				obj.setTranslationalVelocity(velocity);
			}
			else // object is colliding with a wall.
			{
				// this prob could be handled better but it will work for now.
				obj.setLocalX(obj.getPrevX());
				obj.setLocalY(obj.getPrevY());
				obj.setTranslationalVelocity(zero);
			}
		}
		prevX = obj.getLocalX();
		prevY = obj.getLocalY();
	}
	

	float prevX = 0;
	float prevY = 0;
	GameObject universe;
	static final Vector2f zero = new Vector2f();
}
