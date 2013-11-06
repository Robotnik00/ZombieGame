package GameStates;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import InputCallbacks.KeyEventListener;
import InputCallbacks.MouseEvent;
import InputCallbacks.MouseEventListener;
import TextureEngine.ITextureEngine;

public class InputExample extends EventListenerState implements MouseEventListener, KeyEventListener
{

	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) throws Exception 
	{
		// TODO Auto-generated method stub
		super.Init(gfx, snd, game);
		addKeyEventListener(this);
		addMouseEventListener(this);
	}

	@Override
	public void Quit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		super.Update();
	}

	@Override
	public void Draw(float delta) {
		// TODO Auto-generated method stub
		
	}

	// just like awt
	@Override
	public void keyPressed(int event) {
		// TODO Auto-generated method stub
		System.out.printf("key pressed %d\n", event);
	}

	@Override
	public void keyReleased(int event) {
		// TODO Auto-generated method stub
		System.out.printf("key released %d\n", event);
	}

	@Override
	public void buttonPressed(MouseEvent event) {
		// TODO Auto-generated method stub
		System.out.printf("button pressed(button: %d X: %d Y: %d)\n", event.getButton(), event.getX(), event.getY());
	}

	@Override
	public void buttonReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		System.out.printf("button released(button: %d X: %d Y: %d)\n", event.getButton(), event.getX(), event.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		System.out.printf("mouse moved %d %d\n", event.getX(), event.getY());
	}
	
}
