/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	NullAudioEngine.java
 * 		Placeholder class in case the OpenAL class can't initialize, or the sound should be muted or something.
 * 		Plays no sounds.
 */

package AudioEngine;

// imports
import Engine.IGameEngine;



/** 
 *	An empty sound engine for when sound is disabled or unneeded
 */

public class NullAudioEngine implements IAudioEngine
{
	public void	Init(IGameEngine system) throws Exception
	{
		system_ = system;
		system_.LogMessage("NullAudioEngine::Init");
	}
	
	public void	Quit()
	{
		system_.LogMessage("NullAudioEngine::Quit");
	}
	
	public String	GetAudioInfo()
	{
		return "NULL";
	}
	
	public void		SetVolume(float volume)
	{
		// herp derp
	}
	
	public float	GetVolume()
	{
		return 1.0f;
	}
	
	public void		SetListenerPosition(float x, float y)
	{
		// whatever bro.
	}
	
	public ISound LoadSound(String filename) throws Exception
	{
		return new NullSound();
	}
	
	//
	// protected members
	//
	
	protected IGameEngine	system_;
}
