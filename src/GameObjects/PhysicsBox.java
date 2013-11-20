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
	
	
}