package GameObjects;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

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
		else if(i > .2f && i < .4f)
		{

			entity = new FlameThrowerPowerup(universe, player);
		}
		else if(i > .15f && i < .2f)
		{
			entity = new Invulnerable(universe, player);
		}
		else if(i > .1 && i < .15f)
		{
			entity = new DamageMultiplier(universe, player);
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
		drawbox = new SimpleDraw(boxTexture);
		rootNode.setDrawingInterface(drawbox);
		rootNode.setBoundingBox(new AABB(1f,1f));
		rootNode.setProxemityBounds(new AABB(1,1));
		rootNode.setCollidable(true);
		universe.addEntity(this);
		rootNode.setStatic(true);
		rootNode.scale(.5f, .5f);
		drawbox.setColor(new Vector4f(.2f,.2f,.2f, 1));
		drawbox.setBlend(.5f);
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
			drawbox.setTexture(damaged);
		}
		
	}
	Entity entity;
	SimpleDraw drawbox;
}