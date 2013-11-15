package Drawing;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import Engine.IGameEngine;
import GameObjects.GameObject;
import TextureEngine.ITextureEngine;
/**
 * 
 * add this to universe and the camera will appear to follow the object objToFollow
 *
 */
public class CameraView implements DrawObject
{

	public CameraView(GameObject obj, ITextureEngine eng) 
	{
		this.eng = eng;
		this.obj = obj;
		toFollow = null;
		loc = new Vector2f();
	}
	
	@Override
	public void draw(Matrix4f interpolator)
	{
		float[] coors = eng.ScaleWindowCoordinates(Mouse.getX(), Mouse.getY());
		loc.x = coors[0];
		loc.y = coors[1];
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
	
	
	float distanceFactor = 2;
	float scaleDistance = .8f;
	float maxScale = 3;
	float scale = 1;
	float prevScale = 1;
	GameObject obj;
	GameObject toFollow;
	
	ITextureEngine eng;
}

