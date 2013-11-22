package GameObjects;

import org.lwjgl.util.vector.Vector2f;

public class FlameThrower extends Gun
{


	public FlameThrower(Universe universe, Player player) {
		super(universe, player);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
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
	
	@Override
	public void select() {
		
	}

}
