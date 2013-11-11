package Actions;

import org.lwjgl.util.vector.Vector2f;

import Engine.IGameEngine;
import GameObjects.GameObject;

// moves camera to halfway between mouse and chacter and zooms in and out based on mouse.
public class CameraController implements Action 
{

	public CameraController(GameObject universe, GameObject objToFollow, IGameEngine game) 
	{
		this.universe = universe;

		universe.translate(-objToFollow.getGlobalX(), -objToFollow.getGlobalY());
		this.objToFollow = objToFollow;
		this.game = game;
	}
	
	@Override
	public void performAction()
	{
		universe.translate(prevX, prevY);
		mouseLoc.x  = game.GetMouseX()/2;
		mouseLoc.y = game.GetMouseY()/2;
		universe.translate(-mouseLoc.x-objToFollow.getGlobalX(), -mouseLoc.y - objToFollow.getGlobalY());
		velocity.x = -objToFollow.getTranslationalVelocity().x;
		velocity.y = -objToFollow.getTranslationalVelocity().y;
		universe.setTranslationalVelocity(velocity);
		prevX = mouseLoc.x;
		prevY = mouseLoc.y;
		
		mouseLoc.scale(2);
		scale = 1 - mouseLoc.length() + .2f;
		if(scale > .8)
		{
			scale = .8f;
		}
		else if(scale < .5)
		{
			scale = .5f;
		}
		universe.scale(1/prevScale, 1/prevScale);
		universe.scale(scale, scale);
		prevScale = scale;
	}
	Vector2f mouseLoc = new Vector2f();
	float prevX = 0;
	float prevY = 0;
	float scale = 1;
	float prevScale = 1;
	
	Vector2f velocity = new Vector2f();
	GameObject universe;
	GameObject objToFollow;
	IGameEngine game;
}
