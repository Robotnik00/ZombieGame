/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	ALAudioEngine.java
 * 		OpenAL audio engine.
 */

package AudioEngine;

// imports
import java.util.*;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL.*;
import org.lwjgl.util.WaveData;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL11.*;

import Engine.IGameEngine;



/**	ALAudioEngine
 * 
 * 	Implements IAudioEngine via OpenAL
 */

public class ALAudioEngine implements IAudioEngine
{
	// 
	// constant values
	//
	
	/** 
	 * Allocate this many sound channels total.
	 * Try to make this a power of two, and there is no guarantee of there being > 16 channels available. 
	 */
	public final int	NUM_CHANNELS		= 16;
	
	/** 
	 * NOT IMPLEMENTED!
	 * Any particular non-looping sound effect can only occupy this many sound channels at any one time,
	 * to reserve channels for other sounds.
	 */
	public final int	MAX_SFX_SOURCES		= 4;
	
	/** 
	 * Any particular looping sound can only occupy this many sound channels at any one time.
	 */
	public final int	MAX_LOOP_SOURCES	= 1;
	
	
	
	//
	// public methods
	//
	
	public ALAudioEngine()
	{
		masterGain_ = 1.0f;
		listenX_ = 0.0f;
		listenY_ = 0.0f;
		
		soundBuffers_ = new ArrayList<ALSound>();
		soundSources_ = new int[NUM_CHANNELS];
	}
	
	/**
	 * Plays a sound effect.
	 * When playing a sound, the engine will try to find an empty sound channel to play the sound on. If there are no
	 * open sound channels, the sound will not play. 
	 * @param bufferId
	 * @param x
	 * @param y
	 * @param loop
	 * @return The sound channel that the sound was played on, or -1 if the sound was unable to play.
	 * This is more useful for looping sounds.
	 */
	public int	PlaySound(int bufferId, float x, float y, boolean loop)
	{
		int channel, source;
		
		// find a sound channel
		channel = FindOpenChannel(bufferId, loop);
		
		if (channel == -1)
		{
			//system_.LogMessage("ALAudioEngine: Couldn't find a sound channel.");
			return -1;
		}
		
		source = soundSources_[channel];
		
		// set source properties per sound
		alSourcei(source,	AL_BUFFER, bufferId);
		alSourcef(source,	AL_GAIN, masterGain_);
		alSourcei(source,	AL_LOOPING, (loop) ? AL_TRUE : AL_FALSE);
		alSource3f(source,	AL_POSITION, x, y, 0.0f);
		
		// disturb those around you!
		alSourcePlay(source);
		
		return source;
	}
	
	
	
	//
	// IAudioEngine methods
	//
	
	public void Init(IGameEngine system) throws Exception
	{
		int sourceid=-1;
		
		system.LogMessage("ALAudioEngine::Init");
		
		system_ = system;
		
		// init openal
		AL.create();
		
		// Sometimes openAL is failing *without* an exception: (logs don't report a fallback to NullAudioEngine)
		// AL lib: (EE) MMDevApiOpenPlayback: Device init failed: 0x80004005
		// It's hard to find info on this bug, mostly people saying to update LWJGL. This is really weird, 
		// it doesn't happen all the time. Hopefully this will capture the error.
		int err = alGetError();
		if (err != AL_NO_ERROR)
		{
			throw new Exception("ALAudioEngine::Init: OpenAL failed to initialize: error code = "+err);
		}
		
		// allocate sound channels
		for (int i=0; i < soundSources_.length; i++)
		{
			// only set id when channel is successfully initialized
			soundSources_[i] = -1;
			
			// create a sound channel
			sourceid = alGenSources();
			
			if (alGetError() != AL_NO_ERROR)
			{
				throw new Exception(
					"ALAudioEngine::Init: Failed to allocate sound channel ("+i+").\n");
			}
			
			// setup initial sound source stuff
			alSourcef(sourceid, AL_PITCH, 1.0f);		// normal pitch
			alSourcef(sourceid, AL_GAIN, masterGain_);	// initialize to master volume
			alSourcei(sourceid, AL_LOOPING, AL_FALSE);	// don't loop
			alSource3f(sourceid, AL_POSITION, listenX_, listenY_, 0.0f);	// default listener position
			alSource3f(sourceid, AL_VELOCITY, 0.0f, 0.0f, 0.0f);			// no moving sounds here
			
			// save the id
			soundSources_[i] = sourceid;
		}
		
		// disable sound volume from dropping off over distance
		alDistanceModel(AL_NONE);
		
		// initial listener stuff
		alListener3f(AL_POSITION, listenX_, listenY_, 0.0f);
		alListener3f(AL_VELOCITY, 0.0f, 0.0f, 0.0f);
		
		// listener orientation
		FloatBuffer ori = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(
				new float[] {0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f} ).rewind();
		alListener(AL_ORIENTATION, ori);
	}
	
