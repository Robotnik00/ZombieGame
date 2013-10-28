package GameObjects;

import org.lwjgl.util.Rectangle;

import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;
import Actions.Action;
import Actions.PCControl;
import Actions.Physics;
import Engine.IGameEngine;

// this is an example of how to build an object out nodes
public class ExampleObject 
{
	public ExampleObject(GameObject universe, IGameEngine game, ITextureEngine gfx)
	{
		this.universe = universe;
		
		handle = new GameObject(); // create node
		universe.addChild(handle); // add to universe
		PCControl pc = new PCControl(handle, universe, game);
		handle.addAction(pc);
		
		GameObject node = new GameObject(); // create node for rotating
		handle.addChild(node); // add it to handle
		node.rotate((float)Math.PI/4); // rotate it and all of its children
		node.scale(-1, 1);
		
		
		GameObject child1Node = new GameObject();
		Physics action = new Physics(child1Node, game);
		child1Node.addAction(action);
		action.applyTorque(2f);
		node.addChild(child1Node);
		child1Node.translate(1f, 0);
		
		child1 = new GameObject(); // create node
		child1.translate(-.5f, -.5f); // center on rotating node
		child1Node.addChild(child1); // add to node
		child1.scale(.5f, 1); // scale child
		
		child2 = new GameObject(); // create node
		child2.translate(0, .7f); // object location
		node.addChild(child2); // add to node
		
		child3 = new GameObject(); // create node
		child3.translate(-.4f, 0); // object location
		node.addChild(child3); // add to node
		
		loadTexture(gfx);
	}
	public void loadTexture(ITextureEngine gfx) 
	{
		tex1 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex2 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex3 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		
		
		child1.setTexture(tex1); // set texture to draw
		child2.setTexture(tex2); // set texture to draw
		child3.setTexture(tex3); // set texture to draw
		
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
