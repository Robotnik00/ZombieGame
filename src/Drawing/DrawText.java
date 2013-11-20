package Drawing;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;
import Utility.BitmapFont;

// draws text to the screen at objects location/orientation/scale.
public class DrawText implements DrawObject
{
	public DrawText(ITextureEngine gfx, String font_name)
	{
		text = null;
		textRenderer = new BitmapFont();
		
		if(font == null)
			font = gfx.LoadTexture(font_name, 0);
		
		textRenderer.SetFont(font);
		textRenderer.SetKerning(.4f);
	}
	public void setText(String text)
	{
		this.text = text;
	}
	@Override
	public void draw(Matrix4f interpolator) 
	{
		if(text != null)
		{
			if(color != null)
			{
				textRenderer.setBlendColor(color.x, color.y, color.z, 1f);
				textRenderer.setAlpha(color.w);
			}
			interpolator.scale(new Vector3f(scalex,scaley, 0));
			textRenderer.DrawString(text, interpolator);
		}
	}
	public void setBitmapFont(BitmapFont textRenderer)
	{
		this.textRenderer = textRenderer;
	}
	public void setScale(float scalex, float scaley)
	{
		this.scalex = scalex;
		this.scaley = scaley;
	}
	public void setColor(Vector4f color)
	{
		this.color = color;
	}
	
	Vector4f color = null;
	
	float scalex = 1;
	float scaley = 1;
	
	String text;
	BitmapFont textRenderer;
	
	
	static ITexture font = null;
}
