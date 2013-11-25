package GameObjects;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class Shotgun extends Gun
{
	public Shotgun(Universe universe, Player player)
	{
		super(universe, player);
		
		blast = new ArrayList<HandGunProjectile>();
		for(int i = 0; i < numProjectiles; i++)
		{
			blast.add(new HandGunProjectile(universe, player));
		}
		velocity = new Vector2f();
	}

	@Override
	protected void onFire() 
	{

		if(fireSound != null)
			fireSound.Play();
		
		for(int i = 0; i < numProjectiles; i++)
		{
			float orientation = rootNode.getOrientationWrt(universe.getHandle());
			
			velocity.x = (float)Math.cos(orientation) + (float)(Math.random()-.5)*1f;
			velocity.y = (float)Math.sin(orientation) + (float)(Math.random()-.5)*1f;
			
			velocity.scale(projectileSpeed);
			//HandGunProjectile bullet = new HandGunProjectile(universe, player);
			blast.get(i).setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
			blast.get(i).setVelocity(velocity);
			blast.get(i).setTimeToLive(200);
			blast.get(i).setDP(damage*damageMultiplier);
			universe.addEntity(blast.get(i));
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
	Vector2f velocity;
	int numProjectiles = 20;
	ArrayList<HandGunProjectile> blast;
	@Override
	public void select() {
		player.getDrawingInterface().setTexture(shotgun);
	}
	ITexture shotgun;
}
