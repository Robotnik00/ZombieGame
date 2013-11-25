package Actions;

import org.lwjgl.util.vector.Vector2f;

import Engine.IGameEngine;
import GameObjects.Entity;
import GameObjects.GameObject;
import GameObjects.Zombie;
/**
*
* added to a character to make it controlled by a computer
*/
public class AIControl extends PhysicsObjectController
{

	public AIControl(GameObject obj, Zombie character, GameObject universe, IGameEngine eng) {
		super(obj, universe, eng);
		forward = new Vector2f(1, 0);
		this.character = character;
	}

	@Override
	public void performAction() 
	{
		super.performAction();
		if(target != null && !target.isDestroyed())
		{
			float targetX = target.getRootNode().getLocalX();
			float targetY = target.getRootNode().getLocalY();
			float objX = obj.getLocalX();
			float objY = obj.getLocalY();

			float orientation = character.getOrientation();
			
			float deltaTarget = (float)Math.atan2(targetY - objY, targetX - objX);
			
			orientation = deltaTarget - orientation;
			
			character.setOrientation(orientation);
			
			
			forward.x = (float)Math.cos(deltaTarget);
			forward.y = (float)Math.sin(deltaTarget);
			setAppliedForce(forward);
			appliedForce.scale(forceScale);
			float relX = target.getRootNode().getLocalX();
			float relY = target.getRootNode().getLocalY();
			if(Math.abs(obj.getLocalX() - relX) < .5f && Math.abs(obj.getLocalY() - relY) < .5f)
			{
				target.damage(dp);
			}
			
			
		}
		else
		{
			appliedForce.scale(0);
			
		}
	}
	
	
	public void setTarget(Entity target)
	{
		this.target = target;
	}
	public void setSpeed(float speed)
	{
		forceScale *= speed;
	}
	
	
	float forceScale = 1;
	Vector2f forward;
	
	Entity target = null;
	Zombie character;
	
	float dp = .01f;
}
