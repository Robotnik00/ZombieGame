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
import Geometry.AABB;
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
		
		// this is where the controls for the object 'grab hold'
		handle = new GameObject(); // create node
		universe.addChild(handle); // add to universe
		//handle.addAction(new MouseTracker(handle, game)); 
		PCControl pc = new PCControl(handle, universe, game);
		pc.setMass(.1f);    // set this so that the object accelerates quickly
		pc.setForceScale(3f); // max force applied by pc. thus max velocity = 3.0/1.0(with respect to universe.)
		handle.addAction(pc);
		handle.setBoundingBox(new AABB(1.5f,1.5f));
		handle.setCollidable(true);
		
		
		
		// create a rotating node to attach child1 to. 
		// The reason this is required is because child1 has a scale applied to it.
		// since the scale is already applied, we have to create a node in order to 
		// effectively change the order of the transforms from 'rotate then scale' to 'scale then rotate'
		// otherwise the the texture will be distorted.
		GameObject child1Node = new GameObject(); 
		Physics action = new Physics(child1Node, game);
		child1Node.addAction(action);
		action.applyTorque(2f);
		handle.addChild(child1Node);
		child1Node.translate(-1f, -.2f); // sets the location of the axis to rotate child1 about. 
		
		// add child1 to child1Node with a scale in the x direction
		// default location with respect to child1Node is 0.
		child1 = new GameObject(); // create node
		child1Node.addChild(child1); // add to node
		child1.scale(.5f, 1); // scale child
		
		
		// child 2 and 3 have no animation so they are pretty easy to add
		child2 = new GameObject(); // create node
		child2.translate(0, .3f); // object location
		handle.addChild(child2); // add to node
		
		child3 = new GameObject(); // create node
		child3.translate(-.4f, -.5f); // object location
		handle.addChild(child3); // add to node
		
		someText = new GameObject();
		someText.rotate((float)-Math.PI/2);
		someText.scale(.3f, .3f);
		someText.translate(-.4f, .7f);
		
		
		DrawText textrenderer = new DrawText(gfx, "font1.png");
		textrenderer.setText("helloworld");
		someText.setDrawingInterface(textrenderer);
		handle.addChild(someText);
		
		loadTexture(gfx);
	}
	public void loadTexture(ITextureEngine gfx) 
	{
		tex1 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex2 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex3 = gfx.LoadTexture("image.bmp", 0x000000); // load texture

		SimpleDraw drawing1 = new SimpleDraw(tex1);
		SimpleDraw drawing2 = new SimpleDraw(tex2);
		SimpleDraw drawing3 = new SimpleDraw(tex3);

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
	GameObject someText;
	GameObject universe;
}
