package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Actions.TimeToLive;
import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class BloodSplatter extends Entity
{

	public BloodSplatter(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		String texName = "gfx/Environment/BloodSplatters/BloodSplatter" + (int)(Math.random()*5 + 1) + ".png";
		ITexture bloodSplatter = universe.getTextureEngine().LoadTexture(texName, 0);
		SimpleDraw drawSplatter = new SimpleDraw(bloodSplatter);
		drawSplatter.setColor(new Vector4f(.5f,.05f,0.05f,.8f));
		rootNode.setDrawingInterface(drawSplatter);
		drawSplatter.setScale(.3f, .3f);
		
		universe.getBackgroundNode().addChild(rootNode);
		
		ttl = new TimeToLive(this, universe);
		ttl.setTimeToLive(5000); // default 20 secs
		rootNode.addAction(ttl);

		ttl.start();
		
		
	}
	
	public void setTimeToLive(long ttl)
	{
		this.ttl.setTimeToLive(ttl);
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
		universe.getBackgroundNode().removeChild(rootNode);
	}
	
	TimeToLive ttl;
}
