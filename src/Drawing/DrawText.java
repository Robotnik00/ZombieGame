package Drawing;

import org.lwjgl.util.vector.Matrix4f;

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
		textRenderer.SetFont(gfx.LoadTexture(font_name, 0));
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
			textRenderer.DrawString(text, interpolator);
		}
	}
	public void setBitmapFont(BitmapFont textRenderer)
	{
		this.textRenderer = textRenderer;
	}
	
	String text;
	BitmapFont textRenderer;
}
