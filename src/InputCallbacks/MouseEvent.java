package InputCallbacks;

public class MouseEvent 
{

	public static final int LEFT_BUTTON = 0;
	public static final int RIGHT_BUTTON = 1;
	public static final int MIDDLE_BUTTON = 2;
	public MouseEvent(int x, int y, int button)
	{
		this.x = x;
		this.y = y;
		this.button = button;
	}
	public int getButton()
	{
		return button;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	int button;
	int x, y;
}
