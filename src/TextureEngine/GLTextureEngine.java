/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	GLTextureEngine.java
 * 		OpenGL texture loading.	
 */

// imports

package TextureEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import Engine.IGameEngine;



/*	GLTextureEngine
 * 
 * 	This class uses OpenGL to load/manage textures.
 */

public class GLTextureEngine implements ITextureEngine
{
	public GLTextureEngine(int displayWidth, int displayHeight)
	{
		system_ = null;
		displayWidth_ = displayWidth;
		displayHeight_ = displayHeight;
	}
	
	
	
	//
	// ITextureEngine interface methods
	//
	
	public void	Init(IGameEngine system) throws Exception
	{
		system_ = system;
		system_.LogMessage("GLTextureEngine::Init");
		
		// init gl resources
		SetupShaders();
		SetupBuffers();
	}
	
	public void	Quit()
	{
		system_.LogMessage("GLTextureEngine::Quit");
	}
	
	public GLTexture LoadTexture(String filename, int colorkey)
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
				system_.LogMessage("GLTextureEngine::LoadTexture: Filename matched loaded texture: "+filename);
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
			bufWidth <<= 1;
		bufHeight=1;
		while (bufHeight < img.getHeight())
			bufHeight <<= 1;
		
		// non power of two images will be padded
		if (bufWidth != img.getWidth() || bufHeight != img.getHeight())
		{
			system_.LogMessage(
				"GLTextureEngine::LoadTexture: Warning: Image size is not a power of two! Image data will be padded.\n");
		}
		
		// allocate array for pixel data
		int[] pixelData = new int [bufWidth*bufHeight];
		pitch=img.getWidth();
		
		// zero it out
		for (int i=0; i < bufWidth*bufHeight; i++)
			pixelData[i] = 0;
		
		// copy image data, checking for colorkey
		for (int y=0; y < img.getHeight(); y++)
		{
			for (int x=0; x < img.getWidth(); x++)
			{
				// get"RGB" returns "ARGB" which is actually "BGRA". great
				pixel = img.getRGB(x, y);
				
				// colorkey
				if (((pixel & 0x00FFFFFF)) == colorkey)
					pixel &= 0x00FFFFFF;	// colorkey match, set alpha to zero.
				else
					pixel |= 0xFF000000;	// visible color, set alpha to max.
					
				pixelData[y*pitch+x] = pixel;
			}
		}
		
		// make an int buffer for the pixel data, to pass to opengl
		IntBuffer texBuf = IntBuffer.wrap(pixelData);
		texBuf.flip();
		
