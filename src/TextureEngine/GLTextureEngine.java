/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	GLTextureEngine.java
 * 		OpenGL texture loading.	
 */

package TextureEngine;

// imports
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
//import static org.lwjgl.opengl.GL31.*;
//import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;

import Engine.IGameEngine;



/**	
 * This class uses OpenGL to load/manage textures.
 */
public class GLTextureEngine implements ITextureEngine
{
	//
	// public methods
	//
	
	/**
	 * Initialize an opengl context with the given version.
	 * Works for 3.x only!
	 * @param minor Minor OpenGL version, use with major version 3.
	 */
	public GLTextureEngine(int minor)
	{
		system_ 			= null;
		displayWidth_ 		= 0;
		displayHeight_ 		= 0;
		
		minorVersion_		= minor;
		
		textures_			= new ArrayList<GLTexture>();
		
		perspectiveT_ 		= new Matrix4f();
		perspectiveT_.setIdentity();
		
		updatePerspective_	= true;
		matrixBuffer_		= BufferUtils.createFloatBuffer(16);
		perLeft 			= -1.0f;
		perRight 			= 1.0f;
		perBottom 			= -1.0f;
		perTop 				= 1.0f;
		
		drawingState_		= 0;	// 0=none, 1=textures, 2=primitives
		currentTexture_		= -1;
		
		r_ = 0.0f;
		g_ = 0.0f;
		b_ = 0.0f;
		a_ = 1.0f;
	}
	
	/**
	 * Setup the texture drawing state with a given texture.
	 * @param textureId GL texture buffer id.
	 */
	public void SetTextureDrawingState(int textureId)
	{
		if (drawingState_ != 1)
		{
			// undo primitives state
			UnsetPrimitiveDrawingState();
			
			// set program
			glUseProgram(tex_programId_);
			
			// set blend modes
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			// setup vertex buffers
			glBindVertexArray(quadVAOId_);
			glEnableVertexAttribArray(0);
			
			// bind index buffer
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadElementIndexId_);
			
			// force a perspective update when state is first changed
			updatePerspective_ = true;
			
			drawingState_ = 1;
		}
		
