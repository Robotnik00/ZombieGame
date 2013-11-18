package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import Actions.Action;
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
		
		
	}

	@Override
	public void destroy() 
	{
		
	}


	@Override
	public void fireGun() 
	{
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

}
