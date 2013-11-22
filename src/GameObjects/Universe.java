package GameObjects;

import Actions.Camera;
import Actions.ObjectFollower;
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
		
		
		scaleNode = new GameObject();
		universeNode   = new GameObject();
		scaleNode.addChild(universeNode);
		cam = new Camera(universeNode, scaleNode, game);
		universeNode.addAction(cam);
		backgroundNode = new GameObject();
		universeNode.addChild(backgroundNode);
		midgroundNode = new GameObject();
		universeNode.addChild(midgroundNode);
		
		foregroundNode = new GameObject();
		universeNode.addChild(foregroundNode);
		
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
		cam.setFocus(entity.getRootNode());
	}
	/**
	 * scales the entire universe
	 * @param scale
	 */
	public void scaleUniverse(float scale)
	{
		scaleNode.scale(scale, scale);
	}
	
	
	/**
	 *  Dynamically add an Entity while game is running. 
	 *  Example: spawning a zombie
	 * @param entity
	 */
	public void addEntity(Entity entity)
	{
		midgroundNode.addChild(entity.getRootNode());
	}
	public void removeEntity(Entity entity)
	{
		midgroundNode.removeChild(entity.getRootNode());
	}
	/**
	 * update everything
	 * 
	 * @param delta
	 */
	public void draw(float delta)
	{
		gfx.ClearScreen();
		deltaT = (float)delta;
		scaleNode.draw(deltaT);
		HUD.draw(deltaT);
	}
	/**
	 *  update everything
	 */
	public void update()
	{
		
		deltaT = (float)1/game.GetTickFrequency();
		scaleNode.update(deltaT);
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
		return midgroundNode;
	}
	public GameObject getScalingNode()
	{
		return scaleNode;
	}
	public GameObject getBackgroundNode()
	{
		return backgroundNode;
	}
	public GameObject getForgroundNode()
	{
		return foregroundNode;
	}
	public GameObject getHUD()
	{
		return HUD;
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
	Camera cam;
	
	float deltaT = 1;
	GameObject scaleNode;
	GameObject universeNode;
	GameObject backgroundNode;
	GameObject foregroundNode;
	GameObject midgroundNode;
	GameObject HUD;
	
	ITextureEngine gfx;
	IAudioEngine snd;
	IGameEngine game;
	EventListenerState state;
}
