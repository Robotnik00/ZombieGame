package Actions;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Engine.IGameEngine;
import GameObjects.GameObject;
import GameObjects.HandGunProjectile;
import GameObjects.Player;
import GameObjects.Universe;
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

	public PCControl(Player player, IGameEngine eng) 
	{
		super(player.getRootNode(), player.getUniverse().getHandle(), eng);
		
		this.player = player;
	}

	@Override
	public void performAction() 
	{
		super.performAction();
		if(fileButtonDown)
		{
			player.getCurrentGun().fireGun();
		}
		
		
		unitDirection.x = mouseDirection.x + keyBoardDirection.x;
		unitDirection.y = mouseDirection.y + keyBoardDirection.y;
		
		if(unitDirection.length()>0)
			unitDirection.normalise();
		
		unitDirection.scale(forceScale);
		appliedForce.x = unitDirection.x;
		appliedForce.y = unitDirection.y;
	}
	
	@Override
	public void keyPressed(int keyPressed)
	{
		if(keyPressed == MOVE_UP)
		{
			keyBoardDirection.y += 1;
		}
		else if(keyPressed == MOVE_DOWN)
		{
			keyBoardDirection.y -= 1;
		}
		else if(keyPressed == MOVE_LEFT)
		{
			keyBoardDirection.x -= 1;
		}
		else if(keyPressed == MOVE_RIGHT)
		{
			keyBoardDirection.x += 1;
		}
		else if(keyPressed == ACTION_KEY)
		{
			// not sure yet
		}
		else if(keyPressed == RELOAD_KEY)
		{
			player.getCurrentGun().reload();
		}
		
	}
	@Override
	public void keyReleased(int keyReleased)
	{

		if(keyReleased == MOVE_UP)
		{
			keyBoardDirection.y -= 1;
		}
		else if(keyReleased == MOVE_DOWN)
		{
			keyBoardDirection.y += 1;
		}
		else if(keyReleased == MOVE_LEFT)
		{
			keyBoardDirection.x += 1;
		}
		else if(keyReleased == MOVE_RIGHT)
		{
			keyBoardDirection.x -= 1;
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
			
			unitDirection.x -= mouseDirection.x;
			unitDirection.y -= mouseDirection.y;
			mouseDirection.x = eng.GetMouseX() - obj.getGlobalX();
			mouseDirection.y = eng.GetMouseY() - obj.getGlobalY();
			mouseDirection.normalise();
			
			unitDirection.x += mouseDirection.x;
			unitDirection.y += mouseDirection.y;
			unitDirection.normalise();
			force.x = unitDirection.x;
			force.y = unitDirection.y;
			force.scale(forceScale);
			//appliedForce = unitDirection;
		}
		if(event.getButton() == FIRE_BUTTON)
		{	
			fileButtonDown = true;
		}
	}

	@Override
	public void buttonReleased(MouseEvent event) {
		if(event.getButton() == MOVE_BUTTON)
		{
			moveButtonDown = false;
			
			mouseDirection.x = 0;
			mouseDirection.y = 0;
		}
		if(event.getButton() == FIRE_BUTTON)
		{
			fileButtonDown = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		if(moveButtonDown)
		{	
			
			unitDirection.x -= mouseDirection.x;
			unitDirection.y -= mouseDirection.y;
			mouseDirection.x = eng.GetMouseX() - obj.getGlobalX();
			mouseDirection.y = eng.GetMouseY() - obj.getGlobalY();
			mouseDirection.normalise();
			unitDirection.x += mouseDirection.x;
			unitDirection.y += mouseDirection.y;
			
			unitDirection.normalise();
			unitDirection.normalise();
			force.x = unitDirection.x;
			force.y = unitDirection.y;
			force.scale(forceScale);
			appliedForce = force;
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
	
	Vector2f keyBoardDirection = new Vector2f();
	Vector2f mouseDirection = new Vector2f();
	Vector2f unitDirection = new Vector2f();
	Vector2f force = new Vector2f();
	
	int MOVE_UP    = Keyboard.KEY_W;
	int MOVE_DOWN  = Keyboard.KEY_S;
	int MOVE_LEFT  = Keyboard.KEY_A;
	int MOVE_RIGHT = Keyboard.KEY_D;
	int ACTION_KEY = Keyboard.KEY_E;
	int RELOAD_KEY = Keyboard.KEY_R;
	
	int MOVE_BUTTON = 2;
	boolean moveButtonDown = false;
	
	int FIRE_BUTTON = 1;
	boolean fileButtonDown = false;
	
	
	float forceScale = 2; // amount of force to apply
	
	Player player;
	
}
