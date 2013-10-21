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
		
		x_ = 0;
		y_ = 0;
		vx_ = 0;
		vy_ = 0;
		
		image_ = gfx_.LoadTexture("image.bmp", 0x00000000);
		
		// rescale the drawing perspective
		//gfx_.SetOrthoPerspective(0, 640, 0, 480);
	}
		
	public void	Quit()
	{
		//
	}
		
	public void	Update()
	{
		// move toward the mouse
		x_ = game_.GetMouseX();
		y_ = game_.GetMouseY();
	}
	
	public void	Draw(float delta)
	{
		gfx_.ClearScreen();
		
		// have one move with the mouse
		image_.SetScale(0.25f, 0.25f);
		image_.SetOrigin(-0.5f, -0.5f);
		image_.SetPos(x_, y_);
		image_.Draw();
		
		// another is drawn at the origin
		image_.SetScale(0.25f, 0.25f);
		image_.SetOrigin(-0.5f, -0.5f);
		image_.SetPos(0.0f, 0.0f);
		image_.Draw();
	}
	
	
	
	//
	// protected members
	//
	
	protected ITextureEngine	gfx_;
	protected IAudioEngine		snd_;
	protected IGameEngine		game_;
	
	protected ITexture			image_;
	
	protected float				x_,y_;
	protected float				vx_,vy_;
}