		// set texture
		if (textureId != currentTexture_)
		{
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, textureId);
			currentTexture_ = textureId;
		}
	}
	
	/**
	 * Undo texture drawing state setup.
	 */
	public void	UnsetTextureDrawingState()
	{
		glDisable(GL_BLEND);
		
		glBindTexture(GL_TEXTURE_2D,0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		//glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		
		glBindVertexArray(0);
		
		currentTexture_ = -1;
		drawingState_ = 0;
		updatePerspective_ = true;
	}
	
	/**
	 * Draws a texture.
	 */
	public void	DrawTexture()
	{
		if (drawingState_ == 1)
		{
			// set perspective
			if (updatePerspective_ == true)
			{
				//FloatBuffer matrixBuffer_ = BufferUtils.createFloatBuffer(16);
				perspectiveT_.store(matrixBuffer_); matrixBuffer_.flip();
				glUniformMatrix4(tex_uPerspective_, false, matrixBuffer_);
				updatePerspective_ = false;
			}
						
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
			//glDrawArrays(GL_TRIANGLES, 0, 6);
		}
		
		// cleanup is done when states change
	}
	
	/**
	 * Setup opengl for drawing primitive shapes.
	 */
	public void	SetPrimitiveDrawingState()
	{
		if (drawingState_ != 2)
		{
			//
			UnsetTextureDrawingState();
			
			// set program
			glUseProgram(prim_programId_);
			
			// set blend modes
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			// setup vertex buffers
			glBindVertexArray(quadVAOId_);
			glEnableVertexAttribArray(0);
			
			// bind index buffer (this is done in drawing functions)
			
			// force a perspective update when state is first changed
			updatePerspective_ = true;
			
			drawingState_ = 2;
		}
	}
	
	/** 
	 * unset primitive drawing state
	 */
	public void	UnsetPrimitiveDrawingState()
	{
		glDisable(GL_BLEND);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glBindVertexArray(0);
		
		currentTexture_ = -1;
		drawingState_ = 0;
		updatePerspective_ = true;
	}
	
	
	
	//
	// ITextureEngine interface methods
	//
	
	public void	Init(IGameEngine system, int displayWidth, int displayHeight) throws Exception
	{
		system_ = system;
		system_.LogMessage("GLTextureEngine::Init");
		
		displayWidth_ = displayWidth;
		displayHeight_ = displayHeight;
		
		// setup opengl display
		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs contextAttribs = null;
		
		if (minorVersion_ < 2)
		{
			// below 3.2, core, compatibility, forward, etc don't exist
			contextAttribs = new ContextAttribs(3,minorVersion_);
		}
		else
		{
			contextAttribs = new ContextAttribs(3,minorVersion_)
				.withForwardCompatible(true)
				.withProfileCore(true);
		}
		
		// try
		Display.setDisplayMode(new DisplayMode(displayWidth_, displayHeight_));
		Display.create(pixelFormat, contextAttribs);
		
		// checking the version is apparently too much for osx to handle.
		//system_.LogMessage("GLTextureEngine::Init: Testing OpenGL, got version: " + 
		//		glGetInteger(GL_MAJOR_VERSION) + "." + glGetInteger(GL_MINOR_VERSION));
		
		// init gl resources
		SetupShaders();
		SetupBuffers();
		
		// init drawing state
		SetPrimitiveDrawingState();
	}
	
	public void	Quit()
	{
		system_.LogMessage("GLTextureEngine::Quit");
		DestroyResources();
	}
	
	
	public void	SetClearColor(float r, float g, float b)
	{
		glClearColor(r,g,b,1.0f);
	}
	
	public void ClearScreen()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	
	public int	GetScreenWidth()
	{
		return displayWidth_;
	}
	
	public int	GetScreenHeight()
	{
		return displayHeight_;
	}
	
	
	public void SetDrawingArea(int x, int y, int w, int h)
	{
		glViewport(x,y,w,h);
	}
	
	
	public void	SetOrthoPerspective(float left, float right, float bottom, float top)
	{
		float near = 1.0f;
		float far = -1.0f;
		float rw = 1 / (right - left);
		float rh = 1 / (top - bottom);
		float rd = 1 / (far - near);
		Matrix4f p = new Matrix4f();
		
		p.setIdentity();
		// p.m<column><row>
		p.m00 = 2.0f*rw;	p.m10 = 0;			p.m20 = 0;			p.m30 = -(right+left)*rw;
		p.m01 = 0;			p.m11 = 2.0f*rh;	p.m21 = 0;			p.m31 = -(top+bottom)*rh;
		p.m02 = 0;			p.m12 = 0;			p.m22 = -2.0f*rd;	p.m32 = -(far+near)*rd;
		p.m03 = 0;			p.m13 = 0;			p.m23 = 0;			p.m33 = 1.0f;
		
		perspectiveT_ = p;
		
		perLeft = left;
		perRight = right;
		perBottom = bottom;
		perTop = top;
		
		updatePerspective_ = true;
	}
	
	
	
	//
	
	public void SetDrawColor(float r, float g, float b, float a)
	{
		r_ = r;
		g_ = g;
		b_ = b;
		a_ = a;
	}
	
	public void DrawPoint(float x, float y)
	{
		if (drawingState_ != 2)
			SetPrimitiveDrawingState();
		
		// setup model transformation (scaling, rotation, offset)
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		m.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(prim_uModel_, false, matrixBuffer_);
		
		// view
		m.setIdentity();
		m.translate(new Vector2f(x,y));
		m.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(prim_uView_, false, matrixBuffer_);
		
		// set perspective
		if (updatePerspective_ == true)
		{
			//FloatBuffer matrixBuffer_ = BufferUtils.createFloatBuffer(16);
			perspectiveT_.store(matrixBuffer_); matrixBuffer_.flip();
			glUniformMatrix4(tex_uPerspective_, false, matrixBuffer_);
			updatePerspective_ = false;
		}
		
		// draw color
		glUniform4f(prim_uColor_, r_, g_, b_, a_);
		
		// draw only the first point (no elements)
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDrawArrays(GL_POINTS, 0, 1);
	}
	
	public void DrawLine(float x1, float y1, float x2, float y2)
	{
		if (drawingState_ != 2)
			SetPrimitiveDrawingState();
		
		// setup model transformation (scaling, rotation, offset)
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		m.scale(new Vector3f(x2-x1,y2-y1,1.0f));
		m.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(prim_uModel_, false, matrixBuffer_);
		
		// view
		m.setIdentity();
		m.translate(new Vector2f(x1,y1));
		m.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(prim_uView_, false, matrixBuffer_);
		
		// set perspective
		if (updatePerspective_ == true)
		{
			//FloatBuffer matrixBuffer_ = BufferUtils.createFloatBuffer(16);
			perspectiveT_.store(matrixBuffer_); matrixBuffer_.flip();
			glUniformMatrix4(tex_uPerspective_, false, matrixBuffer_);
			updatePerspective_ = false;
		}
		
		// draw color
		glUniform4f(prim_uColor_, r_, g_, b_, a_);
		
		// draw only the first point (no elements)
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, lineElementIndexId_);
		glDrawElements(GL_LINES, 2, GL_UNSIGNED_BYTE, 0);
	}
	
	public void DrawRectangle(float x1, float y1, float x2, float y2)
	{
		SetPrimitiveDrawingState();
		
		// setup model transformation
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		m.scale(new Vector3f(x2-x1, y2-y1, 1.0f));
		m.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(prim_uModel_, false, matrixBuffer_);
		
		// view
		m.setIdentity();
		m.translate(new Vector2f(x1,y1));
		m.store(matrixBuffer_); matrixBuffer_.flip();
		glUniformMatrix4(prim_uView_, false, matrixBuffer_);
		
		// set perspective
		if (updatePerspective_ == true)
		{
			perspectiveT_.store(matrixBuffer_); matrixBuffer_.flip();
			glUniformMatrix4(prim_uPerspective_, false, matrixBuffer_);
			updatePerspective_ = false;
		}
		
		// draw color
		glUniform4f(prim_uColor_, r_, g_, b_, a_);
		
		// draw
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadElementIndexId_);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
	}

	
	
	//
	
	public GLTexture LoadTexture(String filename, int colorkey)
	{
		int tex;
		int bufWidth,bufHeight,pitch;
		int pixel;
		GLTexture gltex = null;
		
		// search loaded textures, see if we have already loaded this.
		for (int i=0; i < textures_.size(); i++)
		{
			if (textures_.get(i).GetTextureName().compareTo(filename) == 0)
			{
				//system_.LogMessage("GLTextureEngine::LoadTexture: Filename matched loaded texture: "+filename);
				return textures_.get(i);
			}
		}
		
		// the texture hasn't been loaded yet, so load the texture file
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (Exception e) {
			system_.LogMessage("GLTextureEngine::LoadTexture: Failed to load file: "+filename+"\n"+e.getMessage());
			return null;
		}
		
		// make a buffer for the image, the next power of two size bigger
		
		bufWidth=1;
		while (bufWidth < img.getWidth())	
			bufWidth *= 2;
		bufHeight=1;
		while (bufHeight < img.getHeight())	
			bufHeight *= 2;
		
		// non power of two images will be padded
		if (bufWidth != img.getWidth() || bufHeight != img.getHeight())
		{
			system_.LogMessage(
				"GLTextureEngine::LoadTexture: Warning: Image size is not a power of two! Image data will be padded.\n");
		}
		
		// allocate array for pixel data
		int[] pixelData = new int [bufWidth*bufHeight];
		for (int i=0; i < pixelData.length; i++)
			pixelData[i] = 0;
		
		// copy image data to padded buffer
		pitch=bufWidth;
		for (int y=0; y < img.getHeight(); y++)
		{
			for (int x=0; x < img.getWidth(); x++)
			{
				// get"RGB" returns "ARGB" which is actually "BGRA". go figure.
				// also, flip the image vertically.
				pixel = img.getRGB(x, img.getHeight()-y-1);
				
				// check colorkey match
				if (((pixel & 0x00FFFFFF)) == colorkey)
					pixel &= 0x00FFFFFF;	// colorkey match, set alpha to zero.
				else
					pixel |= 0xFF000000;	// visible color, set alpha to max.
					
				pixelData[y*pitch+x] = pixel;
			}
		}
		
		// make a buffer for the pixel data (can't use wrap() here?)
		IntBuffer texBuf = BufferUtils.createIntBuffer(pixelData.length);
		texBuf.put(pixelData);
		texBuf.flip();
		
		// i guess we need to activate a texture unit for this?
		glActiveTexture(GL_TEXTURE0);
		
		// create an opengl texture buffer
		tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		
		// each component is 1 byte
		glPixelStorei(GL_UNPACK_ALIGNMENT,1);
		
		// upload the texture data
		glTexImage2D(
				GL_TEXTURE_2D,		// 2d texture target
				0,					// level of detail (0=normal)
				GL_RGBA,			// format to store the image in video memory
				bufWidth,
				bufHeight,
				0,					// border
				GL_BGRA,			// format of the image data supplied
				GL_UNSIGNED_BYTE,	// data is supplied as a buffer of bytes
				texBuf
				);
		
		// smooth filtering when scaled up
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		// texture repeats for coords > 1.0
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		// generate mipmaps?
		glGenerateMipmap(GL_TEXTURE_2D);
		
		// gl cleanup
		glBindTexture(GL_TEXTURE_2D,0);
		
		// pack this stuff into a GLTexture class and add it to the texture resource list
		gltex = new GLTexture(
			this,
			bufWidth, bufHeight,
			filename,
			tex,
			tex_programId_,
			tex_uModel_, tex_uView_,
			tex_uTexModel_,
			tex_uAlphaMul_, 
			tex_uBlendColor_, tex_uBlendMul_
			);
		
		textures_.add(gltex);
		
		return gltex;
	}
	
	public GLTexture LoadGrayscaleFont(String filename, boolean colorkeyWhite)
	{
		int tex;
		int bufWidth,bufHeight,pitch;
		int pixel;
		GLTexture gltex = null;
		
		// search loaded textures, see if we have already loaded this.
		for (int i=0; i < textures_.size(); i++)
		{
			if (textures_.get(i).GetTextureName() == filename)
			{
				//system_.LogMessage("GLTextureEngine::LoadTexture: Filename matched loaded texture: "+filename);
				return textures_.get(i);
			}
		}
		
		// the texture hasn't been loaded yet, so load the texture file
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (Exception e) {
			system_.LogMessage("GLTextureEngine::LoadTexture: Failed to load file: "+filename+"\n"+e.getMessage());
			return null;
		}
		
		// make a buffer for the image, the next power of two size bigger
		
		bufWidth=1;
		while (bufWidth < img.getWidth())	
			bufWidth *= 2;
		bufHeight=1;
		while (bufHeight < img.getHeight())	
			bufHeight *= 2;
		
		// non power of two images will be padded
		if (bufWidth != img.getWidth() || bufHeight != img.getHeight())
		{
			system_.LogMessage(
				"GLTextureEngine::LoadTexture: Warning: Image size is not a power of two! Image data will be padded.\n");
		}
		
		// allocate array for pixel data
		int[] pixelData = new int [bufWidth*bufHeight];
		for (int i=0; i < pixelData.length; i++)
			pixelData[i] = 0;
		
		// copy image data to padded buffer
		pitch=bufWidth;
		for (int y=0; y < img.getHeight(); y++)
		{
			for (int x=0; x < img.getWidth(); x++)
			{
				// get"RGB" returns "ARGB" which is actually "BGRA". go figure.
				// also, flip the image vertically.
				pixel = img.getRGB(x, img.getHeight()-y-1);
				
				// assume the image is a grayscale image (r=g=b),
				// copy the red component into the alpha component.
				pixel &= 0x00FFFFFF;	// get rid of the alpha component
				if (colorkeyWhite)
					pixel |= (0xFF000000 - ((pixel & 0x00FF0000) << 8));
				else
					pixel |= ((pixel & 0x00FF0000) << 8);
				
				pixelData[y*pitch+x] = pixel;
			}
		}
		
		// make a buffer for the pixel data (can't use wrap() here?)
		IntBuffer texBuf = BufferUtils.createIntBuffer(pixelData.length);
		texBuf.put(pixelData);
		texBuf.flip();
		
		// i guess we need to activate a texture unit for this?
		glActiveTexture(GL_TEXTURE0);
		
		// create an opengl texture buffer
		tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		
		// each component is 1 byte
		glPixelStorei(GL_UNPACK_ALIGNMENT,1);
		
		// upload the texture data
		glTexImage2D(
				GL_TEXTURE_2D,		// 2d texture target
				0,					// level of detail (0=normal)
				GL_RGBA,			// format to store the image in video memory
				bufWidth,
				bufHeight,
				0,					// border
				GL_BGRA,			// format of the image data supplied
				GL_UNSIGNED_BYTE,	// data is supplied as a buffer of bytes
				texBuf
				);
		
		// smooth filtering when scaled up
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		// texture repeats for coords > 1.0
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		// generate mipmaps?
		glGenerateMipmap(GL_TEXTURE_2D);
		
		// gl cleanup
		glBindTexture(GL_TEXTURE_2D,0);
		
		// pack this stuff into a GLTexture class and add it to the texture resource list
		gltex = new GLTexture(
			this,
			bufWidth, bufHeight,
			filename,
			tex,
			tex_programId_,
			tex_uModel_, tex_uView_,
			tex_uTexModel_,
			tex_uAlphaMul_, 
			tex_uBlendColor_, tex_uBlendMul_
			);
		
		textures_.add(gltex);
		
		return gltex;
	}
	
	public float[] ScaleWindowCoordinates(int x, int y)
	{
		float sx = ((float)x / displayWidth_) * (perRight - perLeft) + perLeft;
		float sy = ((float)y / displayHeight_) * (perTop - perBottom) + perBottom;
		float[] result = new float[2];
		result[0] = sx;
		result[1] = sy;
		return result;
	}
	
	
	
	//
	// protected members
	//
	
	protected IGameEngine	system_;
	
	protected int			displayWidth_;
	protected int			displayHeight_;
	
	protected int			minorVersion_;
	
	protected int			drawingState_;
	protected int			currentTexture_;
	protected float			r_,g_,b_,a_;
	
	// drawing perspective
	protected float			perLeft;
	protected float			perRight;
	protected float			perBottom;
	protected float			perTop;
	protected FloatBuffer 	matrixBuffer_;
	protected boolean		updatePerspective_;
	
	protected ArrayList<GLTexture>	textures_;
	
	// projection stuff
	protected Matrix4f		perspectiveT_;
	
	// buffers
	protected int			quadVAOId_;
	protected int			quadVBOId_;
	protected int			quadElementIndexId_;
	protected int			lineElementIndexId_;
	
	// primitive shape shader variables
	protected int			prim_programId_;
	protected int			prim_uModel_;			// mat4
	protected int			prim_uView_;			// mat4
	protected int			prim_uPerspective_;		// mat4
	protected int			prim_uColor_;			// vec3
	
	// texture shader variables
	protected int			tex_programId_;
	protected int			tex_uModel_;			// mat4
	protected int			tex_uView_;				// mat4
	protected int			tex_uPerspective_;		// mat4
	protected int			tex_uTexModel_;			// mat4 
	protected int			tex_uAlphaMul_;			// float
	protected int			tex_uBlendColor_;		// vec3
	protected int			tex_uBlendMul_;			// float
	
	
	
	//
	// protected methods
	//
	
	protected void	SetupBuffers() throws Exception
	{
		float[] quad = {		// vertex coordinates
				0.0f, 0.0f,		// bottom left		(0)
				1.0f, 0.0f,		// top left			(1)
				1.0f, 1.0f,		// top right		(2)
				0.0f, 1.0f		// bottom right		(3)
		};
		
		byte[]	quadIndices = {	// indices to vertices that make up the triangles
				0, 1, 2,		// upper triangle
				0, 2, 3			// lower triangle
		};
		
		byte[]	lineIndices = {	// indices to vertices that make a line
				0, 2			// 0,0 and 1,1
		};
		
		//int		vertexCount = quad.length/2;
		
		// must put vertex data into a packed buffer object
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(quad.length);
		vertexBuffer.put(quad);
		vertexBuffer.flip();
		
		ByteBuffer quadIndexBuffer = BufferUtils.createByteBuffer(quadIndices.length);
		quadIndexBuffer.put(quadIndices);
		quadIndexBuffer.flip();
		
		ByteBuffer lineIndexBuffer = BufferUtils.createByteBuffer(quadIndices.length);
		lineIndexBuffer.put(lineIndices);
		lineIndexBuffer.flip();
		
		// make a vertex array object
		quadVAOId_ = glGenVertexArrays();
		glBindVertexArray(quadVAOId_);
		
		// make a vertex buffer for the quad
		quadVBOId_ = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, quadVBOId_);
		
		// fill with data
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		
		// specify data format and attribute location
		// remember: for 2d we only have 2 components per vertex!
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		
		// unbind stuff
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		// make a buffer for the index/element array
		quadElementIndexId_ = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadElementIndexId_);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, quadIndexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		lineElementIndexId_ = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, lineElementIndexId_);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, lineIndexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	protected void	SetupShaders() throws Exception
	{
		String versionString = null;
		
		// this is so sketchy
		if (minorVersion_ == 3)
			versionString = "#version 330\n";
		else
			versionString = "#version " + (130+10*minorVersion_) + "\n";
		
		String TextureVertexShader =
			versionString +
			//"#version 150							\n" +
			// vertex coordinate variables
			"in vec2 aPosition;						\n" +	// vertex and texture position (attrib 0)
			// vertex transformations
			"uniform mat4	uModel;					\n" +	// model transformation matrix (rotate+scale sprite)
			"uniform mat4	uView;					\n" +	// view transformation (move into camera space)
			"uniform mat4	uPerspective;			\n" +	// perspective transformation (orthographic for 2d)
			// texture coordinate transformations
			"uniform mat4	uTexModel;				\n" +	// texture coordinate transformation
			// output
			"out vec2		vTexCoord;				\n" +	// transformed texture coordinate (varying?)
			// Program start
			"void main() {																	\n" +
				// Transform the vertex position (model,view,perspective)
			"	gl_Position = uPerspective * uView * uModel * vec4(aPosition,1.0,1.0);		\n" +
				// Transform the texture coordinates for drawing part of a texture,
				// vertex position is re-used since it is a quad from 0,0 to 1,1.
			"	vTexCoord = vec2( uTexModel * vec4(aPosition, 1.0, 1.0) );					\n" +
			"}																				\n";
		
		String TextureFragmentShader = 
			versionString +
			//"#version 150						\n" +
			// texture coordinate variables
			"in vec2			vTexCoord;		\n" +	// varying?
			"uniform sampler2D	uSampler;		\n" +
			// color + alpha blending variables
			"uniform float		uAlphaMul;		\n" +
			"uniform vec4		uBlendColor;	\n" +
			"uniform float		uBlendMul;		\n" +
			// final color output
			"out vec4			outputColor;	\n" +
			// program start
			"void main() {						\n" +
				// get the texel at the given texture coord
			"	vec4 fragColor = texture(uSampler, vTexCoord);										\n" +
			"	fragColor = vec4(mix(vec3(fragColor),vec3(uBlendColor),uBlendMul),fragColor.a);		\n" +
			"	outputColor = vec4(fragColor.rgb, fragColor.a*uAlphaMul);							\n" +
			"}																						\n";
		
		// one length arrays are goofy but support is there for more attribs if needed.
		String[] attribs = new String[1];
		attribs[0] = "aPosition";
		tex_programId_ = CreateShaderProgram(TextureVertexShader, TextureFragmentShader, attribs);
		
		// uniforms
		tex_uModel_			= glGetUniformLocation(tex_programId_, "uModel");
		tex_uView_			= glGetUniformLocation(tex_programId_, "uView");
		tex_uPerspective_	= glGetUniformLocation(tex_programId_, "uPerspective");
		tex_uTexModel_		= glGetUniformLocation(tex_programId_, "uTexModel");
		tex_uAlphaMul_		= glGetUniformLocation(tex_programId_, "uAlphaMul");
		tex_uBlendColor_	= glGetUniformLocation(tex_programId_, "uBlendColor");
		tex_uBlendMul_		= glGetUniformLocation(tex_programId_, "uBlendMul");
		
		// drawing primitives
		String PrimitiveVertexShader = 
			versionString +
			//"#version 150							\n" +
			// vertex coordinate variables
			"in vec2 aPosition;						\n" +	// vertex and texture position (attrib 0)
			// vertex transformations
			"uniform mat4	uModel;					\n" +	// model transformation matrix (rotate+scale sprite)
			"uniform mat4	uView;					\n" +	// view transformation (move into camera space)
			"uniform mat4	uPerspective;			\n" +	// perspective transformation (orthographic for 2d)
			// Program start
			"void main() {																	\n" +
				// Transform the vertex position (model,view,perspective)
			"	gl_Position = uPerspective * uView * uModel * vec4(aPosition,1.0,1.0);		\n" +
			"}																				\n";
		
		String PrimitiveFragmentShader =
			versionString +
			//"#version 150						\n" +
			// drawing color
			"uniform vec4		uColor;			\n" +
			// final color output
			"out vec4			outputColor;	\n" +
			// program start
			"void main() {						\n" +
			"	outputColor = uColor;			\n" +
			"}									\n";
		
		// use the same attribs as above
		prim_programId_ = CreateShaderProgram(PrimitiveVertexShader, PrimitiveFragmentShader, attribs);
		
		prim_uModel_		= glGetUniformLocation(prim_programId_, "uModel");
		prim_uView_			= glGetUniformLocation(prim_programId_, "uView");
		prim_uPerspective_	= glGetUniformLocation(prim_programId_, "uPerspective");
		prim_uColor_		= glGetUniformLocation(prim_programId_, "uColor");
	}
	
	protected int	CreateShaderProgram(String vertShader, String fragShader, String[] attribNames) throws Exception
	{
		int vertexShaderId;
		int fragmentShaderId;
		int programId;
		int logLength;
		String programLog;
		
		vertexShaderId = CreateShaderObject(vertShader, GL_VERTEX_SHADER);
		fragmentShaderId = CreateShaderObject(fragShader, GL_FRAGMENT_SHADER);
		
		programId = glCreateProgram();
		
		if (programId == 0)
		{
			glDeleteShader(vertexShaderId);
			glDeleteShader(fragmentShaderId);
			throw new Exception("GLTextureEngine::CreateShaderProgram: Failed to create shader program.");
		}
		
		// attach shaders to the program
		glAttachShader(programId, vertexShaderId);
		glAttachShader(programId, fragmentShaderId);
		
		// bind attrib locations BEFORE linking
		for (int i=0; i < attribNames.length; i++)
		{
			// no error checking, LIVIN ON THE EDGE
			glBindAttribLocation(programId, i, attribNames[i]);
		}
		
		glLinkProgram(programId);
		
		if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE)
		{
			logLength = glGetProgrami(programId, GL_INFO_LOG_LENGTH);
			programLog = glGetProgramInfoLog(programId, logLength);
			
			throw new Exception(
					"GLTextureEngine::CreateShaderProgram: Failed to link program: "+programLog);
		}
		
		// flag the shader objects for deletion, they will be removed when the program is deleted
		// (commented out for now until i verify that calling these now won't mess stuff up)
		//glDeleteShader(vertexShaderId);
		//glDeleteShader(fragmentShaderId);
		
		return programId;
	}
	
	protected int	CreateShaderObject(String source, int type) throws Exception
	{
		int shaderId;
		int logLength;
		String shaderLog;
		
		shaderId = glCreateShader(type);
		
		if (shaderId == 0)
		{
			if (type == GL_VERTEX_SHADER)
				throw new Exception("GLTextureEngine::CreateShaderObject: Failed to create shader (VERT).");
			else if (type == GL_FRAGMENT_SHADER)
				throw new Exception("GLTextureEngine::CreateShaderObject: Failed to create shader (FRAGMENT).");
			else
				throw new Exception("GLTextureEngine::CreateShaderObject: Failed to create shader.");
		}
		
		glShaderSource(shaderId, source);
		glCompileShader(shaderId);
		
		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE)
		{
			logLength = glGetShaderi(shaderId, GL_INFO_LOG_LENGTH);
			shaderLog = glGetShaderInfoLog(shaderId, logLength);
			
			throw new Exception(
				"GLTextureEngine::CreateShaderObject: Failed to compile shader ("+type+"): "+shaderLog);
		}
		
		return shaderId;
	}
	
	protected void	DestroyResources()
	{
		CleanupShader();
		CleanupBuffers();
		CleanupTextures();
		
		// FIXME: reset local variables
	}
	
	protected void	CleanupShader()
	{
		glUseProgram(0);
		glDeleteProgram(tex_programId_);
		glDeleteProgram(prim_programId_);
	}
	
	protected void	CleanupBuffers()
	{
		glBindVertexArray(quadVAOId_);
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glDeleteBuffers(quadVBOId_);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		glDeleteBuffers(quadElementIndexId_);
		glDeleteBuffers(lineElementIndexId_);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(quadVAOId_);
	}
	
	protected void	CleanupTextures()
	{
		// unbind any bound texture
		glBindTexture(GL_TEXTURE_2D,0);
		
		// delete them all
		for (int i=0; i < textures_.size(); i++)
		{
			glDeleteTextures(textures_.get(i).GetTextureId());
		}
		
		// GC the texture objects
		textures_.clear();
	}
}













