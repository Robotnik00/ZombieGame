package GameObjects;

import org.lwjgl.util.Rectangle;

import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;
import Actions.Action;
import Actions.MouseTracker;
import Actions.PCControl;
import Actions.Physics;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import Engine.IGameEngine;
import GameStates.EventListenerState;
import Geometry.AABB;

/**
 * Example of how to build models out of GameObjects
 * 
 * 
 *
 */
public class ExampleEntity extends Entity
{

	public ExampleEntity(Universe universe) 
	{
		super(universe);
	}

	@Override
	public void createObject(Universe level) 
	{
		// load the required textures
		ITexture triangle = universe.getTextureEngine().LoadTexture("image.bmp", 0);
		ITexture box = universe.getTextureEngine().LoadTexture("image2.png", 0);
	
		// add pc control to the root node of this object
		//PCControl pcc = new PCControl(this, universe.getGameEngine());
		//universe.getState().addKeyEventListener(pcc);
		//universe.getState().addMouseEventListener(pcc);
		// add action to the rootNode
		//rootNode.addAction(pcc);
		// set mass so the object accelerates fast
		rootNode.setMass(.1f);
		
		// create an axis to rotate the object about, and add a mouseTracker action to it.
		GameObject gimble = new GameObject();
		gimble.addAction(new MouseTracker(gimble, universe.getGameEngine()));
		// add it to rootNode so it translates with the rootNode
		rootNode.addChild(gimble);
		
		// create a node to represent a triangle, and set it to draw the triangle.
		GameObject triangleNode = new GameObject();
		triangleNode.setDrawingInterface(new SimpleDraw(triangle));
		// move the triangle forward by .6 units
		triangleNode.translate(.6f, 0);
		// add it to gimble so it rotates with gimble, which translates with rootNode
		gimble.addChild(triangleNode);
		
		// create a node to represent a box
		GameObject boxNode = new GameObject();
		// set it to draw a box.
		boxNode.setDrawingInterface(new SimpleDraw(box));
		// add it to gimble so it rotates with gimble, which translates with rootNode
		gimble.addChild(boxNode);
		
		
		// add the entity to the universe
		universe.addEntity(this);
		// set the camera to look at this
		universe.setFocus(this);
	}

	@Override
	public void destroy() 
	{
		universe.removeEntity(this);
	}

}