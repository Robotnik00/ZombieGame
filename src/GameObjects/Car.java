package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Drawing.DrawBoundingBox;
import Drawing.SimpleDraw;
import Geometry.AABB;

public class Car extends Entity
{

	public Car(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) {
		drawcar = new SimpleDraw(universe.getTextureEngine().LoadTexture("gfx/Environment/Car1.png", 0));
		rootNode.scale(4, 4);
		rootNode.setBoundingBox(new AABB(.5f,.4f));
		rootNode.setProxemityBounds(new AABB(.5f, .4f));
		rootNode.setCollidable(true);
		
		GameObject carNode = new GameObject();
		carNode.setLocalX(-.05f);
		carNode.setLocalY(-.05f);
		carNode.scale(1, .9f);
		rootNode.addChild(carNode);
		carNode.setDrawingInterface(drawcar);
		GameObject tire1 = new GameObject();
		GameObject tire2 = new GameObject();
		GameObject tire3 = new GameObject();
		GameObject tire4 = new GameObject();
		
		SimpleDraw drawwindow = new SimpleDraw(universe.getTextureEngine().LoadTexture("gfx/Environment/Glass.png", 0));
		rootNode.setDrawingInterface(drawwindow);
		drawwindow.setScale(.4f, .25f);
		
		SimpleDraw drawTire = new SimpleDraw(universe.getTextureEngine().LoadTexture("gfx/Environment/Tire.png", 0));
		drawTire.setScale(.4f, .3f);
		
		tire1.setDrawingInterface(drawTire);
		tire2.setDrawingInterface(drawTire);
		tire3.setDrawingInterface(drawTire);
		tire4.setDrawingInterface(drawTire);

		tire1.setLocalX(-.1f);
		tire1.setLocalY(-.14f);
		tire2.setLocalX(.17f);
		tire2.setLocalY(-.14f);
		tire3.setLocalX(.17f);
		tire3.setLocalY(.2f);
		tire4.setLocalX(-.1f);
		tire4.setLocalY(.2f);
		
		
		carNode.addChild(tire1);
		carNode.addChild(tire2);
		carNode.addChild(tire3);
		carNode.addChild(tire4);
		
		
		
	}
	
	public void setColor(Vector4f color)
	{
		
		drawcar.setColor(color);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		universe.removeEntity(this);
	}
	SimpleDraw drawcar;
}
