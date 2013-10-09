/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	NullAudioEngine.java
 * 		Placeholder class in case the OpenAL class can't initialize, or the sound should be muted or something.
 * 		Plays no sounds.
 */

// imports

public class NullAudioEngine implements IAudioEngine
{
	public void	Init(IGameEngine system)
	{
		system_ = system;
		system_.LogMessage("NullAudioEngine::Init);
	}
	
	public void	Quit()
	{
		system_.LogMessage("NullAudioEngine::Quit");
	}
	
	public ISound LoadSound(String filename)
	{
		//
	}
	
	//
	//
	//
	
	protected IGameEngine	system_;	
}
