/*	ZombieGame
 * 
 *	BitmapFont.java
 *		Takes a bitmap font and draws strings.
 */

package Utility;

// imports
import TextureEngine.ITexture;

import org.lwjgl.util.vector.*;



/**
 * BitmapFont takes a square image containing character graphics for the first 256 characters (16x16 characters).
 * A string can then be supplied and this class will draw the string using the bitmap font.
 */

public class BitmapFont
{
	public BitmapFont()
	{
		font_ 		= null;
		tabSize_	= 4;
		charWidth_ 	= 1.0f/16.0f;
		charHeight_ = 1.0f/16.0f;
		x_			= 0.0f;
		y_			= 0.0f;
		xs_			= 1.0f;
		ys_			= 1.0f;
		
		mmat_ = new Matrix4f();
		mmat_.setIdentity();
		vmat_ = new Matrix4f();
		vmat_.setIdentity();
	}
	
	public void	SetFont(ITexture font)
		{	font_ = font;	}
	
	public void SetPosition(float x, float y)
		{	x_=x; y_=y;	}
	
	public void SetScale(float x, float y)
		{	xs_=x; ys_=y;	}
	
	public void	SetTabSize(int size)
		{	tabSize_ = size;	}
	
	public void SetKerning(float kerning)
		{	kerning_ = kerning;	}
	
	public void setBlendColor(float r, float g, float b, float blend)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.blend = blend;
	}
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	
	public float	GetKerning()
		{	return kerning_; }
	
	
	public void	DrawString(String s)
	{
		float xpos=0;
		float dx=0,dy=0;
		int charCode;
		int lineChar=0;
		
		Matrix4f finalMat_ = new Matrix4f(); 
		
		mmat_.setIdentity();
		mmat_.scale(new Vector3f(xs_,ys_,1.0f));
		//mmat_.translate(new Vector2f(x_,y_));
		
		vmat_.setIdentity();
		vmat_.translate(new Vector2f(x_,y_));
		
		for (int i=0; i < s.length(); i++)
		{
			charCode = (int)s.charAt(i) & 0xFF;
			
			// check special characters and translate
			if (charCode == '\n')
			{
				mmat_.translate(new Vector2f(-xpos,-1.0f));
				xpos = 0.0f;
				lineChar = 0;
				continue;
			}
			
			if (charCode == '\t')
			{
				int offset = (tabSize_ - (lineChar % tabSize_));
				
				lineChar += offset;
				xpos += (float)offset - kerning_*(float)offset;
						
				mmat_.translate(
					new Vector2f(
						(float)offset - kerning_*(float)offset,
						0.0f)
					);
				
				continue;
			}
		
			// calculate the location of that character in the bitmap font
			dx = charWidth_ * (charCode % 16);
			dy = 1.0f - (charHeight_ * (charCode / 16)) - charHeight_;
			font_.SetSrcRect(dx, dy, dx+charWidth_, dy+charHeight_);
			
			// only use model matrix to move
			Matrix4f.mul(vmat_, mmat_, finalMat_);
			font_.Draw(finalMat_);
			
			// move to the next drawing position
			mmat_.translate(new Vector2f(1.0f - kerning_,0.0f));
			xpos += (1.0f - kerning_);
			
			lineChar++;
		}
		
		// reset drawing params
		x_			= 0.0f;
		y_			= 0.0f;
		xs_			= 1.0f;
		ys_			= 1.0f;
		//kerning_	= 0.0f;
	}
	
	// added for drawing text to scene graph
	public void	DrawString(String s, Matrix4f interpolator)
	{
		float xpos=0;
		float dx=0,dy=0;
		int charCode;
		int lineChar=0;
		
		mmat_ = interpolator;
		
		for (int i=0; i < s.length(); i++)
		{
			charCode = (int)s.charAt(i) & 0xFF;
			
			// check special characters and translate
			if (charCode == '\n')
			{
				mmat_.translate(new Vector2f(-xpos,-1.0f));
				xpos = 0.0f;
				lineChar = 0;
				continue;
			}
			
			if (charCode == '\t')
			{
				int offset = (tabSize_ - (lineChar % tabSize_));
				
				lineChar += offset;
				xpos += (float)offset - kerning_*(float)offset;
						
				mmat_.translate(
					new Vector2f(
						(float)offset - kerning_*(float)offset,
						0.0f)
					);
				
				continue;
			}
		
			// calculate the location of that character in the bitmap font
			dx = charWidth_ * (charCode % 16);
			dy = 1.0f - (charHeight_ * (charCode / 16)) - charHeight_;
			font_.SetSrcRect(dx, dy, dx+charWidth_, dy+charHeight_);
			
			// only use model matrix to move
			font_.SetBlendColor(r, g, b, blend);
			font_.SetAlpha(alpha);
			font_.Draw(mmat_);
			// move to the next drawing position
			mmat_.translate(new Vector2f(1.0f - kerning_,0.0f));
			xpos += (1.0f - kerning_);
			
			lineChar++;
		}
		
		// reset drawing params
		x_			= 0.0f;
		y_			= 0.0f;
		xs_			= 1.0f;
		ys_			= 1.0f;
		//kerning_	= 0.0f;
	}
	
	
	//
	// protected values
	//
	
	protected float r, g ,b, blend;
	protected float alpha = 1;
	
	protected float			x_,y_,xs_,ys_;
	protected Matrix4f		mmat_;
	protected Matrix4f		vmat_;
	
	protected ITexture		font_;
	protected float			charWidth_;
	protected float			charHeight_;
	
	protected int			tabSize_;
	protected float			kerning_;
}
