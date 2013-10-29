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


/**	IAudioEngine
 * 
 * 	Interface to a sound library and sound loading function.
 */

public interface IAudioEngine
{
	public void		Init(IGameEngine system) throws Exception;
	public void		Quit();
	public ISound	LoadSound(String filename);
}
