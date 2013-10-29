/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	ITexture.java
 * 		Interface to a texture display class	
 */

package TextureEngine;

// imports
import org.lwjgl.util.vector.Matrix4f;



/**	ITexture
 *
 *	Interface to a texture resource.
 *
 *	To draw the texture, use the Set... functions to supply drawing parameters, then call Draw to actually execute the
 *	draw command with the given parameters. Not all drawing parameters need to be set, and each parameter is reset
 *	after each call to Draw (for example, you must set the drawing position (SetDstPos) each time before calling Draw).
 */

public interface ITexture
{
	// Texture information
	
	/**
	 * Get the texture width in pixels
	 * @return width
	 */
	public int	GetWidth();
	
	/**
	 * Get the texture height in pixels
	 * @return height
	 */
	public int	GetHeight();
	
	
	// Drawing parameters
	
	/** 
	 * Set drawing position on screen.
	 * The range of the coordinates varies depending on the orthographic perspective 
	 * (ITextureEngine.SetOrthoPerspective).
	 */
	public void SetPos(float x, float y);
	
	/**
	 * Specify a portion of the texture to draw (ignore to draw entire texture).
	 * Range: [0.0,1.0] on each axis, with the origin at the bottom-left corner.
	 * @param u1 Left edge
	 * @param v1 Bottom edge
	 * @param u2 Right edge
	 * @param v2 Top edge
	 */
	public void SetSrcRect(float u1, float v1, float u2, float v2);
	
	/**
	 * Stretch the texture on each axis, with 1.0 being the default value.
	 * @param x Width scale factor.
	 * @param y Height scale factor.
	 */
	public void SetScale(float x, float y);
	
	/**
	 * Offset the texture origin (use in conjunction with SetRotation).
	 * For example, use (-0.5f, -0.5f) to move the origin to the center of a texture.
	 * @param x Origin x translation value.
	 * @param y Origin y translation value.
	 */
	public void SetOrigin(float x, float y);
	
	/** 
	 * Rotate the texture.
	 * @param r Rotation in radians.
	 */
	public void SetRotation(float r);
	
	/**
	 * Make the texture transparent.
	 * @param a Alpha (0.0 = fully invisible, 1.0 = fully opaque).
	 */
	public void SetAlpha(float a);
	
	/** 
	 * Mix a color with the texture. Each color component value ranges [0.0,1.0].
	 * "Blend" is how much to blend the color, where the final color = texture*(1-blend) + color*(blend). 
	 * Range [0.0,1.0].
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @param blend Blend factor
	 */
	public void SetBlendColor(float r, float g, float b, float blend);
	
	
	// Drawing
	
	/**
	 * Draw the texture. Use the ITexture.Set... functions to setup drawing parameters before drawing.
	 * You will at least want to set the drawing position.
	 */
	public void Draw();
	
	/**
	 * Draw the texture. Use the ITexture.Set... functions to setup drawing parameters before drawing.
	 * You will at least want to set the drawing position.
	 * 
	 * @param mat Use a transformation matrix instead of setting the scale/rotate/origin functions.
	 */
	public void Draw(Matrix4f mat);
}
