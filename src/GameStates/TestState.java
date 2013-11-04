/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	TestState.java
 * 		Development gamestate.	
 */

package GameStates;

// imports
import AudioEngine.IAudioEngine;
import AudioEngine.ISound;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;
import TextureEngine.ITexture;
import Utility.BitmapFont;

import org.lwjgl.input.*;



/**
 * Gamestate used to test features during development.
 * 
 * @author Jacob
 */
public class TestState implements IGameState
{
	public TestState()
	{
		//
	}
	
	
	
	//
	// IGameState
	//
	
	public void	Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) throws Exception
	{
		gfx_ = gfx;
		snd_ = snd;
		game_ = game;
		
		x_ = 10.0f;
		y_ = 10.0f;
		vx_ = 0;
		vy_ = 0;
		
		image1_ = gfx_.LoadTexture("image.bmp", 0x00000000);
		image2_ = gfx_.LoadTexture("image2.png", 0x0000FFFF);
		image3_ = gfx_.LoadTexture("image3.bmp", 0x00000001);
		font_ = gfx_.LoadTexture("font1.png", 0);
		
		music_ = snd_.LoadSound("music.wav");
		sfx1_ = snd_.LoadSound("sfx1.wav");
		sfx2_ = snd_.LoadSound("sfx2.wav");
		sfx3_ = snd_.LoadSound("sfx3.wav");
		
		bitmapFont_ = new BitmapFont();
		bitmapFont_.SetFont(font_);
		
		// rescale the drawing perspective
		gfx_.SetOrthoPerspective(0, 20, 0, 15);
	}
		
	public void	Quit()
	{
		//
	}
		
	public void	Update()
	{
		// move
		x_ += vx_; 
		y_ += vy_;
		
		snd_.SetListenerPosition(x_, y_);
		
		vx_ = 0;
		vy_ = 0;
		
		// read input, determine movement vector for this frame
        int[] keys = game_.GetKeyEvents();
        
        for (int i=0; i < keys.length; i++)
        {
	        switch (keys[i])
	        {
	        case Keyboard.KEY_W:	moveUp_ = true;		break;
	        case -Keyboard.KEY_W:	moveUp_ = false;	break;
	        case Keyboard.KEY_S:	moveDown_ = true;	break;
	        case -Keyboard.KEY_S:	moveDown_ = false;	break;
	        case Keyboard.KEY_A:	moveLeft_ = true;	break;
	        case -Keyboard.KEY_A:	moveLeft_ = false;	break;
	        case Keyboard.KEY_D:	moveRight_ = true;	break;
	        case -Keyboard.KEY_D:	moveRight_ = false;	break;
	        
	        case Keyboard.KEY_1:	music_.Loop();	game_.LogMessage("KEY_1"); 	break;
	        case Keyboard.KEY_2:	music_.Pause();	game_.LogMessage("KEY_2");	break;
	        case Keyboard.KEY_3:	music_.Stop();	game_.LogMessage("KEY_3");	break;
	        case Keyboard.KEY_4:	sfx1_.Play();	game_.LogMessage("KEY_4");	break;
	        case Keyboard.KEY_5:	sfx2_.Play();	game_.LogMessage("KEY_5");	break;
	        case Keyboard.KEY_6:	sfx3_.Play();	game_.LogMessage("KEY_6");	break;
	        
	        case Keyboard.KEY_ESCAPE:        
	                game_.EndGameLoop();
	                break;
	        }
        }
        
        // move
        float moveSpeed = 0.1f;
        if (moveUp_)	vy_ += moveSpeed;
        if (moveDown_)	vy_ += -moveSpeed;
        if (moveLeft_)	vx_ += -moveSpeed;
        if (moveRight_)	vx_ += moveSpeed;
		
		// display the frame rate
		game_.SetWindowTitle("FPS: "+game_.GetFrameRate());
	}
	
	public void	Draw(float delta)
	{
		//gfx_.ClearScreen();
		
		float dx = x_ + vx_ * delta;
		float dy = y_ + vy_ * delta;
		
		// draw a bunch of tiles
		for (int x=0; x < 20; x++)
		{
			for (int y=0; y < 15; y++)
			{
				image3_.SetPos((float)x, (float)y);
				image3_.Draw();
			}
		}
		//image3_.SetPos(0.0f,0.0f);
		//image3_.SetScale(20.0f, 15.0f);
		//image3_.SetSrcRect(0.0f, 0.0f, 20.0f, 15.0f);
		//image3_.Draw();
		
		
		// draw at the origin, and rotate toward the cursor
		
		//image2_.SetSrcRect(0.25f, 0.25f, 0.75f, 0.75f);
		//image2_.SetScale(0.4f, 0.4f);
		image2_.SetOrigin(-0.5f, -0.5f);
		image2_.SetRotation((float)Math.atan2(dy-10.0f, dx-10.0f));
		image2_.SetPos(10.0f, 10.0f);
		image2_.Draw();
		
		
		// controlled by the keyboard
		
		//image1_.SetScale(1.0f, 1.0f);
		image1_.SetOrigin(-0.5f, -0.5f);
		image1_.SetRotation(
			(float)Math.atan2(
				game_.GetMouseY()-dy,
				game_.GetMouseX()-dx)
				);
		//image1_.SetBlendColor(0.0f, 0.0f, 1.0f, 0.5f);
		//image1_.SetAlpha(0.5f);
		image1_.SetPos(dx, dy);
		image1_.Draw();
		
		// draw font
		bitmapFont_.SetPosition(0.0f, 14.0f);
		bitmapFont_.SetScale(0.5f,0.5f);
		bitmapFont_.SetKerning(0.4f);
		bitmapFont_.DrawString(
			"1) Play music (loop).		\n" +
			"2) Pause music.			\n" +
			"3) Stop music.				\n" +
			"4) Play sound 1 (left).	\n" +
			"5) Play sound 2 (center).	\n" +
			"6) Play sound 3 (right).	\n"
		);
		
		// draw primitive shapes
		
		// sound 1 marker
		gfx_.SetDrawColor(1.0f, 0.0f, 0.0f, 1.0f);
		gfx_.DrawRectangle(1.0f, 8.0f, 2.0f, 9.0f);
		
		// sound 2
		gfx_.SetDrawColor(0.0f, 1.0f, 0.0f, 1.0f);
		gfx_.DrawRectangle(10.0f, 8.0f, 11.0f, 9.0f);
		
		// sound 3
		gfx_.SetDrawColor(0.0f, 0.0f, 1.0f, 1.0f);
		gfx_.DrawRectangle(18.0f, 8.0f, 19.0f, 9.0f);
		
		// music
		gfx_.SetDrawColor(1.0f, 1.0f, 1.0f, 1.0f);
		gfx_.DrawRectangle(10.0f, 2.0f, 11.0f, 3.0f);
		
		//gfx_.SetDrawColor(0.0f, 1.0f, 0.0f, 1.0f);
		//gfx_.DrawLine(0, 0, 2.0f, 2.0f);
		//gfx_.DrawLine(0, 2.0f, 2.0f, 0);
	}
	
	
	
	//
	// protected members
	//
	
	protected ITextureEngine	gfx_;
	protected IAudioEngine		snd_;
	protected IGameEngine		game_;
	
	protected ITexture			image1_;
	protected ITexture			image2_;
	protected ITexture			image3_;
	protected ITexture			font_;
	protected BitmapFont		bitmapFont_;
	
	protected ISound			music_;
	protected ISound			sfx1_;
	protected ISound			sfx2_;
	protected ISound			sfx3_;
	
	protected float				x_,y_;
	protected float				vx_,vy_;
	
	protected boolean			moveUp_;
	protected boolean			moveDown_;
	protected boolean			moveLeft_;
	protected boolean			moveRight_;
}
