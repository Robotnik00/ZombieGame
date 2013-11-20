package GameObjects;

import org.lwjgl.util.vector.Vector2f;

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
		ammo = 100;
		maxAmmo = 100;
	}

	@Override
	public void createObject(Universe universe) 
	{
		texture = universe.getTextureEngine().LoadTexture("gfx/Projectiles/MachineGun.png", 0);
		icon = universe.getTextureEngine().LoadTexture("gfx/Icons/Guns/HandGunIcon.png", 0);
		SimpleDraw gun = new SimpleDraw(texture);
		gun.setScale(1f, 1f);
		gun.setOrientation((float)-Math.PI/2);
		gun.setOffset(0, -.2f);
		
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
	public void reload()
	{
		super.reload();
		
		clips ++;
	}

	@Override
	protected void onFire() 
	{
		
		float orientation = rootNode.getOrientationWrt(universe.getHandle());
		
		Vector2f velocity = new Vector2f((float)Math.cos(orientation) + (float)(Math.random()-.5)*.4f, 
										(float)Math.sin(orientation) + (float)(Math.random()-.5)*.4f);
		
		velocity.scale(projectileSpeed);
		
		HandGunProjectile bullet = new HandGunProjectile(universe, player);
		bullet.setStartingLoc(rootNode.getXWrt(universe.getHandle()), rootNode.getYWrt(universe.getHandle()));
		bullet.setVelocity(velocity);
		bullet.setTimeToLive(800);
		universe.addEntity(bullet);
	}
	
}