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
	
	public void	Init(IGameEngine system)
	{
		system_ = system;
		system_.LogMessage("GLTextureEngine::Init");
		
		// init gl resources
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
	
	
	
	//
	// protected methods
	//
	
	protected void	SetupShaders()
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
	}
}













