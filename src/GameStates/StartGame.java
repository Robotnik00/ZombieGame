
package GameStates;



import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Actions.CollidablePhysics;
import Actions.PCControl;
import Actions.Physics;
import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import GameObjects.ExampleObject;
import GameObjects.GameObject;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;

/// example of making a scene. Creates ExampleObject and adds action to it


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
		universe.scale(.5f, .5f);
		
		ExampleObject obj = new ExampleObject(universe, game, gfx);
	}
	ITexture test;
	
	ITextureEngine	gfx;
	IAudioEngine	snd;
	IGameEngine		game;
	
	GameObject obj1, obj2, obj3;
	
	GameObject universe;
	
	
}
