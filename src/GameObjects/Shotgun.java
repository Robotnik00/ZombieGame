package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class Shotgun extends Gun
{

	public Shotgun(Universe universe, Player player)
	{
		super(universe, player);
	}

	@Override
	protected void onFire() 
	{
		for(int i = 0; i < numProjectiles; i++)
		{
			float orientation = rootNode.getOrientationWrt(universe.getHandle());
			
			Vector2f velocity = new Vector2f((float)Math.cos(orientation) + (float)(Math.random()-.5)*1f, 
											(float)Math.sin(orientation) + (float)(Math.random()-.5)*1f);
			
			velocity.scale(projectileSpeed);
			
			HandGunProjectile bullet = new HandGunProjectile(universe, player);
			bullet.setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
			bullet.setVelocity(velocity);
			bullet.setTimeToLive(200);
			universe.addEntity(bullet);
		}
	}

	@Override
	public void createObject(Universe universe) 
	{

		rateOfFire = .5f;
		ammo = 10;
		maxAmmo = 10;
		
		shotgun = universe.getTextureEngine().LoadTexture("gfx/Characters/player_shotgun.png", 0);
		
		
		
		
		try 
		{
			fireSound = universe.getAudioEngine().LoadSound("snd/guns/shotgun.wav");
		}
		catch (Exception e) 
		{
			fireSound = null;
		}
	}

	@Override
	public void destroy() 
	{
		
	}

	int numProjectiles = 5;

	@Override
	public void select() {
		player.getDrawingInterface().setTexture(shotgun);
	}
	ITexture shotgun;
}
