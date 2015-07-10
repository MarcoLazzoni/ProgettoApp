package com.example.ping;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SignalGenerator extends Service {

	private final String TAG = "SignalGenerator";
	// Signal Settings
	int centralFrequency;
	float signalDuration;
	float pauseDuration;
	float duration;
	private static final int SAMPLE_RATE = 44100;

	public static boolean isRunning;

	int numSamples;
	double sample[];
	byte generatedSnd[];		

	AudioTrack audioTrack;
	
	AudioManager audio;


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle extras = intent.getExtras();
		signalDuration = Float.parseFloat(extras.getString("SignalDuration"));
		pauseDuration = Float.parseFloat(extras.getString("PauseDuration"));
		centralFrequency = Integer.parseInt(extras.getString("CentralFrequency"));

		duration = signalDuration + pauseDuration;

		numSamples = Math.round(duration * SAMPLE_RATE);

		sample = new double[numSamples];
		generatedSnd = new byte[2 * numSamples];		

		new Thread(new Runnable() { 
			public void run(){
				while (isRunning){
					audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
							SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
							AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
							AudioTrack.MODE_STATIC);
					genTone(); 
					playSound();
					
					Log.i(TAG, "playSound()");
					try {
						Thread.sleep((long) (duration*1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					audioTrack.flush();
					audioTrack.stop();
					audioTrack.release();
					}
			}
		}).start();
		return Service.START_STICKY;
	}




	void genTone(){
		WindowFunction wf = new WindowFunction();
		wf.setWindowType("BLACKMAN");
		double[] windowSamples = wf.generate(numSamples);
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = windowSamples[i] * Math.sin(centralFrequency * 2 * Math.PI * i / (SAMPLE_RATE));
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int idx = 0;
		for (final double dVal : sample) {
			// scale to maximum amplitude
			final short val = (short) ((dVal * 32767)); //Map the double [-1, 1] to short [-32767, 32767]
			
			// in 16 bit wav PCM, first byte is the low order byte
			
			//Logical AND last 8bit with 0000000011111111 and byte-cast
			generatedSnd[idx++] = (byte) ( val & 0x00ff);  
			
			//Logical AND last 8bit with 1111111100000000 
			//8-bit right-shift and byte-cast
			generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}


	void playSound(){
		audioTrack.write(generatedSnd, 0, generatedSnd.length);
		audioTrack.play();
	}

}
