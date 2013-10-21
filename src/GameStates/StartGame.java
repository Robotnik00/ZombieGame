
package GameStates;



import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Actions.CollidablePhysics;
import Actions.PCControl;
import Actions.Physics;
import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import GameObjects.GameObject;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;

/// example of making a scene. Contains a single object moving to the right


// sets up a test for collision detection. 
public class StartGame implements IGameState
{
	public StartGame()
	{
		
	}
	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) {
		// TODO Auto-generated method stub
		this.gfx  = gfx;
		this.snd  = snd;
		this.game = game;
		
		buildUniverse();
		
		//game.EndGameLoop(); // quits immediately.
	}
	
	@Override
	public void Quit() {
		// TODO Auto-generated method stub
		
	}

	// maybe this need to have delta for adjustable update frequency?
	@Override
	public void Update() {
		// TODO Auto-generated method stub]
		universe.update();
	}

	@Override
	public void Draw(float delta) 
	{
		gfx.ClearScreen();
		universe.draw();
	}



	// sets up an enviroment with to collidable objects
	public void buildUniverse()
	{
		universe = new GameObject();
		
		obj1 = new GameObject();
		obj1.setCollidable(true); // only objects at the root of a subtree of collidable things are actually set collidable
		obj1.translate(-0.25f, 0); // object location
		obj1.setBoundingBox(new Rectangle(0,0,5,5)); // bounding box
		obj1.setProxemityBounds(new Rectangle(0,0,20,10)); // "bigger" bounding box
		universe.addChild(obj1);
		
		
		obj2 = new GameObject();
		obj2.setCollidable(true);
		obj2.setBoundingBox(new Rectangle(0,0,5,5));
		obj2.setProxemityBounds(new Rectangle(0,0,5,5));
		universe.addChild(obj2);
		
		
		obj3 = new GameObject();
		obj3.setBoundingBox(new Rectangle(0,0,5,5));
		obj3.setProxemityBounds(new Rectangle(0,0,5,5));
		obj1.addChild(obj3);
		obj3.translate(0, 0);
		
		
		// hmmm game.GetFrameRate returns draw update freq
		// needs update frequency so when update freq changes
		// objects still move at same speed
		PCControl control = new PCControl(obj1, universe, game);
		
		obj1.addAction(control);
		
		test = gfx.LoadTexture("image.bmp", 0x000000);
		
		obj1.setTexture(test);
	}
	
	ITextureEngine	gfx;
	IAudioEngine	snd;
	IGameEngine		game;
	
	ITexture test;
	GameObject obj1, obj2, obj3;
	
	GameObject universe;
	
	
}
