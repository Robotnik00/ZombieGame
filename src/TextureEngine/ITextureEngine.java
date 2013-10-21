/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	ITextureEngine.java
 * 		Texture resource manager interface.	
 */

package TextureEngine;

// imports
import Engine.IGameEngine;



/*	ITextureEngine
 * 
 * 	Interface to a graphics resource management class. 
 */

public interface ITextureEngine
{
	// Initializes the display window and graphics libraries.
	// Exception is thrown on failure.
	public void	Init(IGameEngine system, int displayWidth, int displayHeight) throws Exception;
	
	// De-initialize class and clean up resources. 
	// Some libraries require manual resource management (OpenGL), which Java's GC can't take care of.
	public void	Quit();
	
	
	//
	// general drawing functions
	//
	
	// clear the screen to a solid color
	public void	SetClearColor(float r, float g, float b);
	public void ClearScreen();
	
	// Set the area of the screen (in pixels) for drawing operations to target.
	// All operations will be scaled+clipped to within this area.
	public int	GetScreenWidth();
	public int	GetScreenHeight();
	public void SetDrawingArea(int x, int y, int w, int h);	
	
	// Establish the screen coordinate system for 2d drawing operations.
	//	left,right,top,down	- the coordinate value representing this edge of the screen.
	// By default, OpenGL assigns -1.0 to the left/bottom edges, and 1.0 to the right/top.
	public void	SetOrthoPerspective(float left, float right, float top, float bottom);

	// basic drawing operations
//	public void SetDrawColor(float r, float g, float b);
//	public void	SetDrawAlpha(float a);
//	public void DrawPoint(float x, float y);
//	public void DrawLine(float x1, float y1, float x2, float y2);
//	public void DrawRectangle(float x1, float y1, float x2, float y2);
	
	
	//
	// images/textures
	//
	
	// Load a texture from a file.
	//	filename	- name of file
	//	colorkey	- Transparent color of the texture
	// Colorkey is specified in BGRA format, with B=least sig. 8 bits.
	public ITexture	LoadTexture(String filename, int colorkey);
}
