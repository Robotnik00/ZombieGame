/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	GLTextureEngine.java
 * 		OpenGL texture loading.	
 */

// imports
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;



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
	}
	
	public void	Quit()
	{
		system_.LogMessage("GLTextureEngine::Quit");
	}
	
	public GLTexture LoadTexture(String filename, int colorkey)
	{
		return null;
	}
	
	
	
	//
	// protected members
	//
	
	protected IGameEngine	system_;
	
	protected int			displayWidth_;
	protected int			displayHeight_;
	
	protected int			shaderProgramId_;
	
	
	
	//
	// protected methods
	//
	
	protected void	SetupShaders() throws Exception
	{
		String VertexShader = 
			
			// vertex coordinate variables
			"attribute vec2 aVertPos;			\n" +	// vertex position
			"uniform mat4	uModel;				\n" +	// model transformation matrix (rotate+scale sprite)
			"uniform mat4	uView;				\n" +	// view transformation (move into camera space)
			"uniform mat4	uPerspective;		\n" +	// perspective transformation (orthographic for 2d)
			
			// texture coordinate variables
			"attribute vec2 aTexCoord;			\n" +	// texture coordinate
			"uniform mat4	uTexModel;			\n" +	// texture coordinate transformation
			
			// output
			"varying vec2	vTexCoord;			\n" +	// transformed texture coordinate
			
			// Program start
			"void main() {																\n" +
			
				// Transform the vertex position (model,view,perspective), assign to output
			"	gl_Position = uPerspective * uView * uModel * vec4(aVertPos, 0.0, 1.0);	\n" +
				
				// Transform the texture coordinates for drawing part of a texture
			"	vTexCoord = vec2( uTexModel * vec4(aTexCoord, 0.0, 1.0) );				\n" +
			
			"}																			\n";
		
		String FragmentShader = 
			// texture coordinate variables
			"varying vec2		vTexCoord;		\n" +
			"uniform sampler2D	uTexSampler;	\n" +
			
			// color + alpha blending variables
			"uniform float	uAlphaMul;			\n" +
			"uniform vec4	uBlendColor;		\n" +
			"uniform float	uBlendMul;			\n" +
			
			// program start
			"void main() {											\n" +
			
				// write the texel at the given texture coord
			"	gl_FragColor = texture2D(uTexSampler, vTexCoord);	\n" +
			
			"}														\n";
		
		//
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
			throw new Exception(
				"GLTextureEngine::CreateShaderObject: Failed to create shader ("+type+").");
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
}













