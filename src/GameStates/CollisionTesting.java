package GameStates;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Actions.CollidablePhysics;
import Actions.MouseTracker;
import Actions.PCControl;
import AudioEngine.IAudioEngine;
import Drawing.SimpleDraw;
import Engine.IGameEngine;
import GameObjects.GameObject;
import Geometry.AABB;
import TextureEngine.ITextureEngine;


// current scene contains a controllable object, a static object, and a object that response to collisions.
// note for more accurate collisions, higher update freq.
public class CollisionTesting extends EventListenerState
{

	public CollisionTesting()
	{
		super();
	}
	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) throws Exception
	{
		super.Init(gfx, snd, game);
		buildUniverse();
		
	}

	@Override
	public void Quit() {
		
	}

	@Override
	public void Update() 
	{
		super.Update();
		
		
		universe.update((float)1/game.GetTickFrequency());
		game.SetWindowTitle("" + game.GetFrameRate());
	}

	@Override
	public void Draw(float delta) {
		gfx.ClearScreen();
		universe.draw((float)(delta/game.GetTickFrequency()));
	}
	
	private void buildUniverse()
	{
		universe = new GameObject();
		universe.scale(.3f, .3f);
		obj1 = new GameObject();
		obj1.setBoundingBox(new AABB(.5f, .5f)); 
		obj1.setDrawingInterface(new SimpleDraw(gfx.LoadTexture("image.bmp", 0))); 
		obj1.setCollidable(true); // makes it so other objects can collide with obj1. not nessessary for this example 
								  // but for more complex scenes it will allow other collidablePhysics objects to collide with obj1
		PCControl pc = new PCControl(obj1, universe, game);
		this.addMouseEventListener(pc);
		this.addKeyEventListener(pc);
		obj1.addAction(pc); // PCControl extends CollidablePhysics.
		obj1.setMass(.1f);
		
		obj2 = new GameObject();
		obj2.setBoundingBox(new AABB(.5f, .5f)); // set objects bounds. units in global coordinates.
		obj2.translate(2, 0);
		obj2.setDrawingInterface(new SimpleDraw(gfx.LoadTexture("image.bmp", 0)));
		obj2.setCollidable(true); // makes it so other objects can collide with obj2.
		obj2.addAction(new CollidablePhysics(obj2, universe, game));
		obj2.setMass(.1f);
		universe.addChild(obj2);
		
		GameObject staticObject = new GameObject();
		staticObject.setCollidable(true);
		staticObject.translate(.5f, .5f);
		staticObject.setBoundingBox(new AABB(.5f,.5f));
		staticObject.setDrawingInterface(new SimpleDraw(gfx.LoadTexture("image.bmp", 0)));
		universe.addChild(staticObject);
		
		universe.addChild(obj1);
	}
	
	GameObject universe, obj1, obj1child, obj2;
}
