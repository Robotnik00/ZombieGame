package GameObjects;

import Actions.TimeToLive;
import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class HandGunProjectileExplosion extends ProjectileExplosion
{

	public HandGunProjectileExplosion(Universe universe)
	{
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe)
	{
		timeToLive = new TimeToLive(this, universe);
		rootNode.addAction(timeToLive);
		ITexture explosion = universe.getTextureEngine().LoadTexture("gfx/Projectiles/HandGunProjectileExplosion.png", 0);
		SimpleDraw drawExplosion = new SimpleDraw(explosion);
		drawExplosion.setScale(.3f, .3f);
		rootNode.setDrawingInterface(drawExplosion);
		timeToLive.setTimeToLive(100);
		timeToLive.start();

		
	}
	
	TimeToLive timeToLive;

}
