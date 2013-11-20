package GameObjects;

import org.lwjgl.util.vector.Vector2f;

import TextureEngine.ITexture;
import Actions.CollidablePhysics;
import Drawing.DrawBoundingBox;
import Drawing.SimpleDraw;
import Geometry.AABB;

public class PhysicsBox extends Box
{

	public PhysicsBox(Universe universe, Player player) {
		super(universe, player);

	}

	@Override
	public void createObject(Universe universe)
	{
		super.createObject(universe);
		CollidablePhysics col = new CollidablePhysics(rootNode, universe.getHandle(), universe.getGameEngine());
		rootNode.addAction(col);
		rootNode.setMass(.1f);

		
		
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