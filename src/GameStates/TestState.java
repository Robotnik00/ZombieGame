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
import Geometry.AABB;
import TextureEngine.ITextureEngine;
import TextureEngine.ITexture;
import Utility.BitmapFont;

import org.lwjgl.input.*;
import org.lwjgl.util.vector.Vector2f;



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
		
		x1_ = 10.0f;
		y1_ = 10.0f;
		vx1_ = 0;
		vy1_ = 0;
		bounds_ = new AABB(1.0f, 1.0f);
		
		x2_ = 4.0f;
		y2_ = 10.0f;
		wall_ = new AABB(1.0f, 1.0f);
		
		image1_ = gfx_.LoadTexture("image.bmp", 0x00000000);
		image2_ = gfx_.LoadTexture("image2.png", 0x0000FFFF);
		image3_ = gfx_.LoadTexture("image3.bmp", 0x00000001);
		font_ = gfx_.LoadTexture("font1.png", 0);
		
		music_ = snd_.LoadSound("music.wav");	musicx_=9.5f;	musicy_=7.0f;
		sfx1_ = snd_.LoadSound("sfx1.wav");		sfx1x_ =0.0f;	sfx1y_ =3.0f;
		sfx2_ = snd_.LoadSound("sfx2.wav");		sfx2x_ =9.5f;	sfx2y_ =12.0f;
		sfx3_ = snd_.LoadSound("sfx3.wav");		sfx3x_ =19.0f;	sfx3y_ =3.0f;
		
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
		int mus=0,s1=0,s2=0,s3=0;
		float v=0;
		
		// move
		x1_ += vx1_; 
		y1_ += vy1_;
		
		snd_.SetListenerPosition(x1_, y1_);
		
		vx1_ = 0;
		vy1_ = 0;
		
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
	        
	        case Keyboard.KEY_1:	mus=1;			game_.LogMessage("Loop music."); 	break;
	        case Keyboard.KEY_2:	music_.Pause();	game_.LogMessage("Pause music.");	break;
	        case Keyboard.KEY_3:	music_.Stop();	game_.LogMessage("Stop music.");	break;
	        case Keyboard.KEY_4:	s1=1;			game_.LogMessage("Play SFX 1");		break;
	        case Keyboard.KEY_5:	s2=1;			game_.LogMessage("Play SFX 2");		break;
	        case Keyboard.KEY_6:	s3=1;			game_.LogMessage("Play SFX 3");		break;
	        case Keyboard.KEY_7:	v=-0.1f;		game_.LogMessage("Decreasing volume by 0.1."); break;
	        case Keyboard.KEY_8:	v=0.1f;			game_.LogMessage("Increasing volume by 0.1."); break;
	        
	        case Keyboard.KEY_ESCAPE:        
	                game_.EndGameLoop();
	                break;
	        }
        }
        
        // try to move
        float moveSpeed = 0.1f;
        if (moveUp_)	vy1_ += moveSpeed;
        if (moveDown_)	vy1_ += -moveSpeed;
        if (moveLeft_)	vx1_ += -moveSpeed;
        if (moveRight_)	vx1_ += moveSpeed;
        
        // move aabbs
        bounds_.simpleTransform(x1_+vx1_, y1_+vy1_);
        wall_.simpleTransform(x2_, y2_);
        
        colliding_ = false;
        if (bounds_.intersects(wall_))
        {
        	colliding_ = true;
        	Vector2f move = bounds_.solveCollision(wall_);
        	vx1_ += move.x;
        	vy1_ += move.y;
        	System.out.println("Moved: "+move.x+"x, "+move.y+"y.");
        }
        
        // play sounds
        if (mus==1)	{music_.SetPos(musicx_,musicy_); 	music_.Loop();}
        if (s1==1)	{sfx1_.SetPos(sfx1x_,sfx1y_); 		sfx1_.Play();}
        if (s2==1)	{sfx2_.SetPos(sfx2x_,sfx2y_); 		sfx2_.Play();}
        if (s3==1)	{sfx3_.SetPos(sfx3x_,sfx3y_); 		sfx3_.Play();}
        
        // adjust volume
        if (v != 0.0f)
        {
        	v += snd_.GetVolume();
        	if (v < 0.0f)	v = 0.0f;
        	if (v > 1.0f)	v = 1.0f;
        	snd_.SetVolume(v);
        }
		
		// display the frame rate
		game_.SetWindowTitle("FPS: "+game_.GetFrameRate());
	}
	
	public void	Draw(float delta)
	{
		//gfx_.ClearScreen();
		
		float dx = x1_ + vx1_ * delta;
		float dy = y1_ + vy1_ * delta;
		
		// draw a bunch of tiles
		for (int x=0; x < 20; x++)
		{
			for (int y=0; y < 15; y++)
			{
				image3_.SetPos((float)x, (float)y);
				image3_.Draw();
			}
		}
		
		// draw at the origin, and rotate toward the cursor
		image2_.SetOrigin(-0.5f, -0.5f);
		image2_.SetRotation((float)Math.atan2(dy-10.0f, dx-10.0f));
		image2_.SetPos(10.0f, 10.0f);
		image2_.Draw();
		
		
		// controlled by the keyboard
		/*
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
		*/
		
		// draw font
		bitmapFont_.SetPosition(0.0f, 14.5f);
		bitmapFont_.SetScale(0.5f,0.5f);
		bitmapFont_.SetKerning(0.4f);
		bitmapFont_.DrawString(
			"Audio engine: "+snd_.GetAudioInfo()+"\n" +
			"1) Play music (loop).		\n" +
			"2) Pause music.			\n" +
			"3) Stop music.				\n" +
			"4) Play sound 1 (left).	\n" +
			"5) Play sound 2 (center).	\n" +
			"6) Play sound 3 (right).	\n" +
			"Current volume: "+snd_.GetVolume()+"\n" +
			"7) Increase volume.		\n" +
			"8) Decrease volume.		\n"
		);
		
		// draw primitive shapes
		/*
		// sound 1 marker
		gfx_.SetDrawColor(1.0f, 0.0f, 0.0f, 1.0f);
		gfx_.DrawRectangle(sfx1x_, sfx1y_, sfx1x_+1.0f, sfx1y_+1.0f);
		// sound 2
		gfx_.SetDrawColor(0.0f, 1.0f, 0.0f, 1.0f);
		gfx_.DrawRectangle(sfx2x_, sfx2y_, sfx2x_+1.0f, sfx2y_+1.0f);
		// sound 3
		gfx_.SetDrawColor(0.0f, 0.0f, 1.0f, 1.0f);
		gfx_.DrawRectangle(sfx3x_, sfx3y_, sfx3x_+1.0f, sfx3y_+1.0f);
		// music
		gfx_.SetDrawColor(1.0f, 1.0f, 1.0f, 1.0f);
		gfx_.DrawRectangle(musicx_, musicy_, musicx_+1.0f, musicy_+1.0f);
		*/
		//gfx_.SetDrawColor(0.0f, 1.0f, 0.0f, 1.0f);
		//gfx_.DrawLine(0, 0, 2.0f, 2.0f);
		//gfx_.DrawLine(0, 2.0f, 2.0f, 0);
		
		// this object's bounding box
		gfx_.SetDrawColor(1.0f, 0.0f, 0.0f, 1.0f);
		bounds_.simpleTransform(dx, dy);
		gfx_.DrawRectangle(
			bounds_.bl.x,
			bounds_.bl.y,
			bounds_.tr.x,
			bounds_.tr.y
		);
		
		// a "wall"
		gfx_.SetDrawColor(0.0f, 1.0f, 0.0f, 1.0f);
		gfx_.DrawRectangle(
			wall_.bl.x,
			wall_.bl.y,
			wall_.tr.x,
			wall_.tr.y
		);
		
		bitmapFont_.SetPosition(0.0f, 0.5f);
		bitmapFont_.SetScale(0.5f,  0.5f);
		
		if (colliding_)	
			bitmapFont_.DrawString("Colliding");
		else
			bitmapFont_.DrawString("Not colliding.");
		
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
	protected float				musicx_,musicy_;
	protected ISound			sfx1_;
	protected float				sfx1x_,sfx1y_;
	protected ISound			sfx2_;
	protected float				sfx2x_,sfx2y_;
	protected ISound			sfx3_;
	protected float				sfx3x_,sfx3y_;
	
	protected float				x1_,y1_;
	protected float				vx1_,vy1_;
	protected AABB				bounds_;
	
	protected float				x2_,y2_;
	protected AABB				wall_;
	
	protected boolean			colliding_;
	
	protected boolean			moveUp_;
	protected boolean			moveDown_;
	protected boolean			moveLeft_;
	protected boolean			moveRight_;
}
