
package GameStates;


import Actions.ObjectFollower;
import AudioEngine.IAudioEngine;
import Drawing.TileDraw;
import Engine.IGameEngine;
import GameObjects.ExampleObject;
import GameObjects.GameObject;
import InputCallbacks.MouseEvent;
import InputCallbacks.MouseEventListener;
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
		universe.scale(.1f, .1f);
		
		ExampleObject obj = new ExampleObject(universe, game, gfx);
		
		ObjectFollower objFollower = new ObjectFollower(universe, obj.getHandle(), game);
		objFollower.setMass(0.5f);
		objFollower.setFrictionConstant(10f);
		
		universe.addAction(objFollower);
	
	}
	ITexture test;
	
	ITextureEngine	gfx;
	IAudioEngine	snd;
	IGameEngine		game;
	
	GameObject obj1, obj2, obj3;
	
	GameObject universe;

	
	
}
