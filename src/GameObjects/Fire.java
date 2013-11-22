package GameObjects;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class Fire extends Entity
{

	public Fire(Universe universe, Entity e) {
		super(universe);
		entity = e;
		entity.getRootNode().addChild(rootNode);
	}

	@Override
	public void createObject(Universe universe) {
		ITexture fire = universe.getTextureEngine().LoadTexture("gfx/Projectiles/fire.png", 0);
		SimpleDraw drawfire = new SimpleDraw(fire);
		rootNode.setDrawingInterface(drawfire);
		drawfire.setScale(.35f, .35f);
		
	}

	@Override
	public void destroy() 
	{
		entity.getRootNode().removeChild(rootNode);
	}
	Entity entity;
}
