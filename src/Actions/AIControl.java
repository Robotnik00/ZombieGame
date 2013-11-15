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
		if(target != null)
		{
			float targetX = target.getLocalX();
			float targetY = target.getLocalY();
			float objX = obj.getLocalX();
			float objY = obj.getLocalY();
			
			
			float orientation = character.getOrientation();
			float deltaTarget = (float)Math.atan2(targetY - objY, targetX - objX);
			
			orientation = deltaTarget - orientation;
			
			character.setOrientation(orientation);
			
			
			forward.x = (float)Math.cos(deltaTarget);
			forward.y = (float)Math.sin(deltaTarget);
			setAppliedForce(forward);
			
		}
	}
	
	
	public void setTarget(GameObject target)
	{
		this.target = target;
	}
	
	Vector2f forward;
	
	GameObject target = null;
	Zombie character;
}
