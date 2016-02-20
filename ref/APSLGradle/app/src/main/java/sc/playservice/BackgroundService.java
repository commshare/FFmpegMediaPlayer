package sc.playservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrator on 2015/12/4.
 */
public class BackgroundService extends Service {

    // Binder given to clients
    private IBinder mBinder =null;

    public BackgroundService() {
    }

    // 当服务开启时，调用的方法
    @Override
    public void onCreate() {
        super.onCreate();
        mBinder= new BackgroundBinder();
    }

    // 当服务销毁时，调用的方法
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当每次使�? startService 调用服务时，调用该方法（但一个服务同时只会存在一个实例）
     * 通过 Intent 传�?�的数据，也会在这里得到
     * 注意�? onCreate() 方法的区别：
     *      onCreate() �?个应用开启服务时，该方法只会调用�?�?
     *      onStartCommand() 只要使用 startService() 调用此服务，该方法就会执行一�?
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /* 绑定服务时，执行该方�?
        * 在回调方法onBind()中返回这个Binder的实例．
        *
        *
         * bindService() 时，调用的是这个方法，�?�非 onStartCommnad() 方法

        * */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
