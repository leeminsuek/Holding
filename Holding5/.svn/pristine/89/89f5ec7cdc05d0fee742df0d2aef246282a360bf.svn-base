package com.br.holding5.ms;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brainyx.holding5.R;

public class AudioView extends RelativeLayout {
	private MediaRecorder myRecorder;
	private MediaPlayer myPlayer;
	private String outputFile = null;
	private ImageButton startBtn;
	private ImageButton stopBtn;
	private ImageButton playBtn;
	private ImageButton stopPlayBtn;
//	private TextView text;

	private PopupWindow mPopupWindow;
	private getFilePathListener mGetFilePathListener;
	public interface getFilePathListener {
		public void getFilePathCallback(String path);
	}
	
	
	public AudioView(Context context) {
		super(context);


		View.inflate(context, R.layout.audio_layout, this);

		//		AudioTool audioTool = (AudioTool) this.findViewById(R.id.AudioTool1);
		//		audioTool.setOnGetFilePathListener(new getFilePathListener() {
		//			@Override
		//			public void getFilePathCallback(String path) {
		//				if(mGetFilePathListener != null) {
		//					mPopupWindow.dismiss();
		//					mGetFilePathListener.getFilePathCallback(path);
		//				}
		//			}
		//		});

		// store it to sd card
		outputFile = Environment.getExternalStorageDirectory().
				getAbsolutePath() + "/javacodegeeksRecording.mp4";

		myRecorder = new MediaRecorder();
		myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myRecorder.setOutputFile(outputFile);

		startBtn = (ImageButton)findViewById(R.id.start);
		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start(v);
			}
		});

		stopBtn = (ImageButton)findViewById(R.id.stop);
		stopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stop(v);
			}
		});

		playBtn = (ImageButton)findViewById(R.id.play);
		playBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				play(v);   
			}
		});

		stopPlayBtn = (ImageButton)findViewById(R.id.stopPlay);
		stopPlayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				stopPlay(v);
				mPopupWindow.dismiss();
				mGetFilePathListener.getFilePathCallback(outputFile);
			}
		});

	}

	public void setOnGetFilePathListener( getFilePathListener listener ) {
		mGetFilePathListener = listener;
	}

	public void setPopupWindow( PopupWindow popup ) {
		mPopupWindow = popup;
	}

	public void start(View view){
		try {
			myRecorder.prepare();
			myRecorder.start();
		} catch (IllegalStateException e) {
			// start:it is called before prepare()
			// prepare: it is called after start() or before setOutputFormat()
			e.printStackTrace();
		} catch (IOException e) {
			// prepare() fails
			e.printStackTrace();
		}

		startBtn.setEnabled(false);
		stopBtn.setEnabled(true);

		Toast.makeText(getContext(), "녹음시작",
				Toast.LENGTH_SHORT).show();
	}

	public void stop(View view){
		try {
			myRecorder.stop();
			myRecorder.release();
			myRecorder  = null;
			stopBtn.setEnabled(false);
			playBtn.setEnabled(true);

			Toast.makeText(getContext(), "녹음중지",
					Toast.LENGTH_SHORT).show();
		} catch (IllegalStateException e) {
			//  it is called before start()
			e.printStackTrace();
		} catch (RuntimeException e) {
			// no valid audio/video data has been received
			e.printStackTrace();
		}
	}

	public void play(View view) {
		try{
			myPlayer = new MediaPlayer();
			myPlayer.setDataSource(outputFile);
			myPlayer.prepare();
			myPlayer.start();

			playBtn.setEnabled(false);
			stopPlayBtn.setEnabled(true);

			Toast.makeText(getContext(), "재생",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
