package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import TextureEngine.ITexture;

import Actions.Action;
import AudioEngine.ISound;
import Drawing.SimpleDraw;

public class HandGun extends Gun
{

	public HandGun(Universe universe, Player player) {
		super(universe, player);
		// TODO Auto-generated constructor stub
		clips = 1;
		rateOfFire = 4;
	}

	@Override
	public void createObject(Universe universe) 
	{
		handgun = universe.getTextureEngine().LoadTexture("gfx/Characters/player_base.png", 0);
		
		
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
		
		float orientation = rootNode.getOrientationWrt(universe.getHandle());
		
		Vector2f velocity = new Vector2f((float)Math.cos(orientation) + (float)(Math.random()-.5)*.2f, 
										(float)Math.sin(orientation) + (float)(Math.random()-.5)*.2f);
		
		velocity.scale(projectileSpeed);
		
		HandGunProjectile bullet = new HandGunProjectile(universe, player);
		bullet.setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
		bullet.setVelocity(velocity);
		bullet.setTimeToLive(1000);
		bullet.setDP(damage*damageMultiplier);
		universe.addEntity(bullet);
		
		ammo++; // a hack to make handgun have inf ammo
	}

	@Override
	public void select() 
	{
		player.getDrawingInterface().setTexture(handgun);
	}
	ITexture handgun;
}
