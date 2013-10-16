/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	ITextureEngine.java
 * 		Texture resource manager interface.	
 */

// imports



/*	ITextureEngine
 * 
 * 	Interface to a texture resource management class. 
 */

package TextureEngine;

import Engine.IGameEngine;

public interface ITextureEngine
{
	// Initializes the display window and graphics libraries.
	// Exception is thrown on failure.
	public void	Init(IGameEngine system) throws Exception;
	
	// De-initialize class and clean up resources. 
	// Some libraries require manual resource management (OpenGL), which Java's GC can't take care of.
	public void	Quit();
	
	// Load a texture from a file.
	//	filename	- name of file
	//	colorkey	- Transparent color of the texture
	// Colorkey is specified in BGRA format, with B=least sig. 8 bits.
	public ITexture	LoadTexture(String filename, int colorkey);
}
