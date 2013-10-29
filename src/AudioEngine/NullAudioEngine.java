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


/** NullAudioEngine
 *
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
	
	public ISound LoadSound(String filename)
	{
		return new NullSound();
	}
	
	//
	//
	//
	
	protected IGameEngine	system_;

}
