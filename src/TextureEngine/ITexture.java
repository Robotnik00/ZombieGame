/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	ITexture.java
 * 		Interface to a texture display class	
 */

// imports



/*	ITexture
 *
 *	Interface to a texture resource.
 *
 *	To draw the texture, use the Set... functions to supply drawing parameters, then call Draw to actually execute the
 *	draw command with the given parameters. Not all drawing parameters need to be set, and each parameter is reset
 *	after each call to Draw (for example, you must set the drawing position (SetDstPos) each time before calling Draw).
 *
 *	Coordinates are specified from [0,1], with 0 being the left/bottom edge of the image or screen, and 1 being the 
 *	right/top edge. Rotations are specified in radians.
 */

package TextureEngine;

import org.lwjgl.util.vector.Matrix4f;

public interface ITexture
{
	// Texture information
	
	// Get the texture size in pixels
	public int	GetWidth();
	public int	GetHeight();
	
	
	// Drawing parameters
	
	// Set drawing position on screen.
	// The position varies depending on the orthographic perspective setup given to the ITextureEngine.
	// Default range: [-1.0,1.0] on each axis, with the origin in the center of the screen.
	public void SetPos(float x, float y);
	
	// Specify a portion of the texture to draw (ignore to draw entire texture).
	// Range: [0.0,1.0] on each axis, with the origin at the bottom-left corner.
	public void SetSrcRect(float u1, float v1, float u2, float v2);
	
	// Stretch the texture on each axis.
	public void SetScale(float x, float y);
	
	// Offset the texture origin (use in conjunction with SetRotation).
	// Range: [0.0,1.0] on each axis, with the origin at the bottom-left corner.
	public void SetOrigin(float x, float y);
	
	// Rotate the texture by (r) radians.
	public void SetRotation(float r);
	
	// Make the texture transparent (0.0 = fully invisible, 1.0 = fully opaque).
	public void SetAlpha(float a);
	
	// Blend a color with the texture (color range [0,1]).
	// <blend> is how much to blend [0,1], where the final color = texture*(1-blend) + color*(blend).
	public void SetBlendColor(float r, float g, float b, float blend);
	
	
	//
	
	// Draw the texture after setting parameters
	public void Draw();
	public void Draw(Matrix4f mat);
}
