package Actions;

import com.sun.java.util.jar.pack.PackerImpl;

import Engine.IGameEngine;
import GameObjects.GameObject;
/**
*
* makes the object point in the direction of the mouse
*/
public class MouseTracker extends Physics
{
	public MouseTracker(GameObject obj, IGameEngine game)
	{
		super(obj, game);
		this.obj = obj;
		this.game = game;
	}
	
	@Override
	public void performAction()
	{	
		super.performAction();
		
		float mouseAngle = (float)Math.atan2(game.GetMouseY() - obj.getGlobalY(), game.GetMouseX() - obj.getGlobalX());
		float objAngle = obj.getGlobalOrientation();
		mouseAngle = toStdAngle(mouseAngle);
		objAngle = toStdAngle(objAngle);
		float rotation = mouseAngle - objAngle;
		if(rotation > Math.PI)
		{
			rotation -= Math.PI * 2;
		}
		else if(rotation < -Math.PI)
		{
			rotation += Math.PI * 2;
		}
		rotation /= lag;
		
		obj.rotate(rotation);
	}
	public float toStdAngle(float angle)
	{
		if(angle < 0)
		{
			angle += Math.PI*2;
		}
		
		
		return angle;
	}
	float lag = 5;
	GameObject obj;
	IGameEngine game;
}
