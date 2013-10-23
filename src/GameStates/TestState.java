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

import org.lwjgl.input.*;



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
		
		image1_ = gfx_.LoadTexture("image.bmp", 0x00000000);
		image2_ = gfx_.LoadTexture("image2.png", 0x0000FFFF);
		
		// rescale the drawing perspective
		//gfx_.SetOrthoPerspective(0, 640, 0, 480);
	}
		
	public void	Quit()
	{
		//
	}
		
	public void	Update()
	{
		// move
		x_ += vx_; vx_ = 0;
		y_ += vy_; vy_ = 0;
		
		// read input, determine movement vector for this frame
		int[] keys = game_.GetKeyEvents();
		
		for (int i=0; i < keys.length; i++)
		{
			switch (keys[i])
			{
			case Keyboard.KEY_W:	moveUp_ = true;			break;
			case -Keyboard.KEY_W:	moveUp_ = false;		break;
			case Keyboard.KEY_S:	moveDown_ = true;		break;
			case -Keyboard.KEY_S:	moveDown_ = false;		break;
			case Keyboard.KEY_A:	moveLeft_ = true;		break;
			case -Keyboard.KEY_A:	moveLeft_ = false;		break;
			case Keyboard.KEY_D:	moveRight_ = true;		break;
			case -Keyboard.KEY_D:	moveRight_ = false;		break;
			
			case Keyboard.KEY_ESCAPE:	
				game_.EndGameLoop();
				break;
			}
		}
		
		// move
		float moveSpeed = 0.05f;
		if (moveUp_)	vy_ += moveSpeed;
		if (moveDown_)	vy_ += -moveSpeed;
		if (moveLeft_)	vx_ += -moveSpeed;
		if (moveRight_)	vx_ += moveSpeed;
		
		// display the frame rate
		game_.SetWindowTitle("FPS: "+game_.GetFrameRate());
		
	}
	
	public void	Draw(float delta)
	{
		gfx_.ClearScreen();
		
		// draw at the origin, and rotate toward the cursor
		image2_.SetSrcRect(0.0f, 0.0f, 2.0f, 2.0f);
		image2_.SetScale(0.4f, 0.4f);
		image2_.SetOrigin(-0.5f, -0.5f);
		image2_.SetRotation((float)Math.atan2(y_ + vy_*delta, x_ + vx_*delta));
		image2_.SetPos(0.0f, 0.0f);
		image2_.Draw();
		
		// controlled by the keyboard
		image1_.SetScale(0.25f, 0.25f);
		image1_.SetOrigin(-0.5f, -0.5f);
		image1_.SetBlendColor(0.0f, 0.0f, 1.0f, 0.5f);
		image1_.SetAlpha(0.5f);
		image1_.SetPos(x_ + vx_*delta, y_ + vy_*delta);
		image1_.Draw();
	}
	
	
	
	//
	// protected members
	//
	
	protected ITextureEngine	gfx_;
	protected IAudioEngine		snd_;
	protected IGameEngine		game_;
	
	protected ITexture			image1_;
	protected ITexture			image2_;
	
	protected float				x_,y_;
	protected float				vx_,vy_;
	
	protected boolean			moveUp_;
	protected boolean			moveDown_;
	protected boolean			moveLeft_;
	protected boolean			moveRight_;
}












