package GameObjects;

import Actions.ProjectileAction;
import Actions.TimeToLive;
import Drawing.SimpleDraw;
import Geometry.AABB;

public class HandGunProjectile extends Projectile
{

	public HandGunProjectile(Universe universe, Player player) {
		super(universe, player);
		// TODO Auto-generated constructor stub
		randomizeDamage = .1f;
		hp = .1f;
		maxHp = .1f;
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
	@Override 
	public void inflictDamage(Entity entity)
	{
		super.inflictDamage(entity);

		destroy();
	}
	
	@Override
	public void destroy() 
	{
		HandGunProjectileExplosion explosion = new HandGunProjectileExplosion(universe);
		explosion.setStartingLoc(rootNode.getLocalX(), rootNode.getLocalY());
		universe.removeEntity(this);
		universe.addEntity(explosion);
		rootNode.removeAction(timetolive);
	}
	
}
