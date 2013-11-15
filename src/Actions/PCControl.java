package Actions;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Engine.IGameEngine;
import GameObjects.GameObject;
import GameObjects.HandGunProjectile;
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

	public PCControl(GameObject obj, Universe universe, IGameEngine eng) 
	{
		super(obj, universe.getHandle(), eng);
		
		
		this.universe = universe;
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
		if(keyPressed == MOVE_UP)
		{
		}
		else if(keyPressed == MOVE_DOWN)
		{
		}
		else if(keyPressed == MOVE_LEFT)
		{
		}
		else if(keyPressed == MOVE_RIGHT)
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

		if(keyReleased == MOVE_UP)
		{
			//removeForce(unitDirection);
		}
		else if(keyReleased == MOVE_DOWN)
		{
			//removeForce(new Vector2f(-forceScale,0));
		}
		else if(keyReleased == MOVE_LEFT)
		{
			//removeForce(new Vector2f(0,forceScale));
		}
		else if(keyReleased == MOVE_RIGHT)
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
			force.x = unitDirection.x;
			force.y = unitDirection.y;
			force.scale(forceScale);
			appliedForce = unitDirection;
		}
		if(event.getButton() == FIRE_BUTTON)
		{	
			unitDirection.x = eng.GetMouseX() - obj.getGlobalX();
			unitDirection.y = eng.GetMouseY() - obj.getGlobalY();
			unitDirection.normalise();
			HandGunProjectile projectile = new HandGunProjectile(universe);		
			Vector2f projectileVelocity = new Vector2f(unitDirection);
			projectileVelocity.scale(10);
			projectileVelocity.x +=  (float)(Math.random()-.5)*2;
			projectileVelocity.y += (float)(Math.random()-.5)*2;
			projectile.setVelocity(projectileVelocity);
			
			projectile.getRootNode().setLocalX(obj.getLocalX() + unitDirection.x/2.2f);
			projectile.getRootNode().setLocalY(obj.getLocalY() + unitDirection.y/2.2f);
			
			universe.addEntity(projectile);
			projectile.setTimeToLive(1000);
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
			unitDirection.normalise();
			force.x = unitDirection.x;
			force.y = unitDirection.y;
			force.scale(forceScale);
			//obj.translate(unitDirection.x/10, unitDirection.y/10);
			appliedForce = force;
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
	Vector2f force = new Vector2f();
	
	int MOVE_UP    = Keyboard.KEY_W;
	int MOVE_DOWN  = Keyboard.KEY_S;
	int MOVE_LEFT  = Keyboard.KEY_A;
	int MOVE_RIGHT = Keyboard.KEY_D;
	int ACTION_KEY = Keyboard.KEY_E;
	
	
	int MOVE_BUTTON = 2;
	boolean moveButtonDown = false;
	
	int FIRE_BUTTON = 1;
	boolean fileButtonDown = false;
	
	
	float forceScale = 1; // amount of force to apply
	
	Universe universe;
}
