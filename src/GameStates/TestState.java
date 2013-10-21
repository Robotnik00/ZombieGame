/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	TestState.java
 * 		Development gamestate.	
 */

// imports

package GameStates;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;
import TextureEngine.ITexture;



public class TestState implements IGameState
{
	public TestState()
	{
		//
	}
	
	
	//
	// IGameState
	//
	
	public void	Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game)
	{
		gfx_ = gfx;
		snd_ = snd;
		game_ = game;
		
		image_ = gfx_.LoadTexture("image.bmp", 0x000000);
	}
		
	public void	Quit()
	{
		//
	}
		
	public void	Update()
	{
		//game_.LogMessage("TestState::Update");
	}
	
	public void	Draw(float delta)
	{
		//game_.LogMessage("TestState::Draw");
		
		gfx_.ClearScreen();
		
		image_.SetPos(-0.5f, -0.5f);
		image_.SetScale(0.25f, 0.25f);
		image_.Draw();
	}
	
	
	
	//
	// protected members
	//
	
	ITextureEngine	gfx_;
	IAudioEngine	snd_;
	IGameEngine		game_;
	
	ITexture		image_;
}












