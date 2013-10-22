package GameObjects;

import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;
import Actions.Action;

// this is an example of how to build an object out of a single GameObject
public class ExampleObject 
{
	public ExampleObject(GameObject universe)
	{
		this.universe = universe;
		
		handle = new GameObject(); // create node
		handle.translate(-0.25f, 0); // object location
		universe.addChild(handle); // add to universe
	}
	public void loadTexture(ITextureEngine gfx) 
	{
		tex = gfx.LoadTexture("image.bmp", 0x000000); // load texture
		handle.setTexture(tex); // set texture to draw
	}
	public void addAction(Action action)
	{
		handle.addAction(action);
	}
	public GameObject getHandle()
	{
		return handle;
	}
	ITexture tex;
	GameObject handle;
	GameObject universe;
}
