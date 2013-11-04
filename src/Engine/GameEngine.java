/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	GameEngine.java
 * 		Main game controller object implementation.	
 */
package Engine;

import java.util.*;
import java.text.*;

import org.lwjgl.Sys;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;

import AudioEngine.IAudioEngine;
import AudioEngine.NullAudioEngine;
import AudioEngine.ALAudioEngine;
import GameStates.IGameState;
import GameStates.InputExample;
import GameStates.StartGame;
import GameStates.TestState;
import GameStates.CollisionTesting;
import InputCallbacks.KeyEventListener;
import InputCallbacks.MouseEvent;
import InputCallbacks.MouseEventListener;
import TextureEngine.GLTextureEngine;
import TextureEngine.ITextureEngine;




/*	GameEngine
 * 
 * 	The actual game controller object implementation. This class creates and initializes resource systems (texture and
 * 	audio engines), the display itself, and manages gamestates.
 */


public class GameEngine implements IGameEngine
{
	//
	// constants
	//
	
	/**
	 * This is how many game logic updates occur per second, independent of the frame rate.
	 * Try to pick values that divide into 1000 nicely.
	 */
	final int		TICKS_PER_SECOND	= 25;
	
	/** 
	 * Time window for each game logic update to occur.
	 * After the game update, the engine will display as many frames as it can within the remaining time.
	 */
	final long		TICK_LENGTH			= 1000 / TICKS_PER_SECOND;
	
	/**
	 * If a game logic update takes longer than TICK_LENGTH, no frames will be drawn for that tick.
	 * This value forces a frame update if too many logic updates go without frame updates.
	 */
	final int		MAX_FRAMESKIP		= 5;
	
	// Game title / title bar text
	final String	GAME_TITLE			= "Zombie Game";
	
	// display size
	final int		SCREEN_WIDTH		= 640;
	final int		SCREEN_HEIGHT		= 480;
	
	
	
	//
	// Static methods
	//
	
