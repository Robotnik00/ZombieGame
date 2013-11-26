package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Drawing.DrawBoundingBox;
import Drawing.SimpleDraw;
import Drawing.TileDraw;
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
		ITexture dirttex = universe.getTextureEngine().LoadTexture("gfx/Environment/dirt.png", 0x00ffffff);
		
		
		SimpleDraw drawtiles = new SimpleDraw(dirttex);
		drawtiles.setScale(2, 2f);
		dirtnode = new GameObject();
		
		
		SimpleDraw drawGrave = new SimpleDraw(gravetex);
		drawGrave.setOffset(0, -.3f);
		drawGrave.setScale(1, 1.5f);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				GameObject grave = new GameObject();
				grave.setDrawingInterface(drawGrave);
				grave.setLocalX(i*2-4);
				grave.setLocalY(j*2-2);
				grave.setBoundingBox(new AABB(.3f,.3f));
				grave.setProxemityBounds(new AABB(1,1));
				//grave.setStatic(true);
				rootNode.addChild(grave);
				
				GameObject dirt = new GameObject();
				dirt.setDrawingInterface(drawtiles);
				dirt.setLocalX(i*2-4);
				dirt.setLocalY(j*2-2.5f);
				dirtnode.addChild(dirt);
				
			}
		}
		
		
		rootNode.setProxemityBounds(new AABB(10,6));
		rootNode.setCollidable(true);
		//universe.getBackgroundNode().addChild(dirtnode);
		universe.getHandle().addChild(rootNode);
	}
	@Override
	public void setStartingLoc(float x, float y)
	{
		startingX = x;
		startingY = y;
		rootNode.setLocalX(x);
		rootNode.setLocalY(y);
		
		dirtnode.setLocalX(x);
		dirtnode.setLocalY(y);
		
	}
	

	@Override
	public void destroy() {
		
	}
	GameObject dirtnode;
}
