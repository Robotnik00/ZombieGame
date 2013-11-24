package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import TextureEngine.ITexture;

import Actions.Action;
import AudioEngine.ISound;
import Drawing.SimpleDraw;

public class MachineGun extends Gun
{

	public MachineGun(Universe universe, Player player) {
		super(universe, player);
		// TODO Auto-generated constructor stub
		clips = 1;
		rateOfFire = 20;
		ammo = 50;
		maxAmmo = 50;
	}

	@Override
	public void createObject(Universe universe) 
	{
		machinegun = universe.getTextureEngine().LoadTexture("gfx/Characters/player_machinegun.png", 0);
		
		
		try 
		{
			fireSound = universe.getAudioEngine().LoadSound("snd/guns/pew.wav");
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
	public void destroy() 
	{
		
	}


	
	@Override
	public void reload()
	{
		super.reload();
		
		clips ++;
	}

	@Override
	protected void onFire() 
	{
		makeSound++;
		if(fireSound != null && makeSound > 1)
		{
			fireSound.Play();
			makeSound = 0;
		}
		
		float orientation = rootNode.getOrientationWrt(universe.getHandle());
		
		Vector2f velocity = new Vector2f((float)Math.cos(orientation) + (float)(Math.random()-.5)*.4f, 
										(float)Math.sin(orientation) + (float)(Math.random()-.5)*.4f);
		
		velocity.scale(projectileSpeed);
		
		HandGunProjectile bullet = new HandGunProjectile(universe, player);
		bullet.setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
		bullet.setVelocity(velocity);
		bullet.setTimeToLive(800);
		bullet.setDP(damage*damageMultiplier);
		universe.addEntity(bullet);
	}

	@Override
	public void select() {
		player.getDrawingInterface().setTexture(machinegun);
	}
	ITexture machinegun;
	private int makeSound = 0;
}