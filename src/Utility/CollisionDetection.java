package Utility;

import java.util.ArrayList;

import GameObjects.GameObject;

public class CollisionDetection 
{
	
	
	
	// O(nj^2) where n is the number of collidable objects and j is the avg number 
	// of nodes per collidable object. avg(n) because if two objs are not in the same proxemity
	// than their nodes arn't processed. Will support collisions betweem objects made of 
	// a combination of circles and OBBs but right now it only does AABBs. Note if j = 1 
	// its O(n).  This would be equivalent to having simple AABBs as bounding boxes.
	static public GameObject[] getCollisions(GameObject obj, GameObject universe)
	{
		ArrayList<GameObject> dcollisions = new ArrayList<GameObject>();
		
		getCollisionsRec(obj, universe, dcollisions);
		
		GameObject[] collisions = new GameObject[dcollisions.size()];
		
		dcollisions.toArray(collisions);
		
		return collisions;
	}
	// traverse tree and find all objects that collide with obj
	static private void getCollisionsRec(GameObject obj,GameObject universe, ArrayList<GameObject> collisions)
	{
		for(int i = 0; i < universe.getChildren().size(); i++)
		{
			GameObject obj2 = universe.getChildren().get(i);
			if(obj2 != obj && obj2.getCollidable())
			{
				if(collidesWith(obj, obj2))
				{
					collisions.add(obj2);
				}
				getCollisionsRec(obj, obj2, collisions);
			}
		}
	}
	
	// tested this and it works
	// checks if obj1 or any of its children collide with obj2 or any of its children
	static public boolean collidesWith(GameObject obj1, GameObject obj2)
	{
		// if obj1 is not in proxemity of obj2 return false. no intersection. 
		if(obj1.getProxemityBounds() == null || obj2.getProxemityBounds() == null ||
				obj1.getProxemityBounds().intersects(obj2.getProxemityBounds()))
		{
			if(obj1.getBoundingBox() != null && obj2.getBoundingBox() != null && 
					obj1.getBoundingBox().intersects(obj2.getBoundingBox()))
			{
				return true;
			}
			else // check for collisions between children
			{
				System.out.printf("processing children\n");
				for(int i = 0; i < obj1.getChildren().size(); i++) 
				{
					if(collidesWith(obj1.getChildren().get(i), obj2))
					{
						return true;
					}
				}
				for(int i = 0; i < obj2.getChildren().size(); i++)
				{
					if(collidesWith(obj1, obj2.getChildren().get(i)))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
