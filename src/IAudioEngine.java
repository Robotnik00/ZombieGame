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

public interface IAudioEngine
{
	public void		Init(IGameEngine system);
	public void		Quit();
	public ISound	LoadSound(String filename);
}
