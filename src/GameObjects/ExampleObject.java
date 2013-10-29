package GameObjects;

import org.lwjgl.util.Rectangle;

import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;
import Actions.Action;
import Actions.MouseTracker;
import Actions.PCControl;
import Actions.Physics;
import Drawing.SimpleDraw;
import Engine.IGameEngine;
/**
 * Example of how to build models out of GameObjects
 * 
 * 
 *
 */
public class ExampleObject 
{
	/**
	 *  
	 * @param  universe (the root of the scene graph)
	 * @param  game
	 * @param  gfx for loading textures
	 */
	public ExampleObject(GameObject universe, IGameEngine game, ITextureEngine gfx)
	{
		this.universe = universe;
		
		handle = new GameObject(); // create node
		handle.rotate((float)Math.PI/2);
		universe.addChild(handle); // add to universe
		handle.addAction(new MouseTracker(handle, game)); 
		PCControl pc = new PCControl(handle, universe, game);
		pc.setMass(.1f);
		pc.setForceScale(3f);
		handle.addAction(pc);
		
		GameObject node = new GameObject(); // create node for rotating
		handle.addChild(node); // add it to handle
		
		
		
		GameObject child1Node = new GameObject();
		Physics action = new Physics(child1Node, game);
		child1Node.addAction(action);
		action.applyTorque(2f);
		node.addChild(child1Node);
		child1Node.translate(-.7f, -.3f);
		
		child1 = new GameObject(); // create node
		child1.translate(-.5f, -.5f); // center on rotating node
		child1Node.addChild(child1); // add to node
		child1.scale(.5f, 1); // scale child
		
		
		
		child2 = new GameObject(); // create node
		child2.translate(0, .3f); // object location
		node.addChild(child2); // add to node
		
		child3 = new GameObject(); // create node
		child3.translate(-.4f, -.5f); // object location
		node.addChild(child3); // add to node
		
		loadTexture(gfx);
	}
	public void loadTexture(ITextureEngine gfx) 
	{
		tex1 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex2 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex3 = gfx.LoadTexture("image.bmp", 0x000000); // load texture

		SimpleDraw drawing1 = new SimpleDraw(child1, tex1);
		SimpleDraw drawing2 = new SimpleDraw(child2, tex2);
		SimpleDraw drawing3 = new SimpleDraw(child3, tex3);

		child1.setDrawingInterface(drawing1);
		child2.setDrawingInterface(drawing2);
		child3.setDrawingInterface(drawing3);
		
		//child1.setTexture(tex1); // set texture to draw
		//child2.setTexture(tex2); // set texture to draw
		//child3.setTexture(tex3); // set texture to draw
		
	}
	public GameObject getHandle()
	{
		return handle;
	}
	ITexture tex1, tex2, tex3;
	GameObject handle;
	GameObject child1;
	GameObject child2;
	GameObject child3;
	GameObject universe;
}
