package sc.music;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sc.playservice.BackgroundConneciton;


/**
 * Created by Administrator on 2015/12/10.
 */
public class PlayActivity extends Activity implements OnItemClickListener,PlayService.IProgressChangedListener {

	String TAG="ListActivity";
    FileComparator compare;
    List<File> mFileList;
    PlaylistAdapter mAdapter;
    Intent mIntent;
    File mCurFile;
    File mTargetFiles;
    ListView mListView;
    TextView mCurPosView,mDurationView;
    SeekBar mSeekBar;
    AudioPlayer ap;
    int mCurIndex=0;
    PlayService mPlayService;
    BackgroundConneciton mBgConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        Window window = getWindow();
        mListView = (ListView) findViewById(R.id.list);
        mCurPosView = (TextView)findViewById(R.id.curtime);
        mDurationView = (TextView)findViewById(R.id.totaltime);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        mListView.setOnItemClickListener(this);
        
        mFileList = new ArrayList<File>();
        mAdapter=new PlaylistAdapter();
        mTargetFiles= new File("/mnt/sdcard/zhangbin/");
        mIntent= new Intent(this,PlayService.class);
        mBgConn= new BackgroundConneciton(this);
        /*
        * public boolean bindService (Intent service, ServiceConnection conn, int flags)
Added in API level 1
连接到应用程序的service，如果必要就自己创建一个service。这个方法创建了service和app之间的依赖关系。
参数conn会在service被创建的时候收到一个service对象，并被告知这个service是死亡还是重启。

The service will be considered required by the system only for as long as the calling context exists.
For example, if this Context is an Activity that is stopped, the service will not be required to continue running until the Activity is resumed.
This function will throw SecurityException if you do not have permission to bind to the given service.
Note: this method can not be called from a BroadcastReceiver component.
 A pattern you can use to communicate from a BroadcastReceiver to a Service is to call startService(Intent) with the arguments containing the command to be sent, with the service calling its stopSelf(int) method when done executing that command. See the API demo App/Service/Service Start Arguments Controller for an illustration of this. It is okay, however, to use this method from a BroadcastReceiver that has been registered with registerReceiver(BroadcastReceiver, IntentFilter), since the lifetime of this BroadcastReceiver is tied to another object (the one that registered it).

Parameters
service
Identifies the service to connect to. The Intent may specify either an explicit component name, or a logical description (action, category, etc) to match an IntentFilter published by a service.
conn
Receives information as the service is started and stopped. This must be a valid ServiceConnection object; it must not be null.
flags
Operation options for the binding. May be 0, BIND_AUTO_CREATE, BIND_DEBUG_UNBIND, BIND_NOT_FOREGROUND, BIND_ABOVE_CLIENT, BIND_ALLOW_OOM_MANAGEMENT, or BIND_WAIVE_PRIORITY.
Returns
If you have successfully bound to the service, true is returned; false is returned if the connection is not made so you will not receive the service object.
        * */
     //   bindService(mIntent,mBgConn,BIND_AUTO_CREATE);
        mListView.setAdapter(mAdapter);
        doBindService();
        
    }
    Boolean mIsBound=false;
    public void doBindService()
    {
    	Log.d("SCAudio","try to bind service");
        if (!mIsBound)
        {
            bindService(mIntent, mBgConn, Context.BIND_AUTO_CREATE);
            mIsBound = true;
            Log.d("SCAudio","bind ok !!!");
        }
    }
    public void doUnbindService()
    {
    	Log.d("SCAudio","try to unbind service");

        if (mIsBound)
        {
            unbindService(mBgConn);
            mIsBound = false;
        }
    }
    @Override
