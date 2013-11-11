package Actions;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Engine.IGameEngine;
import GameObjects.GameObject;
import InputCallbacks.KeyEventListener;
import InputCallbacks.MouseEvent;
import InputCallbacks.MouseEventListener;

/**
*
* allows for a PC to control an object with a keyboard and mouse ect
*/

// havent done key input yet, but that should be easy.

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
		
		//processKeyEvents();
	}
	
	@Override
	public void keyPressed(int keyPressed)
	{
		unitDirection.x = eng.GetMouseX() - obj.getGlobalX();
		unitDirection.y = eng.GetMouseY() - obj.getGlobalY();
		unitDirection.normalise();
		if(keyPressed == FORWARD_KEY)
		{
			
		}
		else if(keyPressed == REVERSE_KEY)
		{
		}
		else if(keyPressed == LEFT_KEY)
		{
		}
		else if(keyPressed == RIGHT_KEY)
		{
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
			//removeForce(unitDirection);
		}
		else if(keyReleased == REVERSE_KEY)
		{
			//removeForce(new Vector2f(-forceScale,0));
		}
		else if(keyReleased == LEFT_KEY)
		{
			//removeForce(new Vector2f(0,forceScale));
		}
		else if(keyReleased == RIGHT_KEY)
		{
			//removeForce(new Vector2f(0,-forceScale));
		}
		else if(keyReleased == ACTION_KEY)
		{
			// not sure yet
		}
	}
	@Override
	public void buttonPressed(MouseEvent event) {
		// TODO Auto-generated method stub
		if(event.getButton() == MOVE_BUTTON)
		{
			moveButtonDown = true;
			unitDirection.x = eng.GetMouseX() - obj.getGlobalX();
			unitDirection.y = eng.GetMouseY() - obj.getGlobalY();
			unitDirection.normalise();
			unitDirection.scale(forceScale);
			appliedForce = unitDirection;
		}
	}

	@Override
	public void buttonReleased(MouseEvent event) {
		if(event.getButton() == MOVE_BUTTON)
		{
			moveButtonDown = false;
			appliedForce.x = 0;
			appliedForce.y = 0;
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		if(moveButtonDown)
		{
			unitDirection.x = eng.GetMouseX() - obj.getGlobalX();
			unitDirection.y = eng.GetMouseY() - obj.getGlobalY();
			unitDirection.normalise();
			unitDirection.scale(forceScale);
			appliedForce = unitDirection;
		}
		else 
		{
			appliedForce.x = 0;
			appliedForce.y = 0;
		}
	}

	public void setForceScale(float scale)
	{
		this.forceScale = scale;
	}
	public float getForceScale()
	{
		return forceScale;
	}	
	
	
	Vector2f unitDirection = new Vector2f(1,0);
	
	int FORWARD_KEY = Keyboard.KEY_W;
	int REVERSE_KEY = Keyboard.KEY_S;
	int LEFT_KEY    = Keyboard.KEY_A;
	int RIGHT_KEY   = Keyboard.KEY_D;
	int ACTION_KEY  = Keyboard.KEY_E;
	
	
	int MOVE_BUTTON = 2;
	boolean moveButtonDown = false;
	
	int FIRE_BUTTON = 1;
	boolean fileButtonDown = false;
	
	
	float forceScale = 1; // amount of force to apply



}
