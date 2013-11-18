package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Drawing.DrawText;

public class InGameText extends Entity
{

	public InGameText(Universe universe) 
	{
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		text = new DrawText(universe.getTextureEngine(), "font1.png");
		rootNode.setDrawingInterface(text);
		
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		universe.removeEntity(this);
	}
	public void setColor(Vector4f color)
	{
		text.setColor(color);
	}
	public void scaleText(float scale)
	{
		text.setScale(scale, scale);
	}
	public void setText(String text)
	{
		this.text.setText(text);
	}
	DrawText text;
}
