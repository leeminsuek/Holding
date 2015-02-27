package com.br.holding5.ms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.brainyx.holding5.R;

public class AudioTool extends LinearLayout {
	private static final String TAG = "VoiceTool";
	private RecordAudio recordTask;
	private PlayAudio playTask;
	
	private Button mSendBtn;
	
	private ImageButton mRecordingStartButton = null;
	private ImageButton mRecordingStopButton = null;

	private ImageButton mPlayStartButton = null;
	private ImageButton mDeleteVoiceButton = null;
	private ImageButton mPlayStopButton = null;

	private SeekBar mPlaySeekBar = null;
	private File mRecordingFile;

	private boolean isRecording = false;
	private boolean isPlaying = false;

	private final int FREQUENCY = 11025;
	private final int CUSTOM_FREQ_SOAP = 1;
	private final int OUT_FREQUENCY = FREQUENCY * CUSTOM_FREQ_SOAP;
	private final int CHANNEL_CONTIGURATION = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

	public interface getFilePathListener {
		public void getFilePathCallback(String path);
	}
	
	public getFilePathListener mGetFilePathListener;
	
	public AudioTool(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AudioTool(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.audio_recorder_layout, this,
				false);
		addView(view);

		mRecordingStartButton = (ImageButton) view
				.findViewById(R.id.voicetool_record_start_button);
		mRecordingStopButton = (ImageButton) view
				.findViewById(R.id.voicetool_record_stop_button);
		mPlayStartButton = (ImageButton) view
				.findViewById(R.id.voicetool_play_start_button);
		mPlayStopButton = (ImageButton) view
				.findViewById(R.id.voicetool_play_stop_button);
		mSendBtn = (Button) view.findViewById(R.id.send_btn);
		
		mDeleteVoiceButton = (ImageButton) view
				.findViewById(R.id.voicetool_delete_button);
		mPlaySeekBar = (SeekBar) view.findViewById(R.id.voicetool_seekbar);
		mPlaySeekBar.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (isRecording)
					return true;
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					isPlaying = false;
				case MotionEvent.ACTION_UP:
					play();
					break;
				default:
					break;
				}
				return false;
			}
		});
		mRecordingStartButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				record();
			}
		});
		mRecordingStopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopRecording();
			}
		});
		mPlayStartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				play();

			}
		});
		mPlayStopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopPlaying();
			}
		});
		mDeleteVoiceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				delete();
			}

		});
		
		mSendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("TAG",mRecordingFile.getPath());
				if(mGetFilePathListener != null) {
					mGetFilePathListener.getFilePathCallback(mRecordingFile.getPath());
				}
			}
		});

		mRecordingStopButton.setEnabled(false);
		mPlayStartButton.setEnabled(false);
		mPlayStopButton.setEnabled(false);
		mDeleteVoiceButton.setEnabled(false);
		File path = context.getExternalFilesDir(null);
		path.mkdirs();
		try {
			mRecordingFile = File.createTempFile("recording", ".mp4", path);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't create file on SD card", e);
		}
	}
	
	public void setOnGetFilePathListener(getFilePathListener listener) {
		mGetFilePathListener = listener;
	}

	public void play() {
		mPlayStartButton.setEnabled(true);

		playTask = new PlayAudio();
		playTask.execute();
		// TODO : ∏’¿˙ µÈæÓø»
		mPlayStopButton.setEnabled(true);
		mDeleteVoiceButton.setEnabled(false);
		mPlayStopButton.setVisibility(View.VISIBLE);
		mPlayStartButton.setVisibility(View.GONE);
		mRecordingStartButton.setEnabled(false);
	}

	private void delete() {
		if (mRecordingFile != null)
			mRecordingFile.delete();
		mDeleteVoiceButton.setEnabled(false);
		mPlayStartButton.setEnabled(false);
		mRecordingStartButton.setEnabled(true);
		mPlaySeekBar.setProgress(0);
		mPlaySeekBar.setMax(0);
		mSendBtn.setVisibility(View.INVISIBLE);
	}

	public void stopPlaying() {
		isPlaying = false;
		mPlayStopButton.setEnabled(false);
		mPlayStopButton.setVisibility(View.GONE);
		mPlayStartButton.setEnabled(true);
		mPlayStartButton.setVisibility(View.VISIBLE);
		mRecordingStartButton.setEnabled(true);
	}

	public void record() {
		mRecordingStartButton.setEnabled(false);
		mRecordingStartButton.setVisibility(View.GONE);

		mRecordingStopButton.setEnabled(true);
		mRecordingStopButton.setVisibility(View.VISIBLE);

		mPlayStartButton.setEnabled(false);
		mDeleteVoiceButton.setEnabled(false);

		mPlaySeekBar.setMax(1500);
		recordTask = new RecordAudio();
		recordTask.execute();

	}

	public void stopRecording() {
		isRecording = false;

		mPlayStartButton.setEnabled(true);
		mSendBtn.setVisibility(View.VISIBLE);
		mDeleteVoiceButton.setEnabled(true);
		// mPlaySeekBar.setMax(max)
	}

	private class PlayAudio extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			isPlaying = true;

			int bufferSize = AudioTrack.getMinBufferSize(OUT_FREQUENCY,
					CHANNEL_CONTIGURATION, AUDIO_ENCODING);
			short[] audiodata = new short[bufferSize / 4];

			try {
				DataInputStream dis = new DataInputStream(
						new BufferedInputStream(new FileInputStream(
								mRecordingFile)));

				AudioTrack audioTrack = new AudioTrack(
						AudioManager.STREAM_MUSIC, OUT_FREQUENCY,
						CHANNEL_CONTIGURATION, AUDIO_ENCODING, bufferSize,
						AudioTrack.MODE_STREAM);

				audioTrack.play();
				if (mPlaySeekBar.getProgress() == mPlaySeekBar.getMax()) {
					mPlaySeekBar.post(new Runnable() {
						@Override
						public void run() {
							mPlaySeekBar.setProgress(0);
						}
					});
				} else {
					int skipCount = ((mPlaySeekBar.getProgress() - (mPlaySeekBar
							.getProgress() % audiodata.length)) * FREQUENCY / 1000);
					double time = (double) mPlaySeekBar.getProgress() / 1000.0f;
					skipCount = (int) (time * (double) FREQUENCY);
					skipCount = (skipCount - (skipCount % audiodata.length)) * 2;
					dis.skip(skipCount);
				}
				while (isPlaying && dis.available() > 0) {
					int i = 0;

					while (dis.available() > 0 && i < audiodata.length) {
						audiodata[i] = dis.readShort();
						i++;
					}
					audioTrack.write(audiodata, 0, audiodata.length);
					final int length = audiodata.length;
					mPlaySeekBar.post(new Runnable() {
						@Override
						public void run() {
							mPlaySeekBar.setProgress(mPlaySeekBar.getProgress()
									+ (int) ((float) length * 1000.0f / (float) FREQUENCY));

						}
					});
				}

				dis.close();
			} catch (Throwable t) {
				Log.e(TAG, "Playback Failed");
			}

			if (isPlaying) {
				mPlaySeekBar.setProgress(mPlaySeekBar.getMax());
				isPlaying = false;

				mPlayStartButton.post(new Runnable() {
					@Override
					public void run() {
						mPlayStartButton.setEnabled(true);
						mPlayStopButton.setEnabled(false);
						mPlayStartButton.setVisibility(View.VISIBLE);
						mPlayStopButton.setVisibility(View.GONE);
						mDeleteVoiceButton.setEnabled(true);
						mRecordingStartButton.setEnabled(true);
					}
				});
			}

			return null;
		}
	}

	// Record AsyncTask
	private class RecordAudio extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			isRecording = true;
			try {
				DataOutputStream dos = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(
								mRecordingFile, true)));

				int bufferSize = AudioRecord.getMinBufferSize(FREQUENCY,
						CHANNEL_CONTIGURATION, AUDIO_ENCODING);

				AudioRecord audioRecord = new AudioRecord(
						MediaRecorder.AudioSource.MIC, FREQUENCY,
						CHANNEL_CONTIGURATION, AUDIO_ENCODING, bufferSize);

				short[] buffer = new short[bufferSize];
				audioRecord.startRecording();

				while (isRecording) {
					int bufferReadResult = audioRecord.read(buffer, 0,
							bufferSize);
					int amplitude = 0;
					for (int i = 0; i < bufferReadResult; i++) {
						dos.writeShort(buffer[i]);
						amplitude += Math.abs((int) buffer[i]);
					}
					final int amp = amplitude;
					mPlaySeekBar.post(new Runnable() {
						@Override
						public void run() {
							mPlaySeekBar.setProgress(amp / 10000);
						}
					});
				}

				audioRecord.stop();
				dos.close();

				mPlaySeekBar.post(new Runnable() {
					@Override
					public void run() {
						int time = (int) ((float) mRecordingFile.length() * 500.0f / (float) FREQUENCY);
						mPlaySeekBar.setMax(time);
						mPlaySeekBar.setProgress(0);
						Log.d(TAG, "Recorded time : " + time);
					}
				});
			} catch (Throwable t) {
			}

			return null;
		}

		protected void onPostExecute(Void result) {
			mRecordingStartButton.setEnabled(true);
			mRecordingStartButton.setVisibility(View.VISIBLE);
			mRecordingStopButton.setEnabled(false);
			mRecordingStopButton.setVisibility(View.GONE);
			mPlayStartButton.setEnabled(true);
		}
	}
}
