package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import Actions.Action;
import AudioEngine.ISound;
import Drawing.SimpleDraw;

public class HandGun extends Gun
{

	public HandGun(Universe universe, Player player) {
		super(universe, player);
		// TODO Auto-generated constructor stub
		clips = 1;
		rateOfFire = 2;
	}

	@Override
	public void createObject(Universe universe) 
	{
		texture = universe.getTextureEngine().LoadTexture("gfx/Projectiles/HandGun.png", 0);
		icon = universe.getTextureEngine().LoadTexture("gfx/Icons/Guns/HandGunIcon.png", 0);
		SimpleDraw gun = new SimpleDraw(texture);
		gun.setScale(.3f, .3f);
		gun.setOffset(-.6f, 0);
		rootNode.setDrawingInterface(gun);
		
		
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
	public void fireGun() 
	{
		// bug: if you are out of ammo, there is no delay between "clicks."
		// here is some different code to fix that, we may want to generalize this and move it to Gun?
		
		if (universe.getGameEngine().GetTime() - lastTriggerTime > (1000/rateOfFire))
		{
			// now we can *try* shoot.
			// if no ammo, we make a click.
			// we apply the same delay to shooting as we do to "pulling the trigger"
			
			lastTriggerTime = universe.getGameEngine().GetTime();
			
			if (ammo > 0)
			{
				if(fireSound != null)
					fireSound.Play();
				
				float orientation = rootNode.getOrientationWrt(universe.getHandle());
				
				Vector2f velocity = new Vector2f((float)Math.cos(orientation) + (float)(Math.random()-.5)*.2f, 
												(float)Math.sin(orientation) + (float)(Math.random()-.5)*.2f);
				
				velocity.scale(projectileSpeed);
				
				HandGunProjectile bullet = new HandGunProjectile(universe, player);
				bullet.setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
				bullet.setVelocity(velocity);
				bullet.setTimeToLive(1000);
				universe.addEntity(bullet);
				ammo -= 1;
			}
			else
			{
				if (outOfAmmoSound != null)
					outOfAmmoSound.Play();
			}
		}
		
		/*
		if(ammo > 0 && !firing)
		{
			firing = true;
			float orientation = rootNode.getOrientationWrt(universe.getHandle());
			
			Vector2f velocity = new Vector2f((float)Math.cos(orientation) + (float)(Math.random()-.5)*.2f, 
											(float)Math.sin(orientation) + (float)(Math.random()-.5)*.2f);
			
			velocity.scale(projectileSpeed);
			
			HandGunProjectile bullet = new HandGunProjectile(universe, player);
			bullet.setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
			bullet.setVelocity(velocity);
			bullet.setTimeToLive(1000);
			universe.addEntity(bullet);
			ammo -= 1;
			
			startFiringTime = universe.getGameEngine().GetTime();
			Action timer = new Action()
			{

				@Override
				public void performAction() 
				{
					long currenttime = universe.getGameEngine().GetTime() - startFiringTime;
					if(currenttime > (long)1000/rateOfFire)
					{
						firing = false;
						rootNode.removeAction(this);
					}
				}
				
			};
			
			rootNode.addAction(timer);
		}
		else if(ammo <= 0 && outOfAmmoSound != null)
		{
			outOfAmmoSound.Play();
		}
		*/
	}

	@Override
	public void stopFiring() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reload()
	{
		super.reload();
		
		clips ++;
	}
	
	ISound fireSound;
	ISound outOfAmmoSound;
	
	long	lastTriggerTime=0;
}
