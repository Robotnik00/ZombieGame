package GameObjects;

import Actions.MouseTracker;
import Actions.PCControl;
import Drawing.DrawBoundingBox;
import Drawing.SimpleDraw;
import Geometry.AABB;
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
		// add pc control to the root node of this object
		PCControl pcc = new PCControl(rootNode, universe.getHandle(), universe.getGameEngine());
		universe.getState().addKeyEventListener(pcc);
		universe.getState().addMouseEventListener(pcc);
		// add action to the rootNode
		rootNode.addAction(pcc);
		// set mass so the object accelerates fast
		rootNode.setMass(.1f);
		rootNode.setBoundingBox(new AABB(.5f,.5f));
		rootNode.translate(startingPosX, startingPosY);
		rootNode.setCollidable(true);
		
		
		
		// create an axis to rotate the object about, and add a mouseTracker action to it.
		GameObject gimble = new GameObject();
		gimble.addAction(new MouseTracker(gimble, universe.getGameEngine()));
		SimpleDraw drawChar = new SimpleDraw(playerTexture);
		drawChar.setOrientation((float)-Math.PI/2);
		gimble.setDrawingInterface(drawChar);
		// add it to rootNode so it translates with the rootNode
		rootNode.addChild(gimble);
		
		universe.addEntity(this);
		universe.setFocus(this);
	}

	@Override
	public void destroy() 
	{
		// TODO Auto-generated method stub
		
	}
	float startingPosX;
	float startingPosY;
	float health = 1.0f;
	/**
	 * ect... 
	 */
}
