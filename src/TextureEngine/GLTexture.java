/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	GLTexture.java
 * 		OpenGL texture display class	
 */

// imports



/*	GLTexture
 *
 *	Contains a loaded texture which can be drawn via OpenGL.
 *
 *	These are created and returned by an ITextureEngine, which actually manages OpenGL resources.
 */
package TextureEngine;

public class GLTexture implements ITexture
{
	public GLTexture() {}
	
	//
	// interface methods
	//
	
	public int	GetWidth()	{return width_;}
	public int	GetHeight()	{return height_;}
	
	public void SetPos(float x, float y)
		{ x_ = x; y_ = y; }
	public void SetSrcRect(float u1, float v1, float u2, float v2)
		{u1_ = u1; v1_ = v1; u2_ = u2; v2_ = v2; }
	public void SetScale(float x, float y) 
		{sx_ = x; sy_ = y;}
	public void SetOrigin(float x, float y)
		{ ox_ = x; oy_ = y; }
	public void SetRotation(float r)
		{ rad_ = r; }
	public void SetAlpha(float a)
		{ a_ = a; }
	public void SetBlendColor(float r, float g, float b, float strength)
		{ r_ = r; g_ = g; b_ = b; strength_ = strength; }
	
	public void Draw()
	{
		// set drawing state stuff
		// set shader program
		// set uniforms/attribs
		
		ResetDrawingParams();
		// undo gl stuff
	}

	//
	// protected members
	//
	
	// texture information
	protected int	width_, height_;
	
	// GL information
	protected int	textureId_;
	// other texture stuff? VBOs, index array, etc.
	protected int	shaderId_;
	// shader variables/uniforms
	
	// drawing parameters
	protected float	x_, y_;
	protected float	u1_, v1_, u2_, v2_;
	protected float sx_, sy_;
	protected float ox_, oy_;
	protected float rad_;
	protected float a_;
	protected float strength_, r_, g_, b_;
	
	//
	// protected methods
	//
	
	protected void	ResetDrawingParams()
	{
		x_ = 0.0f;
		y_ = 0.0f;
		u1_ = 0.0f; v1_ = 0.0f; u2_ = 1.0f; v2_ = 1.0f;
		sx_ = 1.0f; sy_ = 1.0f;
		ox_ = 0.0f; oy_ = 0.0f;
		rad_ = 0.0f;
		a_ = 1.0f; 
		strength_ = 0.0f; r_ = 0.0f; g_ = 0.0f; b_ = 0.0f;
	}
}







