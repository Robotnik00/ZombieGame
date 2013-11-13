package GameStates;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import InputCallbacks.KeyEventListener;
import InputCallbacks.MouseEvent;
import InputCallbacks.MouseEventListener;
import TextureEngine.ITextureEngine;

public abstract class EventListenerState implements IGameState
{
	public EventListenerState()
	{
		keyListeners = new ArrayList<KeyEventListener>();
		mouseListeners = new ArrayList<MouseEventListener>();
	}

	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) throws Exception
	{
		this.gfx = gfx;
		this.snd = snd;
		this.game = game;
	}

	@Override
	public void Quit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		DispatchKeyEvents();
		DispatchMouseEvents();
	}

	@Override
	public void Draw(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	public void addKeyEventListener(KeyEventListener keyListener) 
	{
		keyListeners.add(keyListener);
	}
	
	
	public void addMouseEventListener(MouseEventListener mouseListener)
	{
		mouseListeners.add(mouseListener);
	}
	
	protected void DispatchKeyEvents()
	{
		int[] keyEvents = game.GetKeyEvents();
		
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
		int[] mouseEvents = game.GetMouseEvents();
		
		for (int j=0; j < mouseEvents.length; j++)
		{
			MouseEvent event = new MouseEvent(Mouse.getX(), Mouse.getY(), Math.abs(mouseEvents[j]));
			
			if (mouseEvents[j] > 0)
			{
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).buttonPressed(event);
			}
			else if(mouseEvents[j] < 0)
			{
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).buttonReleased(event);
			}
		}
		MouseEvent event = new MouseEvent(Mouse.getX(), Mouse.getY(), 0);
		if(game.GetRelMouseX() != 0 || game.GetRelMouseY() != 0)
		for(int i = 0; i < mouseListeners.size(); i++)
			mouseListeners.get(i).mouseMoved(event);
	}

	protected ArrayList<KeyEventListener> 	keyListeners;
	protected ArrayList<MouseEventListener> mouseListeners;
	
	ITextureEngine gfx;
	IAudioEngine snd;
	IGameEngine game;
}
