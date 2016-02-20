package sc.music;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


/**
 * Created by Administrator on 2015/12/10.
 */
public class PlayService extends Service {
	String TAG="PlayService";
    AudioPlayer audioPlayer;
    IProgressChangedListener progressListener;
    IBinder playServiceBinder;
    MsgHander msgHandler;

    /*搞了这么�?个进度改变接�?*/
    public interface IProgressChangedListener{
        public void OnProgressChanged(int state);
    }

    public void setIProgressChangedListener(IProgressChangedListener listener){
        progressListener=listener;
    }
    class MsgHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    public void onCreate() {
    	 Log.i(TAG, "Service onCreate--->");  
        super.onCreate();
        playServiceBinder=new PlayBinder();
        /*消息处理也是放在service中实现*/
        msgHandler=new MsgHander();
        audioPlayer=AudioPlayer.getInstance(msgHandler);

    }

    @Override
    public IBinder onBind(Intent intent) {
    	 Log.i(TAG, "Service onBind--->");  
        return playServiceBinder;
    }

    /*从binder返回service和ap实例*/
   public  class PlayBinder extends Binder {
       public PlayService getPlayerService(){
           return PlayService.this;
       }
       public AudioPlayer getAudioPlayer(){
           return audioPlayer;
       }
    }

@Override
public void onDestroy() {
	 Log.i(TAG, "Service onDestroy--->");  

	// TODO Auto-generated method stub
	super.onDestroy();
}

@Override
public void onStart(Intent intent, int startId) {
	 Log.i(TAG, "Service onStart--->");  

	// TODO Auto-generated method stub
	super.onStart(intent, startId);
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
	 Log.i(TAG, "Service onStartCommand--->");  

	// TODO Auto-generated method stub
	return super.onStartCommand(intent, flags, startId);
}

@Override
public boolean onUnbind(Intent intent) {
	 Log.i(TAG, "Service onUnbind--->");  

	// TODO Auto-generated method stub
	return super.onUnbind(intent);
}
   
}
