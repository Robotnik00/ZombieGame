package InputCallbacks;
/**
*
* interface that allows a class to get mouse input
*/
public interface MouseEventListener 
{
	public void buttonPressed(MouseEvent event);
	public void buttonReleased(MouseEvent event);
	public void mouseMoved(MouseEvent event);
}
