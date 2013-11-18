package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import TextureEngine.ITexture;
import Actions.CollidablePhysics;
import Drawing.DrawBoundingBox;
import Drawing.SimpleDraw;
import Geometry.AABB;

public class StaticBox extends Entity
{

	public StaticBox(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
		hp = 10;
		maxHp = 10;
	}

	@Override
	public void createObject(Universe universe)
	{
		ITexture boxTexture = universe.getTextureEngine().LoadTexture("gfx/Environment/box_normal.png", 0);
		rootNode.setDrawingInterface(new SimpleDraw(boxTexture));
		rootNode.setBoundingBox(new AABB(1f,1f));
		rootNode.setProxemityBounds(new AABB(1,1));
		rootNode.setCollidable(true);
		universe.addEntity(this);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		universe.removeEntity(this);
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
}