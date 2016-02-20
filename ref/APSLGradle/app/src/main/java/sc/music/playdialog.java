package sc.music;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;




public class playdialog extends Activity
{
	SeekBar seek;
	AudioPlayer a;
	TextView curtime,totaltime;
	Handler h=new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			if(msg.what==2){
				curtime.setText(PlayActivity.gettime(a.native_getCurrentPosition()));
				seek.setProgress((int)a.native_getCurrentPosition());
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control);
		seek=(SeekBar) findViewById(R.id.seek);
		curtime=(TextView)findViewById(R.id.curtime);
		totaltime=(TextView)findViewById(R.id.totaltime);
		Intent i=getIntent();
		if(i.getAction().equals(Intent.ACTION_VIEW)){
			Uri path=i.getData();
			a=new AudioPlayer(h);
			a.native_setDataSource(path.getPath());
			totaltime.setText(PlayActivity.gettime(a.native_getDuration()));
			seek.setMax((int)a.native_getDuration());
			a.native_play();
		}
	}
	public void click(View v){
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode==4){
			a.native_release();

		}
		return super.onKeyDown(keyCode, event);
	}
	
}
