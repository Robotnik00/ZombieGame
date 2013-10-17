
package GameStates;



import org.lwjgl.util.vector.Vector2f;

import Actions.PCControl;
import Actions.Physics;
import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import GameObjects.GameObject;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;

/// example of making a scene. Contains a single object moving to the right

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
	public void Draw(float delta) {
	}

	// will notify all objects that are colliding with each other
	public void notifyCollisions()
	{
	}
	
	public void buildUniverse()
	{
		universe = new GameObject();
		
		GameObject obj1 = new GameObject();
		universe.addChild(obj1);
		
		// hmmm game.GetFrameRate returns draw update freq
		// needs update frequency so when update freq changes
		// objects still move at same speed
		PCControl control = new PCControl(obj1, game);
		
		obj1.addAction(control);
	}
	
	ITextureEngine	gfx;
	IAudioEngine	snd;
	IGameEngine		game;
	
	ITexture test;
	
	GameObject universe;
	
	
}
