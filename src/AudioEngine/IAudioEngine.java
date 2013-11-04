/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	IAudioEngine.java
 * 		Interface to sound library.	
 */

package AudioEngine;

// imports
import Engine.IGameEngine;



/**	
 * Interface to a sound library and sound loading function.
 */

public interface IAudioEngine
{
	/**
	 * Initialize the sound engine.
	 * @param system
	 * @throws Exception
	 */
	public void		Init(IGameEngine system) throws Exception;
	
	/**
	 * Shutdown the sound engine, clean up sound resources.
	 */
	public void		Quit();
	
	/**
	 * Return a string describing the sound engine/driver.
	 */
	public String	GetAudioInfo();
	
	/**
	 * Set the master sound volume. Range: 0.0 = muted, 1.0 = normal volume for each sound. 
	 * No upper limit on volume is defined, but values > 1.0 are not guaranteed to be louder or do anything.
	 * @param volume 
	 */
	public void		SetVolume(float volume);
	
	/**
	 * Return the current master volume.
	 */
	public float	GetVolume();
	
	/**
	 * Set the position of the listener, used for directional sounds.
	 * This should only affect panning of the sound; sound volume is NOT set to drop off over a distance.
	 * @param x
	 * @param y
	 */
	public void		SetListenerPosition(float x, float y);
	
	/**
	 * Loads sound files.
	 * If the file has not been loaded, a sound resource will be created and returned.
	 * If it has been loaded, the resource can be accessed by supplying the same filename.
	 * @param filename Filename of sound effect. Supports: wav. Yes, just wav. For now.
	 */
	public ISound	LoadSound(String filename) throws Exception;
}
