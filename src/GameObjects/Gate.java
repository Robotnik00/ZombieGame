package GameObjects;

import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class Gate extends Entity
{

	public Gate(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) {
		
		ITexture wood = universe.getTextureEngine().LoadTexture("gfx/Environment/wood.png", 0x00ffffff);
		SimpleDraw drawwood = new SimpleDraw(wood);
		drawwood.setScale(.31f, .31f);
		rootNode.setDrawingInterface(drawwood);
		rootNode.setBoundingBox(new AABB(.3f,.3f));
		rootNode.setCollidable(true);

	}

	@Override
	public void destroy() {
		universe.removeEntity(this);
		
	}

}
