/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	GLTexture.java
 * 		OpenGL texture display class	
 */

package TextureEngine;

// imports



/*	GLTexture
 *
 *	Contains a loaded texture which can be drawn via OpenGL.
 *
 *	These are created and returned by an ITextureEngine, which actually manages OpenGL resources.
 */


public class GLTexture implements ITexture
{
	// lots of gl information must be passed
	public GLTexture(
			int width, int height,							// texture name
			String name,									// resource name
			int textureId,									// texture id
			int quadBufferId, int quadIndexBufferId,		// vertex buffer objects
			int shaderProgramId, 							// shader program
			int attribPosition, /*int attribTexCoord,*/ 	// shader attribute arrays for position
			int uModel, int uView, int uPerspective,		// position transformation matrix uniforms (mat3)
			int uTexModel, 									// texture transformation matrix uniform (mat3)
			int uSampler,									// texture sampler2d uniform		
			int uAlphaMul, int uBlendColor, int uBlendMul) 	// alpha and blending functions, and blend color
	{
		width_ 	= width;
		height_ = height;
		name_ 	= name;
		
		textureId_ 	= textureId;
		
		quadBufferId_ 		= quadBufferId;
		quadIndexBufferId_ 	= quadIndexBufferId;
		
		shaderProgramId_ 	= shaderProgramId;
		attribPosition_ 	= attribPosition;
		uModel_				= uModel;
		uView_				= uView;
		uPerspective_		= uPerspective;
		uTexModel_			= uTexModel;
		uSampler_			= uSampler;
		uAlphaMul_			= uAlphaMul;
		uBlendColor_		= uBlendColor;
		uBlendMul_			= uBlendMul;
	}
	
	public String	GetTextureName()
		{return name_;}
	public int		GetTextureId()
		{return textureId_;}
	
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
	protected int			width_, height_;
	protected String		name_;
	
	// texture
	protected int			textureId_;
	// buffers
	protected int			quadBufferId_;
	protected int			quadIndexBufferId_;
	// shader variables
	protected int			shaderProgramId_;
	protected int			attribPosition_;
	protected int			attribTexCoord_;
	protected int			uModel_;
	protected int			uView_;
	protected int			uPerspective_;
	protected int			uTexModel_;
	protected int			uSampler_;
	protected int			uAlphaMul_;
	protected int			uBlendColor_;
	protected int			uBlendMul_;
	
	// drawing parameters
	protected float			x_, y_;
	protected float			u1_, v1_, u2_, v2_;
	protected float 		sx_, sy_;
	protected float 		ox_, oy_;
	protected float 		rad_;
	protected float 		a_;
	protected float 		strength_, r_, g_, b_;
	
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







