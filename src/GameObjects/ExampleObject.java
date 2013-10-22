package GameObjects;

import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;
import Actions.Action;

// this is an example of how to build an object out nodes
public class ExampleObject 
{
	public ExampleObject(GameObject universe)
	{
		this.universe = universe;
		
		handle = new GameObject(); // create node
		handle.translate(-0.4f, 0); // object location
		universe.addChild(handle); // add to universe
		

		child1 = new GameObject(); // create node
		child1.translate(.4f, 0); // object location
		handle.addChild(child1); // add to handle
		
		child2 = new GameObject(); // create node
		child2.translate(0, .4f); // object location
		handle.addChild(child2); // add to handle
	}
	public void loadTexture(ITextureEngine gfx) 
	{
		tex1 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex2 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		tex3 = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		
		
		handle.setTexture(tex1); // set texture to draw
		child1.setTexture(tex2); // set texture to draw
		child2.setTexture(tex3); // set texture to draw
		
	}
	public void addAction(Action action)
	{
		handle.addAction(action);
	}
	public GameObject getHandle()
	{
		return handle;
	}
	ITexture tex1, tex2, tex3;
	GameObject handle;
	GameObject child1;
	GameObject child2;
	GameObject universe;
}
