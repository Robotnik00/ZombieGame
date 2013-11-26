package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class Building extends Entity
{

	public Building(Universe universe) 
	{
		super(universe);
	}

	@Override
	public void createObject(Universe universe) 
	{
		ITexture buildingtex = universe.getTextureEngine().LoadTexture("gfx/Environment/Building.png", 0);
		drawbuilding = new SimpleDraw(buildingtex);
		drawbuilding.setOffset(0, .22f);
		rootNode.setDrawingInterface(drawbuilding);
		rootNode.setBoundingBox(new AABB(3.1f,3.1f));
		rootNode.setProxemityBounds(new AABB(7,7));
		rootNode.setCollidable(true);
		drawbuilding.setScale(7, 7);
		universe.getHandle().addChild(rootNode);
		universe.getForgroundNode().addChild(rootNode);
	}
	public void setColor(Vector4f color)
	{
		drawbuilding.setColor(color);
	}
	public void setBlend(float blend)
	{
		drawbuilding.setBlend(blend);
	}
	
	@Override
	public void destroy() 
	{
		universe.getHandle().removeChild(rootNode);
		universe.getForgroundNode().removeChild(rootNode);
	}
	
	SimpleDraw drawbuilding;

}
