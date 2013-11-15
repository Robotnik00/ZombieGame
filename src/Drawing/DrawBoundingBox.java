package Drawing;

import org.lwjgl.util.vector.Matrix4f;

import GameObjects.GameObject;
import Geometry.AABB;
import TextureEngine.ITextureEngine;

public class DrawBoundingBox implements DrawObject
{
	public DrawBoundingBox(GameObject obj, ITextureEngine gfx) 
	{
		this.gfx = gfx;
		this.obj = obj;
	}
	@Override
	public void draw(Matrix4f interpolator) {
		AABB aabb = obj.getBoundingBox();
		
		gfx.DrawRectangle(aabb.bl.x, aabb.bl.y, aabb.tr.x, aabb.tr.y);
		
	}
	ITextureEngine gfx;
	GameObject obj;
}
