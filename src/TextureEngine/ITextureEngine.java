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



/**
 * Interface to a graphics resource management class. 
 */

public interface ITextureEngine
{
	/** 
	 * Initializes the display window and graphics libraries.
	 * 
	 * @param system Game Engine
	 * @param displayWidth Drawing area width in pixels.
	 * @param displayHeight Drawing area height in pixels.
	 * @throws Exception
	 */
	public void	Init(IGameEngine system, int displayWidth, int displayHeight) throws Exception;
	
	/** 
	 * De-initialize class and clean up resources. 
	 * Some libraries require manual resource management (OpenGL), which Java's GC can't take care of.
	 */
	public void	Quit();
	
	
	//
	// general drawing functions
	//
	
	/** 
	 * Set the color when clearing the screen. Color components range [0.0,1.0]
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 */
	public void	SetClearColor(float r, float g, float b);
	
	/**
	 * Clear the screen to the set clear color.
	 */
	public void ClearScreen();
	
	/**
	 * Get the width of the screen in pixels.
	 */
	public int	GetScreenWidth();
	
	/**
	 * Get the height of the screen in pixels.
	 */
	public int	GetScreenHeight();
	
	/** 
	 * Set the region of the screen (in pixels) for drawing operations to target.
	 * All operations will be scaled and clipped to within this area.
	 * 
	 * @param x X coordinate of drawing region origin.
	 * @param y Y coordinate of drawing region origin.
	 * @param w Width of drawing region.
	 * @param h Height of drawing region.
	 */
	public void SetDrawingArea(int x, int y, int w, int h);	
	
	/**
	 * Establish the screen coordinate system for 2d drawing operations.
	 * By default, OpenGL assigns -1.0 to the left/bottom edges, and 1.0 to the right/top.
	 * @param left - Left edge
	 * @param right - Right edge
	 * @param bottom - Bottom edge
	 * @param top - Top edge
	 */
	public void	SetOrthoPerspective(float left, float right, float bottom, float top);

	// basic drawing operations
	public void SetDrawColor(float r, float g, float b, float a);
	public void DrawPoint(float x, float y);
	public void DrawLine(float x1, float y1, float x2, float y2);
	public void DrawRectangle(float x1, float y1, float x2, float y2);
	
	
	//
	// images/textures
	//
	
	/** Load a texture from a file.
	 * 
	 * @param filename Name of image file
	 * @param colorkey Transparent color of image, specified in BGRA format with B=least significant byte.
	 * @return
	 */
	public ITexture	LoadTexture(String filename, int colorkey);
	
	/**
	 * Loads a grayscale image, copying the color component into the alpha component (useful for fonts).
	 * 
	 * @param filename Name of image file.
	 * @param colorkeyWhite If true, white is the transparent color (alpha=0) and black is the opaque color (alpha=1).
	 * @return
	 */
	public ITexture LoadGrayscaleFont(String filename, boolean colorkeyWhite);
	
	//
	// utility functions
	//
	
	/**
	 * Apply drawing perspective transformations to window coordinates.
	 * @param x Coordinate in pixels.
	 * @param y Coordinate in pixels.
	 * @return float[2], [0] = scaled x coordinate, [1] = scaled y coordinate
	 */
	public float[] ScaleWindowCoordinates(int x, int y);
}
