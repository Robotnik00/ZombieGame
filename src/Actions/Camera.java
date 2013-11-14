package Actions;

import org.lwjgl.util.vector.Vector2f;

import Engine.IGameEngine;
import GameObjects.GameObject;
/**
 * 
 * add this to universe and the camera will appear to follow the object objToFollow
 *
 */
public class Camera implements Action
{

	public Camera(GameObject obj, IGameEngine eng) 
	{
		this.eng = eng;
		this.obj = obj;
		toFollow = null;
	}
	
	@Override
	public void performAction()
	{
		if(toFollow != null)
		{
			Vector2f loc = new Vector2f(eng.GetMouseX(),eng.GetMouseY());
			
			
			
			
			Vector2f velocity = new Vector2f(toFollow.getTranslationalVelocity());
			velocity.scale(-1);
			obj.translate(-toFollow.getGlobalX() - eng.GetMouseX()/distanceFactor, -toFollow.getGlobalY() - eng.GetMouseY()/distanceFactor);
			obj.setTranslationalVelocity(velocity);
	
			prevScale = scale;
			if(loc.length() > scaleDistance)
			{
				scale = (float)1/(maxScale*(loc.length() - scaleDistance) + 1);
			
			}
			else
			{
				scale = 1;
			}
	
			obj.scale(1/prevScale, 1/prevScale);
			obj.scale(scale, scale);
		}
	}
	
	public void setFocus(GameObject obj)
	{
		this.toFollow = obj;
	}
	
	/**
	 * sets the distance the mouse must be in order to begin zooming out
	 * @param dist
	 */
	public void setScaleDistance(float dist)
	{
		this.scaleDistance = dist;
	}
	/**
	 * sets the % of distance between mouse and object to move the camera
	 * @param distFactor
	 */
	public void setTranslationDistance(float distFactor)
	{
		this.distanceFactor = distFactor;
	}
	
	
	
	float distanceFactor = 2;
	float scaleDistance = .8f;
	float maxScale = 3;
	float scale = 1;
	float prevScale = 1;
	GameObject obj;
	GameObject toFollow;
	
	IGameEngine eng;
}
