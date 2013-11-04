/*	Zombie Game
 *
 */

package AudioEngine;

// imports
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL11.*;

import AudioEngine.ALAudioEngine;



public class ALSound implements ISound
{
	// public methods
	
	public ALSound(String filename, int bufferId, ALAudioEngine alAudioEngine)
	{
		filename_ = filename;
		bufferId_ = bufferId;
		channelId_ = -1;
		al_ = alAudioEngine;
		x_ = 0.0f;
		y_ = 0.0f;
	}
	
	public int	GetBufferId()
	{
		return bufferId_;
	}
	
	public String GetFileName()
	{
		return filename_;
	}
	
	//
	// ISound member functions
	//
	
	public void	SetPos(float x, float y)
	{
		x_ = x;
		y_ = y;
	}
	
	public void	Play()
	{
		if (channelId_ != -1)
		{
			// unpause
			alSourcePlay(channelId_);
		}
		else
		{
			// play normally
			al_.PlaySound(bufferId_, x_, y_, false);
			ResetParams();
		}
	}
	
	public void	Loop()
	{
		if (channelId_ == -1)
		{
			channelId_ = al_.PlaySound(bufferId_, x_, y_, true);
			ResetParams();
		}
		else
		{
			// resume
			alSourcePlay(channelId_);
		}
	}
	
	public void Pause()
	{
		// only pause looping sounds
		if (channelId_ != -1)
		{
			alSourcePause(channelId_);
			//channelId_ = -1;
		}
	}
	
	public void Stop()
	{
		if (channelId_ != -1)
		{
			alSourceStop(channelId_);
			channelId_ = -1;
		}
	}
	
	//
	// protected members
	//
	
	protected String		filename_;
	protected int			bufferId_;
	protected int			channelId_;
	protected ALAudioEngine	al_;
	protected float			x_,y_;
	
	
	
	//
	// protected methods
	//
	
	protected void	ResetParams()
	{
		x_ = 0.0f;
		y_ = 0.0f;
	}
}
