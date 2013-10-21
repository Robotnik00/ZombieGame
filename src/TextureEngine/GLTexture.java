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
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.util.vector.*;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;



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
			GLTextureEngine gfx,					// parent texture engine
			int width, int height,					// texture size
			String name,							// resource name
			int textureId,							// texture id
			int shaderProgramId, 					// shader program
			int uModel, int uView,					// position transformation matrix uniforms (mat4)
			int uTexModel, 							// texture transformation matrix uniform (mat4)
			int uAlphaMul, 							// alpha blending
			int uBlendColor, int uBlendMul) 		// blending
	{
		gfx_ = gfx;
		
		width_ 	= width;
		height_ = height;
		name_ 	= name;
		
		textureId_ 	= textureId;
		
		shaderProgramId_ 	= shaderProgramId;
		uModel_				= uModel;
		uView_				= uView;
		uTexModel_			= uTexModel;
		//uSampler_			= uSampler;
		uAlphaMul_			= uAlphaMul;
		uBlendColor_		= uBlendColor;
		uBlendMul_			= uBlendMul;
		
		x_= 0.0f; y_= 0.0f;
		u1_= 0.0f; v1_= 0.0f; u2_= 0.0f; v2_= 0.0f;
		sx_= 0.0f; sy_= 0.0f;
		ox_= 0.0f; oy_= 0.0f;
		rad_= 0.0f;
		alpha_= 0.0f;
		blend_= 0.0f; r_= 0.0f; g_= 0.0f; b_= 0.0f;
		
		matrixBuffer_		= BufferUtils.createFloatBuffer(16);
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
		{ u1_ = u1; v1_ = v1; u2_ = u2; v2_ = v2; }
	public void SetScale(float x, float y) 
		{ sx_ = x; sy_ = y;}
	public void SetOrigin(float x, float y)
		{ ox_ = x; oy_ = y; }
	public void SetRotation(float r)
		{ rad_ = r; }
	public void SetAlpha(float a)
		{ alpha_ = a; }
	public void SetBlendColor(float r, float g, float b, float strength)
		{ r_ = r; g_ = g; b_ = b; blend_ = strength; }
	
	public void Draw()
	{
		// what gltexture sets:
		// program
		// modelT, viewT, texModelT,
		// texture id,
		// alpha+blend stuff
		
		// what this needs to set:
		// array and index attribs,
		// perspectiveT, 
		
		glUseProgram(shaderProgramId_);
		
		// setup model transformation (scaling, rotation, offset)
		Matrix4f modelT = new Matrix4f();
		modelT.setIdentity();
		modelT.translate(new Vector2f(ox_,oy_));
		
		// automatically scale from perspective size to pixel size?
		//modelT.scale(new Vector3f((float)width_,(float)height_,1.0f));
		
		modelT.scale(new Vector3f(sx_,sy_,1.0f));
		modelT.rotate(rad_, new Vector3f(0.0f, 0.0f, 1.0f));
		modelT.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(uModel_, false, matrixBuffer_);
		
		// setup view transformation (position)
		Matrix4f viewT = new Matrix4f();
		viewT.setIdentity();
		viewT.translate(new Vector2f(x_,y_));
		viewT.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(uView_, false, matrixBuffer_);
		
		// texture model
		Matrix4f texModelT = new Matrix4f();
		texModelT.setIdentity();
		// FIXME: do this
		texModelT.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(uTexModel_, false, matrixBuffer_);
		
		// texture sampler
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureId_);
		//glUniform1i(uSampler_, 0);
		
		// fragment shader stuff
		glUniform1f(uAlphaMul_, alpha_);
		glUniform1f(uBlendMul_, blend_);
		glUniform3f(uBlendColor_, r_, g_, b_);
		
		// finally draw the texture
		gfx_.DrawTexture();
		
		// undo stuff
		ResetDrawingParams();
		glUseProgram(0);
	}

	//
	// protected members
	//
	
	protected GLTextureEngine	gfx_;
	
	// texture information
	protected int			width_, height_;
	protected String		name_;
	
	// texture
	protected int			textureId_;
	
	// shader variables
	protected int			shaderProgramId_;
	protected int			uModel_;
	protected int			uView_;
	protected int			uTexModel_;
	//protected int			uSampler_;
	protected int			uAlphaMul_;
	protected int			uBlendColor_;
	protected int			uBlendMul_;
	
	// drawing parameters
	protected float			x_, y_;
	protected float			u1_, v1_, u2_, v2_;
	protected float 		sx_, sy_;
	protected float 		ox_, oy_;
	protected float 		rad_;
	protected float 		alpha_;
	protected float 		blend_, r_, g_, b_;
	
	// stuff
	protected FloatBuffer	matrixBuffer_;
	
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
		alpha_ = 1.0f; 
		blend_ = 0.0f; r_ = 0.0f; g_ = 0.0f; b_ = 0.0f;
	}
}







