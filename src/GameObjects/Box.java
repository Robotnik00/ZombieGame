package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import TextureEngine.ITexture;
import Actions.CollidablePhysics;
import Drawing.DrawBoundingBox;
import Drawing.SimpleDraw;
import Geometry.AABB;

public class Box extends Entity
{

	public Box(Universe universe, Player player) {
		super(universe);
		hp = 3;
		maxHp = 3;
		
		float i = (float)Math.random();
		if(i > .8f)
		{
			entity = new MachineGunPowerup(universe, player);
		}
		else if(i > .6f && i < .8f)
		{
			entity = new ShotgunPowerup(universe, player);
		}
		else if(i > .4f && i < .6f)
		{
			entity = new RestoreHealth(universe, player);
		}
		else 
		{
			entity = null;
		}
		
	}

	@Override
	public void createObject(Universe universe)
	{
		destroyable = true;
		ITexture boxTexture = universe.getTextureEngine().LoadTexture("gfx/Environment/box_normal.png", 0);
		rootNode.setDrawingInterface(new SimpleDraw(boxTexture));
		rootNode.setBoundingBox(new AABB(1f,1f));
		rootNode.setProxemityBounds(new AABB(1,1));
		rootNode.setCollidable(true);
		universe.addEntity(this);
		rootNode.setStatic(true);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		universe.removeEntity(this);
		if(entity != null)
		{
			universe.addEntity(entity);
			entity.setStartingLoc(rootNode.getLocalX(), rootNode.getLocalY());
		}
	}
	
	@Override
	public void damage(float dp)
	{
		super.damage(dp);
		
		if(hp/maxHp < .5f)
		{
			ITexture damaged = universe.getTextureEngine().LoadTexture("gfx/Environment/box_damaged.png", 0);
			rootNode.setDrawingInterface(new SimpleDraw(damaged));
		}
		
	}
	Entity entity;
}