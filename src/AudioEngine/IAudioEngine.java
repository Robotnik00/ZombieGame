/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	IAudioEngine.java
 * 		Interface to sound library.	
 */

// imports



/*	IAudioEngine
 * 
 */
package AudioEngine;

import Engine.IGameEngine;


public interface IAudioEngine
{
	public void		Init(IGameEngine system) throws Exception;
	public void		Quit();
	public ISound	LoadSound(String filename);
}
