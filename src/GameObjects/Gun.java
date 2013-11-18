package GameObjects;

import Actions.Action;
import TextureEngine.ITexture;

public abstract class Gun extends Entity
{

	public Gun(Universe universe, Player player) {
		super(universe);
		// TODO Auto-generated constructor stub.
		this.player = player;
	}
	
	public abstract void fireGun();	
	public abstract void stopFiring();
	// reload the gun
	public void reload() 
	{
		if(!reloading && clips > 0)
		{
			reloading = true;
			startReloadTime = universe.getGameEngine().GetTime();
			ammo = 0;
			
			Action action = new Action() {

				@Override
				public void performAction() 
				{
					long currentTime = universe.getGameEngine().GetTime() - startReloadTime;
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
	public int getAmmo()
	{
		return ammo;
	}
	public int getMaxAmmo()
	{
		return ammo;
	}
	
	Player player;
	
	float rateOfFire = 1; // shots per second
	float projectileSpeed = 10; // units per second
	int ammo = 10;  // ammount of ammo
	int maxAmmo = 10; // amount of ammo the gun holds
	int clips = 0; // number of clips
	long reloadTime = 1000; // time it takes to reload
	long startReloadTime;
	long startFiringTime;
	boolean reloading = false;
	boolean firing = false;
	ITexture icon;
	ITexture texture;
}
