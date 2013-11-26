package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class Wall extends Entity
{

	public Wall(Universe universe)
	{
		super(universe);
	}

	@Override
	public void createObject(Universe universe) 
	{
		ITexture wall = universe.getTextureEngine().LoadTexture("gfx/Environment/blockwall_cracked.png", 1);
		SimpleDraw drawwall = new SimpleDraw(wall);
		drawwall.setColor(new Vector4f(.2f,.2f,.2f,1));
		drawwall.setBlend(.5f);
		rootNode.setDrawingInterface(drawwall);
		
		rootNode.setCollidable(true);
		rootNode.setBoundingBox(new AABB(1.2f,1.2f));
		rootNode.setProxemityBounds(new AABB(1.3f,1.3f));
		
	}

	@Override
	public void destroy() 
	{
		universe.removeEntity(this);
	}

}
