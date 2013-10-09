/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	TestState.java
 * 		Development gamestate.	
 */

// imports

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
	}
		
	public void	Quit()
	{
		//
	}
		
	public void	Update()
	{
		//
	}
	
	public void	Draw(float delta)
	{
		//
	}
	
	
	
	//
	// protected members
	//
	
	ITextureEngine	gfx_;
	IAudioEngine	snd_;
	IGameEngine		game_;
}












