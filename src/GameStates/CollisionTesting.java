package GameStates;

import org.lwjgl.util.vector.Vector4f;

import Actions.MouseTracker;
import Actions.PCControl;
import AudioEngine.IAudioEngine;
import Drawing.SimpleDraw;
import Engine.IGameEngine;
import GameObjects.GameObject;
import Geometry.AABB;
import TextureEngine.ITextureEngine;



// note there are some known problems with collisions that need to be handled by CollidablePhysics.
// First off notice that if controlable object rotates into the other object the collision does not work.
// Second the objects don't respond to the collision correctly they just negate velocity. 
// Third if you move keep colliding into the object until velocity goes to zero, you get stuck.
// these are not collision detection problems however, that is working.
public class CollisionTesting extends EventListenerState implements IGameState
{

	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) throws Exception
	{
		super.Init(gfx, snd, game);
		this.gfx = gfx;
		this.snd = snd;
		this.game = game;
		buildUniverse();
		
	}

	@Override
	public void Quit() {
		
	}

	@Override
	public void Update() {
		super.Update();
		universe.update((float)1/game.GetTickFrequency());
		game.SetWindowTitle("" + game.GetFrameRate());
	}

	@Override
	public void Draw(float delta) {
		gfx.ClearScreen();
		universe.draw((float)(delta/game.GetTickFrequency()));
		if(obj1.getBoundingBox().bl != null)
		{
			gfx.DrawRectangle(
					obj1.getBoundingBox().bl.x, 
					obj1.getBoundingBox().bl.y, 
					obj1.getBoundingBox().tr.x, 
					obj1.getBoundingBox().tr.y);

			gfx.DrawRectangle(
					obj2.getBoundingBox().bl.x, 
					obj2.getBoundingBox().bl.y, 
					obj2.getBoundingBox().tr.x, 
					obj2.getBoundingBox().tr.y);
		}
		
	}
	
	private void buildUniverse()
	{
		universe = new GameObject();
		universe.scale(.2f, .2f);
		obj1 = new GameObject();
		obj1.setBoundingBox(new AABB(.5f, .5f)); 
		obj1.setDrawingInterface(new SimpleDraw(gfx.LoadTexture("image.bmp", 0))); 
		obj1.setCollidable(true); // makes it so other objects can collide with obj1. not nessessary for this example 
								  // but for more complex scenes it will allow other collidablePhysics objects to collide with obj1
		PCControl pc = new PCControl(obj1, universe, game);
		this.addMouseEventListener(pc);
		this.addKeyEventListener(pc);
		obj1.addAction(pc); // PCControl extends CollidablePhysics.
		
		obj2 = new GameObject();
		obj2.setBoundingBox(new AABB(.5f, .5f)); // set objects bounds. units in global coordinates.
		obj2.translate(3, 0);
		obj2.setDrawingInterface(new SimpleDraw(gfx.LoadTexture("image.bmp", 0)));
		obj2.setCollidable(true); // makes it so other objects can collide with obj2.
		
		universe.addChild(obj2);
		
		
		universe.addChild(obj1);
	}
	
	GameObject universe, obj1, obj1child, obj2;
	ITextureEngine gfx;
	IAudioEngine snd;
	IGameEngine game;
}