	/**
	 * Program entry point.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args)
	{
		try
		{
			GameEngine game = new GameEngine(args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	
	
	//
	// public methods
	//
	
	/**
	 * "Actual" main function. Initializes various "engines" and sets up the initial gamestate.
	 * @param args Command line arguments.
	 * @throws Exception
	 */
	public GameEngine(String[] args) throws Exception
	{
		// init members
		textureEngine_		= null;
		audioEngine_		= null;
		currentState_		= null;
		runGame_			= true;
		framesPerSecond_	= 0;
		lastMousePosition_	= new float[2];
		
		// copy arguments
		arguments_ = args;

		keyListeners = new ArrayList<KeyEventListener>();
		mouseListeners = new ArrayList<MouseEventListener>();
		
		// setup window w/LWJGL, init opengl
		SetupDisplay(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		// init audio
		SetupAudio();
		
		// other stuff
		Mouse.create();
		Keyboard.create();
		
		// start initial gamestate
		ChangeGameState(new TestState());
		//ChangeGameState(new StartGame());
		//ChangeGameState(new InputExample());
		//ChangeGameState(new CollisionTesting());
		
		// run the game loop
		GameLoop();
		
		// clean up resources (tex, audio, etc)
		ShutdownAudio();
		ShutdownDisplay();
	}
	
	
	
	//
	// IGameEngine methods
	//
	
	public void	ChangeGameState(IGameState state) throws Exception
	{
		LogMessage("GameEngine::ChangeGameState: Switching contexts...");
		
		// stop the old state
		if (currentState_ != null)
			currentState_.Quit();
		
		// initialize and store the new one
		state.Init(textureEngine_, audioEngine_, this);
		currentState_ = state;
		
		LogMessage("GameEngine::ChangeGameState: Done.");
	}
	
	public void	EndGameLoop()
	{
		LogMessage("EndGameLoop");
		runGame_ = false;
	}
	
	// information
	
	public int	GetTickFrequency()
	{
		return TICKS_PER_SECOND;
	}
	
	public int	GetFrameRate()
	{
		return framesPerSecond_;
	}
	
	public int	CheckArguments(String arg)
	{
		for (int i=0; i < arguments_.length; i++)
		{
			if (arguments_[i] == arg)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public String[]	GetArguments()
	{
		return arguments_;
	}
	
	public void	LogMessage(String message)
	{
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		System.out.println("[" + sf.format(date) + "] " + message);
	}
		
	public void	SetWindowTitle(String str)
	{
		Display.setTitle(str);
	}
	
	// time
	
	public long	GetTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	// input
	
	@Override
	public float GetMouseX()
	{
		//PumpMouseMotionEvents();
		return lastMousePosition_[0];
	}

	@Override
	public float GetMouseY() 
	{
		//PumpMouseMotionEvents();
		return lastMousePosition_[1];
	}

	@Override
	public int[] GetMouseEvents() 
	{
		return lastMouseEvents_;
	}

	@Override
	public int[] GetKeyEvents() 
	{
		return lastKeyEvents_;
	}
	
	@Override
	public void addKeyEventListener(KeyEventListener keyListener) 
	{
		keyListeners.add(keyListener);
	}
	
	@Override
	public void addMouseEventListener(MouseEventListener mouseListener)
	{
		mouseListeners.add(mouseListener);
	}
	
	
	
	//
	// protected members
	//
	
	protected ITextureEngine	textureEngine_;
	protected IAudioEngine		audioEngine_;
	
	protected IGameState		currentState_;
	protected boolean			runGame_=true;
	
	protected int				framesPerSecond_;
	
	protected String[]			arguments_;
	
	protected float[]			lastMousePosition_;
	protected int[]				lastMouseEvents_;
	protected int[]				lastKeyEvents_;
	
	protected ArrayList<KeyEventListener> 	keyListeners;
	protected ArrayList<MouseEventListener> mouseListeners;
	
	
	
	//
	// protected methods
	//
	
	/**
	 * Runs the current gamestate, calling update and draw, and measures game performance.
	 */
	protected void	GameLoop()
	{
		int updates = 0;	// each time update() is called, used for frameskip
		int frames = 0;		// each time draw() is called
		int numUpdates=0;	// incremented with update(), frame rate is polled when it hits TICKS_PER_SECOND
		float frameDelta = 0.0f;
		long next_tick = GetTime();
		IGameState thisState = null;
	
		while (runGame_)// && !Display.isCloseRequested())
		{
			//LogMessage("Loop");
			
			// Is the display requesting to be closed?
			if (Display.isCloseRequested() == true)
			{
				LogMessage("GameEngine::GameLoop: Display close requested, ending game loop.");
				EndGameLoop();
				return;
			}
			
			// Keep track of the current state at the start of the tick, to check for state changes later.
			// ChangeGameState does not call the new state Update, 
			// so if the current state changes during this tick, a frame should not be drawn.
			thisState = currentState_;
			
			updates = 0;
			
			while (GetTime() > next_tick && updates < MAX_FRAMESKIP)
			{
				// update keyboard and mouse events
				PumpKeyboardEvents();
				PumpMouseButtonEvents();
				PumpMouseMotionEvents();
				
				// notify event listeners
				DispatchKeyEvents();
				DispatchMouseEvents();
				
				// update
				currentState_.Update();
				updates++;
				//LogMessage("Update");
				
				// update frame rate counter
				numUpdates++;
				if (numUpdates == TICKS_PER_SECOND)
				{
					framesPerSecond_ = frames;
					frames = 0;
					numUpdates = 0;
				}
				
				// when is the next update due? if the last update took too long, the loop will iterate again
				next_tick += TICK_LENGTH;
			}
			
			// skip frame updates this tick if the state was changed;
			// the new state has not been updated, and the old state will not be able to draw at this point
			if (thisState != currentState_)
				continue;
			
			// update this again, mouse position can be updated between frames
			PumpMouseMotionEvents();
			
			// express the current time as a fraction of a tick,
			// use this value to interpolate graphics between frames
			frameDelta = (float)(GetTime() + TICK_LENGTH - next_tick) / (float)TICK_LENGTH;
			currentState_.Draw(frameDelta);
			frames++;
			//LogMessage("Draw");
			
			// swap buffers, update the screen, update LWJGL
			Display.update();
			//Display.sync(60);
		}
	}
	
	/**
	 * Reads keyboard input, called once per frame.
	 */
	protected void	PumpKeyboardEvents()
	{
		ArrayList<Integer> events = new ArrayList<Integer>();
        
        while (Keyboard.next())
        {
	        if (Keyboard.getEventKeyState())        // pressed
	        {
	            events.add(Keyboard.getEventKey());
	        }
	        else // released
	        {
	        	events.add(-Keyboard.getEventKey());
	        }
        }
        
        // don't create an empty array
        if (events.size() == 0)
        {
        	events.add(Keyboard.KEY_NONE);
        }
        
        // pack button events into array
        lastKeyEvents_ = new int [events.size()];
        //LogMessage("PumpKeyboardEvents: " + events.size() + " events.");
        
        for (int i=0; i < events.size(); i++)
        {
        	lastKeyEvents_[i] = events.get(i);
        }
	}

	
	/**
	 * Reads mouse button input, called once per frame.
	 */
	protected void	PumpMouseButtonEvents()
	{
		// can't use native data types on containers, that would be too simple.
		ArrayList<Integer> events = new ArrayList<Integer>();
		
		while (Mouse.next())
		{
			// pressed = positive button id
			// released = negative button id
			// add 1 because 0 is neither +/-
			if (Mouse.getEventButtonState())        // pressed
			{        
				events.add(Mouse.getEventButton()+1);
			}
			else // released
			{
				events.add(-(Mouse.getEventButton()+1));
			}
		}
		
		// pack button events into array
		lastMouseEvents_ = new int [events.size()];
		
		for (int i=0; i < events.size(); i++)
		{
			lastMouseEvents_[i] = events.get(i);
		}
	}
	
	protected void	DispatchKeyEvents()
	{
		int[] keyEvents = GetKeyEvents();
		
		for(int i = 0; i < keyListeners.size(); i++)
		{
			for (int j=0; j < keyEvents.length; j++)
			{
				if (keyEvents[j] >= 0)	// pressed
				{
					keyListeners.get(i).keyPressed(keyEvents[j]);
				}
				else // released
				{
					keyListeners.get(i).keyReleased(-keyEvents[j]);
				}
			}
		}
	}
	
	protected void	DispatchMouseEvents()
	{
		int[] mouseEvents = GetMouseEvents();
		
		for (int j=0; j < mouseEvents.length; j++)
		{
			MouseEvent event = new MouseEvent(Mouse.getX(), Mouse.getY(), Math.abs(mouseEvents[j]));
			
			if (mouseEvents[j] >= 0)
			{
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).buttonPressed(event);
			}
			else
			{
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).buttonReleased(event);
			}
		}
	}
	
	/**
	 * Reads mouse position, called before update and draw frames.
	 * The mouse position begins as a coordinate in window space (in pixels),
	 * but is scaled into the ITextureEngine's current drawing perspective.
	 */
	protected void	PumpMouseMotionEvents()
	{
		lastMousePosition_ = textureEngine_.ScaleWindowCoordinates(Mouse.getX(), Mouse.getY());
	}
	
	/** 
	 * Initialize LWJGL and create a display w/ opengl 3.2 context
	 * @param width Width of window in pixels.
	 * @param height Height of window in pixels.
	 * @throws Exception
	 */
	protected void	SetupDisplay(int width, int height) throws Exception
	{
		LogMessage("SetupDisplay");
		
		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs contextAttribs = new ContextAttribs(3,2)
		//	.withForwardCompatible(true)
			.withProfileCore(true);
		
		// try
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.setTitle(GAME_TITLE);
		Display.create(pixelFormat, contextAttribs);
		
		LogMessage("SetupDisplay: Testing OpenGL, got version: " + 
				glGetInteger(GL_MAJOR_VERSION) + "." + glGetInteger(GL_MINOR_VERSION));
		
		// opengl texture engine
		textureEngine_ = new GLTextureEngine();
		textureEngine_.Init(this,width,height);
		
		textureEngine_.SetClearColor(0.0f, 0.5f, 1.0f);
		textureEngine_.SetDrawingArea(0, 0, width, height);
		
		textureEngine_.SetOrthoPerspective(
				-1.0f, 1.0f, 
				-((float)height/(float)width), ((float)height/(float)width)
				);
		
		textureEngine_.ClearScreen();
	}
	
	/**
	 * Shutdown the ITextureEngine, frees graphics resources, and shutdown LWJGL display.
	 */
	protected void	ShutdownDisplay()
	{
		LogMessage("ShutdownDisplay");
		textureEngine_.Quit();
		Display.destroy();
	}
	
	/**
	 * Initialize the IAudioEngine, either with OpenAL or Null on failure.
	 */
	protected void	SetupAudio() throws Exception
	{
		try 
		{
			InitOpenALAudioEngine();
		}
		catch (Exception e)
		{
			LogMessage(e.getMessage());
			LogMessage("Falling back to NullAudioEngine.");
			InitNullAudioEngine();
		}
	}
	
	/**
	 * Initialize the ALAudioEngine.
	 * @throws Exception
	 */
	protected void 	InitOpenALAudioEngine() throws Exception
	{
		//throw new Exception("InitOpenALAudioEngine: Not implemented yet!", null);
		audioEngine_ = new ALAudioEngine();
		audioEngine_.Init(this);
	}
	
	/**
	 * Initialize the NullAudioEngine.
	 */
	protected void 	InitNullAudioEngine()
	{
		audioEngine_ = new NullAudioEngine();
		try 
		{
			audioEngine_.Init(this);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Shutdown audio engine and resources.
	 */
	protected void	ShutdownAudio()
	{
		LogMessage("ShutdownAudio");
		audioEngine_.Quit();
	}
}
