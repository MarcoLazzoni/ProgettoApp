package com.example.ping;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.*;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private SeekBar sBar;
	private TextView tView;
	private TextView fText;
	private Button start;
	//private Typeface font = Typeface.createFromAsset(getAssets(), "??");
	int value=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Inizialize();
		//fText.setTypeface(font);
		//tView.setTypeface(font);
		sBar.setOnSeekBarChangeListener(SeekChange);
		sBar.setProgress(15);
		tView.setText((17000 + value*100 + " HZ"));
		
	}
	OnSeekBarChangeListener SeekChange = new OnSeekBarChangeListener() {
		
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			tView.setText((17000 + value*100 + " HZ"));
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
							
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			value=progress;
			tView.setText((17000 + value*100 + " HZ"));
		
		}
		
		
	};
	
	//OnClickListener StartSending = new OnClickListener() {
		
		//@Override
		//public void onClick(View v) {
			// TODO Auto-generated method stub
			
	//	}
	//};
	

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
	private void Inizialize(){
		sBar = (SeekBar) findViewById(R.id.seekBar1);
		tView = (TextView) findViewById(R.id.textView1);
		fText = (TextView) findViewById(R.id.textView2);
		start = (Button) findViewById(R.id.button1);
	}
}
