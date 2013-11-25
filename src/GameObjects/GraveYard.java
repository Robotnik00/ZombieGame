package GameObjects;

import Drawing.DrawBoundingBox;
import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class GraveYard extends Entity
{

	public GraveYard(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		ITexture gravetex = universe.getTextureEngine().LoadTexture("gfx/Environment/grave.png", 0x00ffffff);
		SimpleDraw drawGrave = new SimpleDraw(gravetex);
		drawGrave.setOffset(0, -.2f);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				GameObject grave = new GameObject();
				grave.setDrawingInterface(drawGrave);
				grave.setLocalX(i);
				grave.setLocalY(j);
				grave.setBoundingBox(new AABB(.4f,.4f));
				grave.setProxemityBounds(new AABB(1,1));
				grave.setStatic(true);
				rootNode.addChild(grave);
			}
		}
		rootNode.setProxemityBounds(new AABB(5,3));
		rootNode.setCollidable(true);
	}

	@Override
	public void destroy() {
		
	}

}
