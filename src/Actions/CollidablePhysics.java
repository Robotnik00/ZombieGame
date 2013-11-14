package Actions;

import java.util.ArrayList;

import org.lwjgl.examples.spaceinvaders.Game;
//import org.lwjgl.examples.spaceinvaders.Game;
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
// right now collisions are modeled as if just a collision between two points even tho bounds are a rectangle.
// need a better response to collisions with walls
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
		Vector2f moveTotal = new Vector2f(0.0f, 0.0f);
		Vector2f move;
		
		for (int i=0; i < collisions.length; i++)
		{
			move = obj.getBoundingBox().solveCollision(collisions[i].getBoundingBox());
			moveTotal.x += move.x;
			moveTotal.y += move.y;
		}
		obj.translate(moveTotal.x, moveTotal.y);
		
		
		/*
		for(int i = 0; i < collisions.length; i++)
		{
			collisions[i].setLocalX(collisions[i].getPrevX());
			collisions[i].setLocalY(collisions[i].getPrevY());
		}
		if(collisions.length > 0)
		{
			if(!collisions[0].isStatic()) // object is colliding with another object
			{
				
				u1.x = obj.getTranslationalVelocity().x;
				u1.y = obj.getTranslationalVelocity().y;
				u2.x = collisions[0].getTranslationalVelocity().x;
				u2.y = collisions[0].getTranslationalVelocity().y;

				v1.x = u1.x;
				v1.y = u1.y;
				v2.x = u2.x;
				v2.y = u2.y;
				v1.scale((obj.getMass() - collisions[0].getMass())/(obj.getMass()+collisions[0].getMass()));
				v2.scale(2*collisions[0].getMass()/(obj.getMass() + collisions[0].getMass()));
				Vector2f.add(v1, v2, result);
				obj.setTranslationalVelocity(result);
				v1.x = u1.x;
				v1.y = u1.y;
				v2.x = u2.x;
				v2.y = u2.y;
				v2.scale((collisions[0].getMass()-obj.getMass())/(obj.getMass()+collisions[0].getMass()));
				v1.scale(2*obj.getMass()/(obj.getMass() + collisions[0].getMass()));
				Vector2f.add(v1, v2, result);
				collisions[0].setTranslationalVelocity(result);				 
			}
			else // object is colliding with a wall.
			{
				// this prob could be handled better but it will work for now.
				obj.setLocalX(obj.getPrevX());
				obj.setLocalY(obj.getPrevY());
				obj.setTranslationalVelocity(zero);
			}	
		}
		*/
		
		prevX = obj.getLocalX();
		prevY = obj.getLocalY();
	}
	
    Vector2f u1 = new Vector2f();
    Vector2f u2 = new Vector2f();
    Vector2f v1 = new Vector2f();
    Vector2f v2 = new Vector2f();
    Vector2f result = new Vector2f();
    Vector2f prevVelocity = new Vector2f();
    
	float prevX = 0;
	float prevY = 0;
	GameObject universe;
	static final Vector2f zero = new Vector2f();
}
