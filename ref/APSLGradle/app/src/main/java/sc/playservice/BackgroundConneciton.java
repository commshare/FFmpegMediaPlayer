package sc.playservice;

import sc.music.AudioPlayer;
import sc.music.PlayService;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import sc.music.PlayActivity;

/**
 * Created by Administrator on 2015/12/4.
 * Activity 中需要实�? ServiceConnection 接口�? onServiceConnected() �? onServiceDisconnected() 两个方法，这两个方法也是 Service �? Activity 相互通信的关�?
 *
 * 实现�?个接�?
 */
public class BackgroundConneciton implements ServiceConnection {
	PlayService.PlayBinder mPlayBinder;
	PlayActivity mActivity;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
    	/*从这个回调中拿到binder是非常重要的*/
    	mPlayBinder= (PlayService.PlayBinder)service;
    	/*当PlayService创建ok，就会回调这个类，传递来这些，然后这个类会告知Activity，是不是搞的有点麻烦啊？*/
    	this.mActivity.setupPlayer(getAudioPlayer(mPlayBinder),getPlayService(mPlayBinder));

    }
    AudioPlayer getAudioPlayer(PlayService.PlayBinder mPlayBinder){
    	return mPlayBinder.getAudioPlayer();
    }
    PlayService getPlayService(PlayService.PlayBinder mPlayBinder){
    	return mPlayBinder.getPlayerService();
    }

    public BackgroundConneciton(PlayActivity listActivity) {
		
		// TODO Auto-generated constructor stub
    	this.mActivity=listActivity;
	}

	@Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
