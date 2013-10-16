package Actions;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import Engine.IGameEngine;
import GameObjects.GameObject;

// should allow for basic wasd movement. no mouse yet. 
// right now it just adds forces in different directions according to keypresses.
// this makes it so if to keys are pressed the object may move faster than 
// if only one key is pressed(will fix later). Just testing user input and 
// adding actions to GameObjects. 

public class PCControl extends PhysicsObjectController
{

	public PCControl(GameObject obj, IGameEngine eng, float deltaT) 
	{
		super(obj, eng, deltaT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performAction() 
	{
		super.performAction();
		
		processKeyEvents();
		processMouseEvents();
	}
	public void processKeyEvents()
	{
		int keyEvents[] = eng.GetKeyEvents();
		for(int i = 0; i < keyEvents.length; i++)
		{
			if(keyEvents[i] > 0)
			{
				keyPressed(keyEvents[i]);
			}
			else
			{
				keyReleased(keyEvents[i] * -1);
			}
		}
	}
	
	
	public void keyPressed(int keyPressed)
	{
		if(keyPressed == FORWARD_KEY)
		{
			applyForce(new Vector2f(0,1));
		}
		else if(keyPressed == REVERSE_KEY)
		{
			applyForce(new Vector2f(0,-1));
		}
		else if(keyPressed == LEFT_KEY)
		{
			applyForce(new Vector2f(-1,0));
		}
		else if(keyPressed == RIGHT_KEY)
		{
			applyForce(new Vector2f(1,0));
		}
		else if(keyPressed == ACTION_KEY)
		{
			// not sure yet
		}
		
	}
	
	public void keyReleased(int keyReleased)
	{
		if(keyReleased == FORWARD_KEY)
		{
			removeForce(new Vector2f(0,1));
		}
		else if(keyReleased == REVERSE_KEY)
		{
			removeForce(new Vector2f(0,-1));
		}
		else if(keyReleased == LEFT_KEY)
		{
			removeForce(new Vector2f(-1,0));
		}
		else if(keyReleased == RIGHT_KEY)
		{
			removeForce(new Vector2f(1,0));
		}
		else if(keyReleased == ACTION_KEY)
		{
			// not sure yet
		}
	}
	 
	public void processMouseEvents()
	{
		
	}
	

	int FORWARD_KEY = Keyboard.KEY_W;
	int REVERSE_KEY = Keyboard.KEY_S;
	int LEFT_KEY    = Keyboard.KEY_A;
	int RIGHT_KEY   = Keyboard.KEY_D;
	int ACTION_KEY  = Keyboard.KEY_E;
	
	float forceScale = 1; // amount of force to apply
}
