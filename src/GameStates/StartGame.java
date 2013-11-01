
package GameStates;


import org.lwjgl.util.vector.Vector2f;

import Actions.ObjectFollower;
import Actions.Physics;
import AudioEngine.IAudioEngine;
import Drawing.SimpleDraw;
import Drawing.TileDraw;
import Engine.IGameEngine;
import GameObjects.ExampleObject;
import GameObjects.GameObject;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;

/// example of making a scene. Creates ExampleObject and adds action to it

/**
 * StartGame a sandbox state showing how to create objects using nodes
 * 
 * 
 */
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
		universe.update((float)1/game.GetTickFrequency());
		game.SetWindowTitle(""+game.GetFrameRate());
	}

	@Override
	public void Draw(float delta) 
	{
		gfx.ClearScreen();
		universe.draw(delta);
	}



	// sets up an enviroment with to collidable objects
	public void buildUniverse()
	{
		universe = new GameObject(); // create universe
		test = gfx.LoadTexture("image3.bmp", 1);
		universe.setDrawingInterface(new TileDraw(universe, test));
		universe.translate(0, -.5f); 
		universe.scale(.2f, .2f);
		
		ExampleObject obj = new ExampleObject(universe, game, gfx);
		
		ObjectFollower objFollower = new ObjectFollower(universe, obj.getHandle(), game);
		objFollower.setMass(.1f);
		objFollower.setFrictionConstant(1f);
		
		universe.addAction(objFollower);
		
		
		for(int i = 0; i < 100; i++)
		{
			GameObject obj1 = new GameObject();
			obj1.translate((float)(Math.random()-.5)* 10, (float)(Math.random()-.5) * 10);
			obj1.rotate((float)(Math.random()*2*Math.PI));
			obj1.setDrawingInterface(new SimpleDraw(obj1, gfx.LoadTexture("image.bmp", 0)));
			obj1.rotate((float)Math.PI/2);
			Physics p = new Physics(obj1, game);
			p.applyForce(new Vector2f((float)(Math.random()-.5)*5, (float)(Math.random()-.5)*5));
			p.applyTorque((float)(Math.random()));
			obj1.addAction(p);
			
			universe.addChild(obj1);
		}
		
		for(int i = 0; i < 10; i++)
		{
			GameObject obj2 = new GameObject();
			obj2.translate((float)(Math.random()-.5)* 10, (float)(Math.random()-.5) * 10);
			obj2.rotate((float)(Math.random()*2*Math.PI));
			obj2.setDrawingInterface(new SimpleDraw(obj2, gfx.LoadTexture("image2.png", 0)));
			universe.addChild(obj2);
		}
		
		
	}
	ITexture test;
	
	ITextureEngine	gfx;
	IAudioEngine	snd;
	IGameEngine		game;
	
	GameObject obj1, obj2, obj3;
	
	GameObject universe;

	
	
}
