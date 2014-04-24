package com.is3av.text2speech;

import java.util.Locale;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener{
	private EditText textEntered;
	private TextToSpeech tts; 
	private Button speak;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tts = new TextToSpeech(this,this);
		speak = (Button) findViewById(R.id.speakbutton);
		textEntered = (EditText) findViewById(R.id.entertext);
		speak.setOnClickListener(listener);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			speakOut();
		}
	} ;
	@Override
	public void onDestroy() {
		//shutting down TTS
		if(tts!=null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if(status==TextToSpeech.SUCCESS) {
			int result=tts.setLanguage(Locale.US);
			if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS","This language is not supported");
			}
			else {
				speak.setEnabled(true);
				speakOut();
			}
		}
			else {
				Log.e("TTS","Initialization failed");
			}
		
	}

	private void speakOut() {
		String text = textEntered.getText().toString();
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
}
