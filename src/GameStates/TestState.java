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
import Engine.IGameEngine;
import InputCallbacks.KeyEventListener;
import TextureEngine.ITextureEngine;
import TextureEngine.ITexture;

import org.lwjgl.input.*;



/**
 * Gamestate used to test features during development.
 * 
 * @author Jacob
 */
public class TestState implements IGameState, KeyEventListener
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
		game_.addKeyEventListener(this);
		
		x_ = 10.0f;
		y_ = 10.0f;
		vx_ = 0;
		vy_ = 0;
		
		image1_ = gfx_.LoadTexture("image.bmp", 0x00000000);
		image2_ = gfx_.LoadTexture("image2.png", 0x0000FFFF);
		image3_ = gfx_.LoadTexture("image3.bmp", 0x00000001);
		
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
	}
	
	@Override
	public void keyPressed(int event) {
		// TODO Auto-generated method stub
		switch (event)
		{
		case Keyboard.KEY_W:	vy_+= moveSpeed;		break;
		case Keyboard.KEY_S:	vy_ += -moveSpeed;		break;
		case Keyboard.KEY_A:	vx_ += -moveSpeed;		break;
		case Keyboard.KEY_D:	vx_ += moveSpeed;		break;
		
		case Keyboard.KEY_ESCAPE:	
			game_.EndGameLoop();
			break;
		}// move
	}

	@Override
	public void keyReleased(int event) 
	{
		switch (event)
		{
		case Keyboard.KEY_W:	vy_-= moveSpeed;		break;
		case Keyboard.KEY_S:	vy_ -= -moveSpeed;		break;
		case Keyboard.KEY_A:	vx_ -= -moveSpeed;		break;
		case Keyboard.KEY_D:	vx_ -= moveSpeed;		break;
		}// move
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
	
	protected float				x_,y_;
	protected float				vx_,vy_;
	float moveSpeed = 0.1f;
	
	protected boolean			moveUp_;
	protected boolean			moveDown_;
	protected boolean			moveLeft_;
	protected boolean			moveRight_;



}












