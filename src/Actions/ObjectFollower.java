package Actions;

import org.lwjgl.util.vector.Vector2f;

import Engine.IGameEngine;
import GameObjects.GameObject;
/**
 * 
 * add this to universe and the camera will appear to follow the object objToFollow
 *
 */
public class ObjectFollower extends Physics
{

	public ObjectFollower(GameObject thisObj, GameObject objToFollow, IGameEngine eng) {
		super(thisObj, eng);
		toFollow = objToFollow;
	}
	
	@Override
	public void performAction()
	{
		super.performAction();
		
		Vector2f loc = new Vector2f(toFollow.getGlobalX(), toFollow.getGlobalY());
		
		if(loc.length() > deadZone)
		{
			loc.normalise();
			loc.scale(forceScale);
			loc.negate();
			
			appliedForce = loc;
			
			
		}
		else
		{
			appliedForce.scale(0);
		}
		
		
	}
	
	float deadZone = 0.05f;
	float forceScale = 3f;
	
	GameObject toFollow;
}
