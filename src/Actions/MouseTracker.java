package Actions;

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
		float rotation = mouseAngle - objAngle;
		
		obj.rotate(rotation);
	}
	GameObject obj;
	IGameEngine game;
}
