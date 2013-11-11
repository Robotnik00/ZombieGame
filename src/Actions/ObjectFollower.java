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
		
		Vector2f loc = new Vector2f((toFollow.getGlobalX() + eng.GetMouseX())/2,(toFollow.getGlobalY() + eng.GetMouseY())/2);
		
		if(loc.lengthSquared() > deadZone)
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
	// the distance from center of screen before the screen moves.
	public void setDeadZone(float deadZone)
	{
		this.deadZone = deadZone;
	}
	// the amount of force applied to move the screen
	// normally this should be set equal to the max velocity of the object to follow
	public void setForceScale(float force)
	{
		this.forceScale = force;
	}
	
	float deadZone = 0.00005f;
	float forceScale = 3f;
	
	GameObject toFollow;
}
