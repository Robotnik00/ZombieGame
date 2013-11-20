package Actions;

import org.lwjgl.input.Mouse;
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

	public Camera(GameObject obj, GameObject scaleNode, IGameEngine eng) 
	{
		this.eng = eng;
		this.obj = obj;
		this.scaleNode = scaleNode;
		toFollow = null;
		loc = new Vector2f();
	}
	
	@Override
	public void performAction()
	{
		if(toFollow != null)
		{
			loc.x = eng.GetMouseX();
			loc.y = eng.GetMouseY();
			obj.translate((-toFollow.getGlobalX() - eng.GetMouseX()/distanceFactor)/lag, (-toFollow.getGlobalY() - eng.GetMouseY()/distanceFactor)/lag);
			prevScale = scale;
			if(loc.length() > scaleDistance+deadZone)
			{
				if(scale > maxScale)
					scale *= scaleVelocity;
			}
			else if(loc.length() < scaleDistance - deadZone && scale < 1)
			{
				scale /= scaleVelocity;
			}
			
			scaleNode.scale(1/prevScale, 1/prevScale);
			scaleNode.scale(scale, scale);
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
	
	
	
	Vector2f loc;
	float lag = 2f;
	
	float distanceFactor = 2;
	float scaleDistance = .6f;
	float maxScale = .8f;
	float scale = 1;
	float prevScale = 1;
	float deadZone = .1f;
	float scaleVelocity = .98f; 
	
	GameObject obj;
	GameObject toFollow;
	GameObject scaleNode;
	
	IGameEngine eng;
}
