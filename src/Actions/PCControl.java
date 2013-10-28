package Actions;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import Engine.IGameEngine;
import GameObjects.GameObject;
import InputCallbacks.KeyEventListener;
import InputCallbacks.MouseEventListener;

// should allow for basic wasd movement. no mouse yet. 
// right now it just adds forces in different directions according to keypresses.
// this makes it so if to keys are pressed the object may move faster than 
// if only one key is pressed(will fix later). Just testing user input and 
// adding actions to GameObjects. 

public class PCControl extends PhysicsObjectController implements KeyEventListener, MouseEventListener
{

	public PCControl(GameObject obj, GameObject universe, IGameEngine eng) 
	{
		super(obj, universe, eng); 
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performAction() 
	{
		super.performAction();
		
		processKeyEvents();
	}
	// prob should make an event queue for this
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
	
	@Override
	public void keyPressed(int keyPressed)
	{
		if(keyPressed == FORWARD_KEY)
		{
			applyForce(new Vector2f(0,forceScale));
		}
		else if(keyPressed == REVERSE_KEY)
		{
			applyForce(new Vector2f(0,-forceScale));
		}
		else if(keyPressed == LEFT_KEY)
		{
			applyForce(new Vector2f(-forceScale,0));
		}
		else if(keyPressed == RIGHT_KEY)
		{
			applyForce(new Vector2f(forceScale,0));
		}
		else if(keyPressed == ACTION_KEY)
		{
			// not sure yet
		}
		
	}
	@Override
	public void keyReleased(int keyReleased)
	{
		if(keyReleased == FORWARD_KEY)
		{
			removeForce(new Vector2f(0,forceScale));
		}
		else if(keyReleased == REVERSE_KEY)
		{
			removeForce(new Vector2f(0,-forceScale));
		}
		else if(keyReleased == LEFT_KEY)
		{
			removeForce(new Vector2f(-forceScale,0));
		}
		else if(keyReleased == RIGHT_KEY)
		{
			removeForce(new Vector2f(forceScale,0));
		}
		else if(keyReleased == ACTION_KEY)
		{
			// not sure yet
		}
	}
	@Override
	public void buttonPressed(int event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonReleased(int event) {
		// TODO Auto-generated method stub
		
	}
	
	public void setForceScale(float scale)
	{
		this.forceScale = scale;
	}
	public float getForceScale()
	{
		return forceScale;
	}
	 
	int FORWARD_KEY = Keyboard.KEY_W;
	int REVERSE_KEY = Keyboard.KEY_S;
	int LEFT_KEY    = Keyboard.KEY_A;
	int RIGHT_KEY   = Keyboard.KEY_D;
	int ACTION_KEY  = Keyboard.KEY_E;
	
	float forceScale = 5; // amount of force to apply


}
