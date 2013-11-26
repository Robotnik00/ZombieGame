package GameObjects;

import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class RoadSegment extends Entity
{

	public RoadSegment(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) 
	{
		ITexture roadtex = universe.getTextureEngine().LoadTexture("gfx/Environment/pavedroad.png", 1);
		SimpleDraw drawroad = new SimpleDraw(roadtex);
		rootNode.setDrawingInterface(drawroad);
		rootNode.setProxemityBounds(new AABB(3,3));
		drawroad.setScale(3, 3);
		universe.getBackgroundNode().addChild(rootNode);
	}

	@Override
	public void destroy() 
	{
		universe.getBackgroundNode().removeChild(rootNode);
	}

}
