/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	ISound.java
 * 		Interface to a sound resource.	
 */

package AudioEngine;

// imports



/**	
 * Interface to a sound effect resource.
 */

public interface ISound
{
	/**
	 * Set the position of the sound, used for directional sound. Default: [0.0f,0.0f]
	 * @param x
	 * @param y
	 */
	public void	SetPos(float x, float y);
	
	/**
	 * Begin playing the sound, or resume the playing of a paused sound.
	 */
	public void	Play();
	
	/**
	 * Loop a sound continuously.
	 * When played as a loop, the sound can only be played once at a time.
	 */
	public void	Loop();
	
	/**
	 * Pause a looping sound.
	 */
	public void Pause();
	
	/**
	 * Stop playing a looping sound.
	 */
	public void Stop();
}
