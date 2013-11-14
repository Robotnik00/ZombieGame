package GameObjects;

import AudioEngine.IAudioEngine;
import Drawing.CameraView;
import Engine.IGameEngine;
import GameStates.EventListenerState;
import GameStates.IGameState;
import TextureEngine.ITextureEngine;
/**
 * This encapsalates everything that happens in the game. 
 * Entities are created and added to this object
 * 
 *
 */
public abstract class Universe
{
	public Universe(ITextureEngine gfx, IAudioEngine snd, IGameEngine game, EventListenerState state)
	{
		this.gfx = gfx;
		this.snd = snd;
		this.game = game;
		this.state = state;
		
		universeNode   = new GameObject();
		backgroundNode = new GameObject();
		universeNode.addChild(backgroundNode);
		HUD = new GameObject();
		buildUniverse();
	}
	
	/**
	 * override this class to tell the engine how to draw the background and universe
	 * and what to put in the level.
	 * 
	 */
	protected abstract void buildUniverse();
	
	/**
	 *  focuses the view on the entity
	 * @param entity
	 */
	public void setFocus(Entity entity)
	{
		universeNode.setDrawingInterface(new CameraView(universeNode, entity.getRootNode(), game));
	}
	/**
	 * scales the entire universe
	 * @param scale
	 */
	public void scaleUniverse(float scale)
	{
		universeNode.scale(scale, scale);
	}
	
	
	/**
	 *  Dynamically add an Entity while game is running. 
	 *  Example: spawning a zombie
	 * @param entity
	 */
	public void addEntity(Entity entity)
	{
		backgroundNode.addChild(entity.getRootNode());
	}
	public void removeEntity(Entity entity)
	{
		backgroundNode.removeChild(entity.getRootNode());
	}
	/**
	 * update everything
	 * 
	 * @param delta
	 */
	public void draw(float delta)
	{
		gfx.ClearScreen();
		deltaT = (float)delta/*/game.GetTickFrequency()*/;
		universeNode.draw(deltaT);
		HUD.draw(deltaT);
	}
	/**
	 *  update everything
	 */
	public void update()
	{
		deltaT = (float)1/game.GetTickFrequency();
		universeNode.update(deltaT);
		HUD.update(deltaT);
	}
	
	public IGameEngine getGameEngine()
	{
		return game;
	}
	public IAudioEngine getAudioEngine()
	{
		return snd;
	}
	public ITextureEngine getTextureEngine()
	{
		return gfx;
	}
	public GameObject getHandle()
	{
		return universeNode;
	}
	public EventListenerState getState()
	{
		return state;
	}
	public void destroy()
	{
		this.universeNode = null;
		System.gc();
	}
	float deltaT = 1;
	GameObject universeNode;
	GameObject backgroundNode;
	GameObject HUD;
	
	ITextureEngine gfx;
	IAudioEngine snd;
	IGameEngine game;
	EventListenerState state;
}
