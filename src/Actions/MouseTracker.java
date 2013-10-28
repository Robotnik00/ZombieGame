package Actions;

import Engine.IGameEngine;
import GameObjects.GameObject;

public class MouseTracker implements Action
{
	public MouseTracker(GameObject obj, IGameEngine game)
	{
		this.obj = obj;
		this.game = game;
	}
	
	@Override
	public void performAction()
	{	
		float mouseAngle = (float)Math.atan2(game.GetMouseY() - obj.getGlobalY(), game.GetMouseX() - obj.getGlobalX());
		float objAngle = obj.getGlobalOrientation();
		float rotation = mouseAngle - objAngle;
		
		obj.rotate(rotation);
		
		
	}
	GameObject obj;
	IGameEngine game;
}