public void onDestroy() {
    super.onDestroy();

//         if (mServiceConn != null) {
//             unbindService(mServiceConn);
//            }
    doUnbindService();
}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    	Log.i(TAG,"click index["+i+"]");
        mCurIndex=i;
        playCurIndex();
    }

    @Override
    public void OnProgressChanged(int msg) {
        // TODO: Implement this method
        switch (msg)
        {
            case 0:
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mCurIndex);
                setTitle(mFileList.get(mCurIndex).getName());
                break;
            case 1:
                mCurIndex++;
                if (mCurIndex == mFileList.size())return;
                playCurIndex();
                break;
            case 2:
                mCurPosView.setText(gettime(ap.native_getDuration()));
                mSeekBar.setProgress((int) ap.native_getCurrentPosition());
                break;
        }

    }
    /*这里才开始i玩真�?*/
    public void setupPlayer(AudioPlayer ap,PlayService playService){
        this.ap=ap;
        this.mPlayService=playService;
        this.mPlayService.setIProgressChangedListener(this);
        mCurIndex=0;
        updateFiles(mTargetFiles);


    }

    class PlaylistAdapter extends BaseAdapter{
        TextView size,time;
        @Override
        public int getCount() {
            return mFileList.size();
        }

        @Override
        public Object getItem(int i) {
            return mFileList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        /*
     * Parameters:
p1 The position of the item within the adapter's data set of the item whose view we want.
想要view的数据集合中，item的位置，

p2 The old view to reuse, if possible.
Note: You should check that this view is non-null and of an appropriate type before using.
If it is not possible to convert this view to display the correct data, this method can create a new view.
Heterogeneous lists can specify their number of view types, so that this View is always of the right type
(see getViewTypeCount() and getItemViewType(int)).
要重复使用的view，如果可能的话�?�注意，这个view应该是非NULL的并且有�?个合适的类型�?
如果不能把这些view转换以显示正确的数据，这个方法可能会创建�?个新的view�?

p3 The parent that this view will eventually be attached to
view�?终要绑定的父类�??

Returns:
A View corresponding to the data at the specified position
返回�?个对应于指定位置的view�?
     * */
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            File f=mFileList.get(i);
            ClickMgr mgr=new ClickMgr(i);
            view = getLayoutInflater().inflate(R.layout.adapter,null);
            TextView tv=(TextView)view.findViewById(R.id.name);
         //   tv.setOnClickListener(mgr);
            tv.setText(f.getName());
            ((Button)view.findViewById(R.id.info)).setOnClickListener(mgr);

            return view;
        }
    }

    public class ClickMgr implements View.OnClickListener{
        File f;
        int itemIndex;
        public ClickMgr(int i) {
    	
            f=mFileList.get(i);
            itemIndex=i;
            /*构造listview的时候，getView的时候会调用多次*/
          //  mCurIndex=i;
        	Log.d(TAG,"#1#cur index ["+mCurIndex+"] and i["+itemIndex+"]");
        }

        @Override
        public void onClick(View view) {
            if(view instanceof Button){
                showinfo(f);
               // playfile(f);
            }else
            	if(view instanceof TextView){
            		Log.d(TAG,"#2#cur index ["+mCurIndex+"] and i["+itemIndex+"]");
            		playCurIndex();
            	}

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCurIndex!=0)
            setTitle(mFileList.get(mCurIndex).getName());
    }

    void playfile(File f){
        /*首先release*/
        ap.native_release();
        ap.native_setDataSource(f.getAbsolutePath());
        mDurationView.setText(gettime(ap.native_getDuration()));
        mSeekBar.setMax((int) ap.native_getDuration());
        ap.native_play();
        setTitle(f.getName());
    }
    void playfile(int index){
        File f=mFileList.get(index);
        playfile(f);

    }
    void playCurIndex(){
        File f=mFileList.get(mCurIndex);
        playfile(f);

    }
    private void showinfo(File f) {
    	String info=ap.native_getInfo(f.getAbsolutePath());
    	Log.d(TAG,"info ["+info+"]");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("文件信息").setMessage(info);
        builder.create().show();

    }

    public static String subname[]={
            ".ape",
            ".flac",
            ".wav",
            ".mp3",
            ".mp2",
            ".aac",
            ".wav",
            ".wma",
    };
    private void updateFiles(File ff)
    {
        /*目录不存�?*/
        if (!ff.exists())return;
        /*遍历目录下的�?有文件目�?*/
        for (File f:ff.listFiles())
        {
            /*如果是目录，递归遍历*/
            if (f.isDirectory())updateFiles(f);
            /*对每个后�?，分别进行全目录遍历，这个效率不高吧*/
            for (String sub:subname)
            {
                /*发现是所�?的文件后�?�?*/
                if (f.getName().endsWith(sub))
                {
                    mFileList.add(f);
                }
            }
        }
        Collections.sort(mFileList, compare);
		/*Collator cmp=Collator.getInstance(Locale.CHINESE);
		 Collections.sort(lf,cmp);*/
    }

    String dw[]={
            "B",
            "KB",
            "MB",
            "GB",
            "TB",
    };
    private String size(double a, int b)
    {
        if (a < 1024)
        {
            return String.format("%.2f%s", a, dw[b]);
        }
        b++;
        return size(a / 1024, b);
    }
    public static String gettime(long i)
    {
        long m=i / 60;
        long s=i % 60;
        return String.format("%d:%02d", m, s);
    }
	public void click(View v)
	{
		switch (v.getId())
		{
			case R.id.play:
				Log.d(TAG,"check isplaying");
				if (ap.native_isPlaying())
				{
					Log.d(TAG,"is playing,not play it !");
					
					break;
				}
				//ap.native_play();
				playCurIndex();
				break;
			case R.id.pause:
				ap.native_pause();
				break;
			case R.id.next:
				mCurIndex++;
				if (mCurIndex == mFileList.size())
					mCurIndex = 0;
				playCurIndex();
				break;
			case R.id.previous:
				mCurIndex--;
				if (mCurIndex == -1)
					mCurIndex = mFileList.size() - 1;
				playCurIndex();
		}
	}
}