		// create an opengl texture buffer
		tex = glGenTextures();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex);
		
		// FIXME: glPixelStorei?
		
		// upload the texture data
		glTexImage2D(
				GL_TEXTURE_2D,		// 2d texture target
				0,					// level of detail (0=normal)
				GL_RGBA,			// format to store the image in video memory
				img.getWidth(),
				img.getHeight(),
				0,					// border
				GL_BGRA,			// format of the image data supplied
				GL_UNSIGNED_INT,	// data is supplied as a buffer of bytes
				texBuf
				);
		
		glBindTexture(GL_TEXTURE_2D,0);
		
		// pack this stuff into a GLTexture class
		gltex = new GLTexture(
			bufWidth, bufHeight,
			filename,
			tex,
			quadBufferId_, quadIndexBufferId_,
 			shaderProgramId_,
 			attribPosition_,	//attribTexCoord_,
 			uModel_, uView_, uPerspective_,
 			uTexModel_,
 			uSampler_,
 			uAlphaMul_, uBlendColor_, uBlendMul_
			);
		
		textures_.add(gltex);
		
		return gltex;
	}
	
	
	
	//
	// protected members
	//
	
	protected IGameEngine	system_;
	
	protected int			displayWidth_;
	protected int			displayHeight_;
	
	protected ArrayList<GLTexture>	textures_;
	
	// buffers
	protected int			quadBufferId_;
	protected int			quadIndexBufferId_;
	
	// shader stuff
	protected int			shaderProgramId_;
	protected int			attribPosition_;
	//protected int			attribTexCoord_;
	protected int			uModel_;
	protected int			uView_;
	protected int			uPerspective_;
	protected int			uTexModel_;
	protected int			uSampler_;
	protected int			uAlphaMul_;
	protected int			uBlendColor_;
	protected int			uBlendMul_;
	
	
	
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
		
		int		vertexCount = quad.length/2;
		
		// must put vertex data into a packed buffer object
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(quad.length);
		vertexBuffer.put(quad);
		vertexBuffer.flip();
		
		ByteBuffer indexBuffer = BufferUtils.createByteBuffer(quadIndices.length);
		indexBuffer.put(quadIndices);
		indexBuffer.flip();
		
		// make a vertex buffer for the quad
		quadBufferId_ = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, quadBufferId_);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		// make a buffer for the index/element array
		quadIndexBufferId_ = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadIndexBufferId_);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	protected void	SetupShaders() throws Exception
	{
		String VertexShader = 
			"#version 150						\n" +
			// vertex coordinate variables
			"attribute vec2 aPosition;			\n" +	// vertex position
			"uniform mat3	uModel;				\n" +	// model transformation matrix (rotate+scale sprite)
			"uniform mat3	uView;				\n" +	// view transformation (move into camera space)
			"uniform mat3	uPerspective;		\n" +	// perspective transformation (orthographic for 2d)
			
			// texture coordinate variables
			//"attribute vec2 aTexCoord;			\n" +	// texture coordinate
			"uniform mat3	uTexModel;			\n" +	// texture coordinate transformation
			
			// output
			"varying vec2	vTexCoord;			\n" +	// transformed texture coordinate
			
			// Program start
			"void main() {																	\n" +
				// Transform the vertex position (model,view,perspective)
			"	vec3 fPosition = uPerspective*uView*uModel*vec3(aPosition,1.0);				\n" +
				// extend to full 4-component output
			"	gl_Position = vec4(fPosition, 1.0);											\n" +
				// Transform the texture coordinates for drawing part of a texture
			"	vTexCoord = vec2( uTexModel * vec3(aPosition, 1.0));						\n" +
			"}																				\n";
		
		String FragmentShader = 
			"#version 150						\n" +
			// texture coordinate variables
			"varying vec2		vTexCoord;		\n" +
			"uniform sampler2D	uSampler;		\n" +
			
			// color + alpha blending variables
			"uniform float	uAlphaMul;			\n" +
			"uniform vec4	uBlendColor;		\n" +
			"uniform float	uBlendMul;			\n" +
			
			// program start
			"void main() {											\n" +
				// write the texel at the given texture coord
			"	gl_FragColor = texture2D(uSampler, vTexCoord);		\n" +
			"}														\n";
		
		shaderProgramId_ = CreateShaderProgram(VertexShader, FragmentShader);
		
		// get shader variables
		attribPosition_	= glGetAttribLocation(shaderProgramId_, "aPosition");
		//attribTexCoord_	= glGetAttribLocation(shaderProgramId_, "aTexCoord");
		uModel_			= glGetUniformLocation(shaderProgramId_, "uModel");
		uView_			= glGetUniformLocation(shaderProgramId_, "uView");
		uPerspective_	= glGetUniformLocation(shaderProgramId_, "uPerspective");
		uTexModel_		= glGetUniformLocation(shaderProgramId_, "uTexModel");
		uSampler_		= glGetUniformLocation(shaderProgramId_, "uSampler");
		uAlphaMul_		= glGetUniformLocation(shaderProgramId_, "uAlphaMul");
		uBlendColor_	= glGetUniformLocation(shaderProgramId_, "uBlendColor");
		uBlendMul_		= glGetUniformLocation(shaderProgramId_, "uBlendMul");
	}
	
	protected int	CreateShaderProgram(String vertShader, String fragShader) throws Exception
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
		
		glAttachShader(programId, vertexShaderId);
		glAttachShader(programId, fragmentShaderId);
		
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
		glDeleteProgram(shaderProgramId_);
	}
	
	protected void	CleanupBuffers()
	{
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glDeleteBuffers(quadBufferId_);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		glDeleteBuffers(quadIndexBufferId_);
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













