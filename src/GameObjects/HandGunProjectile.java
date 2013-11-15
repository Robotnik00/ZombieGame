package GameObjects;

import Actions.ProjectileAction;
import Actions.TimeToLive;
import Drawing.SimpleDraw;
import Geometry.AABB;

public class HandGunProjectile extends Projectile
{

	public HandGunProjectile(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		
		SimpleDraw drawProjectile = new SimpleDraw(universe.getTextureEngine().LoadTexture("gfx/Projectiles/HandGunProjectile.png", 0));
		drawProjectile.setScale(.05f, .05f);
		rootNode.setDrawingInterface(drawProjectile);
		rootNode.setBoundingBox(new AABB(.1f, .1f));
		rootNode.addAction(new ProjectileAction(this, universe.getHandle()));
	}
	
	public void setTimeToLive(long ttl)
	{
		TimeToLive timetolive = new TimeToLive(this, universe);
		timetolive.setTimeToLive(ttl);
		rootNode.addAction(timetolive);
		timetolive.start();
	}
	
	
	@Override
	public void destroy() 
	{
		HandGunProjectileExplosion explosion = new HandGunProjectileExplosion(universe);
		explosion.setStartingLoc(rootNode.getLocalX(), rootNode.getLocalY());
		universe.removeEntity(this);
		universe.addEntity(explosion);
	}
	
	
}
