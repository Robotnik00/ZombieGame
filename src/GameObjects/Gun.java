package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import Actions.Action;
import AudioEngine.ISound;
import TextureEngine.ITexture;

public abstract class Gun extends Entity
{

	public Gun(Universe universe, Player player) {
		super(universe);
		// TODO Auto-generated constructor stub.
		this.player = player;
	}
	
	public void fireGun()
	{
		if (universe.getTime() - lastTriggerTime > (1000/rateOfFire))
		{
			lastTriggerTime = universe.getTime();
			
			if (ammo > 0)
			{
		
				onFire();
				ammo -= 1;
			}
			else
			{
				if (outOfAmmoSound != null)
					outOfAmmoSound.Play();
			}
		}
	}
	
	protected abstract void onFire();
	
	// reload the gun
	public void reload() 
	{
		if(!reloading && clips > 0)
		{
			reloading = true;
			startReloadTime = universe.getTime();
			ammo = 0;
			
			Action action = new Action() {

				@Override
				public void performAction() 
				{
					long currentTime = universe.getTime() - startReloadTime;
					if(currentTime > reloadTime)
					{
						ammo = maxAmmo;
						clips -= 1;
						rootNode.removeAction(this);
						reloading = false;
					}
				}

			};

			rootNode.addAction(action);
		}
	}
	public ITexture getIcon()
	{
		return icon;
	}
	public boolean isReloading()
	{
		return reloading;
	}
	public void addAmmo(int ammo)
	{
		this.ammo += ammo;
	}
	public int getAmmo()
	{
		return ammo;
	}
	public int getMaxAmmo()
	{
		return ammo;
	}
	public void setDamageMultiplier(float dp)
	{
		damageMultiplier = dp;
	}
	// used to display the selected gun.
	public void setPowerup(Powerup p)
	{
		itsPowerup = p;
	}
	public Powerup getPowerup()
	{
		return itsPowerup;
	}
	
	public abstract void select();

	
	Player player;
	
	float rateOfFire = 1; // shots per second
	float projectileSpeed = 10; // units per second
	int ammo = 10;  // ammount of ammo
	int maxAmmo = 10; // amount of ammo the gun holds
	int clips = 0; // number of clips
	long reloadTime = 1000; // time it takes to reload
	long startReloadTime;
	long startFiringTime;
	long	lastTriggerTime=0;
	float damageMultiplier = 1;
	float damage = .2f;
	boolean reloading = false;
	boolean firing = false;
	ITexture icon;
	ITexture texture;
	ISound fireSound;
	ISound outOfAmmoSound;
	Powerup itsPowerup = null;
}
