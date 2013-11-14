package GameObjects;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class Player extends Entity
{

	public Player(Universe universe)
	{
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) 
	{
		ITexture playerTexture = universe.getTextureEngine().LoadTexture("gfx/Characters/player1.png", 0);
		rootNode.setDrawingInterface(new SimpleDraw(playerTexture));
		universe.addEntity(this);
	}

	@Override
	public void destroy() 
	{
		// TODO Auto-generated method stub
		
	}

}
