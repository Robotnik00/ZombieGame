package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class TreeLeaves extends Entity
{

	public TreeLeaves(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) 
	{
		destroyable = false;
		
		String texname;
		if(Math.random() < .8f)
		{
			texname = "gfx/Environment/TreeLeaves1.png";
			
		}
		else 
		{
			texname = "gfx/Environment/TreeNoLeaves1.png";
		}
		
		
		ITexture treetex = universe.getTextureEngine().LoadTexture(texname, 0);
		SimpleDraw drawtree = new SimpleDraw(treetex);
		drawtree.setOrientation((float)(Math.random()*2*Math.PI));
		drawtree.setScale(3, 3);
		rootNode.setDrawingInterface(drawtree);
		universe.getForgroundNode().addChild(rootNode);
		rootNode.setStatic(true);
		rootNode.setCollidable(true);
		rootNode.setBoundingBox(new AABB(.65f,.65f));
		rootNode.setProxemityBounds(new AABB(3,3));
		universe.addEntity(this);
		drawtree.setBlend(.3f);
		drawtree.setColor(new Vector4f(.3f,.3f,.3f,1f));
	}

	@Override
	public void destroy() {
		universe.getHandle().removeChild(rootNode);
		universe.getForgroundNode().removeChild(rootNode);
	}

}
