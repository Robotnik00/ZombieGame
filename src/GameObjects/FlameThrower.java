package GameObjects;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Actions.TimeToLive;
import TextureEngine.ITexture;

public class FlameThrower extends Gun
{


	public FlameThrower(Universe universe, Player player) {
		super(universe, player);
		rateOfFire = 50;
		projectileSpeed = 4;
		maxAmmo = 100;
		ammo = 100;
	}

	
	@Override
	public void createObject(Universe universe) 
	{
		flamethrower = universe.getTextureEngine().LoadTexture("gfx/Characters/player_flamethrower.png", 0);
		
		
		
		try 
		{
			fireSound = universe.getAudioEngine().LoadSound("snd/guns/flamethrower2.wav");
			outOfAmmoSound = universe.getAudioEngine().LoadSound("snd/guns/click2.wav");
		}
		catch (Exception e) 
		{
			fireSound = null;
			outOfAmmoSound = null;
			universe.getGameEngine().LogMessage(
					"HandGun: Couldn't load 'snd/guns/pew.wav', 'snd/guns/click2.wav'");
			//e.printStackTrace();
		}
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onFire() 
	{
		makeSound++;
		if(fireSound != null && makeSound > 10)
		{
			fireSound.Play();
			makeSound = 0;
		}
		float orientation = rootNode.getOrientationWrt(universe.getHandle());
		
		Vector2f velocity = new Vector2f((float)Math.cos(orientation) + (float)(Math.random()-.5)*.4f, 
										(float)Math.sin(orientation) + (float)(Math.random()-.5)*.4f);
		
		velocity.scale(projectileSpeed);
		
		Flame flame = new Flame(universe, player);
		flame.setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
		flame.setVelocity(velocity);
		flame.setTimeToLive(1000);
		universe.addEntity(flame);
	}
	
	@Override
	public void select() {

		player.getDrawingInterface().setTexture(flamethrower);
	}
	
	ITexture flamethrower;
	private int makeSound = 0;
}