	public void Quit()
	{
		system_.LogMessage("ALAudioEngine::Quit");
		
		// stop all playing sounds
		for (int i=0; i < soundSources_.length && soundSources_[i]>-1; i++)
		{
			alSourceStop(soundSources_[i]);
		}
		
		// free sound resources
		for (int i=0; i < soundBuffers_.size(); i++)
		{
			alDeleteBuffers(soundBuffers_.get(i).GetBufferId());
		}
		soundBuffers_.clear();
		
		// delete sound channels
		for (int i=0; i < soundSources_.length && soundSources_[i]>-1; i++)
		{
			alDeleteSources(soundSources_[i]);
			soundSources_[i] = -1;
		}
		
		// shutdown openal
		AL.destroy();
	}
	
	public String GetAudioInfo()
	{
		return "OpenAL 1.0";
	}
	
	public void SetVolume(float volume)
	{
		masterGain_ = volume;
		
		// adjust the volume in all the sound channels
		for (int i=0; i < soundSources_.length; i++)
		{
			alSourcef(soundSources_[i], AL_GAIN, volume);
		}
	}
	
	public float GetVolume()
	{
		return masterGain_;
	}
	
	public void SetListenerPosition(float x, float y)
	{
		listenX_ = x;
		listenY_ = y;
		alListener3f(AL_POSITION, listenX_, listenY_, 0.0f);
	}
	
	public ISound LoadSound(String filename) throws Exception
	{
		int bufferId;
		
		// if we have already loaded this file, return the sound resource for it
		for (int i=0; i < soundBuffers_.size(); i++)
		{
			if (soundBuffers_.get(i).GetFileName().compareTo(filename) == 0)
			{
				return soundBuffers_.get(i);
			}
		}
		
		// load wave file
		WaveData wav;
		
		// we can't just give wavedata a path string, because then it only looks for files
		// starting in the classpath. what. the. fuck.
		try {
			wav = WaveData.create(new BufferedInputStream(new FileInputStream(filename)));
		} catch (Exception e) {
			throw new Exception("ALAudioEngine::LoadSound: Couldn't load file "+filename+": "+e.getMessage());
		}
		
		// create al sound buffer
		bufferId = alGenBuffers();
		
		if (alGetError() != AL_NO_ERROR)
		{
			throw new Exception(
					"ALAudioEngine::LoadSound: Couldn't create sound buffer.");
		}
		
		alBufferData(bufferId, wav.format, wav.data, wav.samplerate);
		wav.dispose();	// don't need the sound data here anymore
		
		// one last error check
		if (alGetError() != AL_NO_ERROR)
		{
			throw new Exception(
					"ALAudioEngine::LoadSound: Couldn't fill sound buffer data.");
		}

		// pack into a sound resource
		ALSound als = new ALSound(filename, bufferId, this);
		
		// add to the resource list
		soundBuffers_.add(als);
		
		return als;
	}
	
	
	
	//
	// protected members
	//
	
	protected IGameEngine			system_;
	
	// sound resources
	protected ArrayList<ALSound>	soundBuffers_;
	protected int[]					soundSources_;
	
	protected float					masterGain_;	// volume
	protected float					listenX_, listenY_;
	
	
	
	//
	// protected methods
	//
	
	/**
	 * Attempts to find a sound channel which is not playing anything.
	 * @return index into soundSources_
	 */
	protected int	FindOpenChannel(int bufferId, boolean loop)
	{
		int count = 0;
		int maxCount = (loop) ? MAX_LOOP_SOURCES : MAX_SFX_SOURCES;
		int source = -1;
		int state=0;
		
		// just return the first open channel
		for (int i=0; i < soundSources_.length; i++)
		{
			state = alGetSourcei(soundSources_[i], AL_SOURCE_STATE);
			
			if (state != AL_PLAYING && state != AL_PAUSED)
			{
				return i;
			}
		}
		
		return -1;
		
		// Go through all channels to see if the sound is playing at all, but remember the first open channel we find.
		// FIXME: polling each channel like this is probably detrimental to performance somehow.
		/*
		for (int i=0; i < soundSources_.length && count < maxCount; i++)
		{
			// if this channel is playing anything
			if (alGetSourcei(soundSources_[i], AL_PLAYING) == AL_TRUE)
			{
				// is it playing this sound already?
				if (alGetSourcei(soundSources_[i], AL_BUFFER) == bufferId)
				{
					// yes, skip this channel and count this occurrence
					count++;
				}
			}
			else
			{
				// this channel is not playing anything, remember it
				source = i;
			}
		}
		
		
		// are we not already playing this sound too many times?
		if (count < maxCount)
			return source;
		
		return -1;
		*/
	}
}
