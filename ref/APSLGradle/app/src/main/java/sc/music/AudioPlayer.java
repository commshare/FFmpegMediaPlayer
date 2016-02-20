package sc.music;

import android.os.Handler;
import android.util.Log;

/**
 * Created by Administrator on 2015/12/4.
 */
public class AudioPlayer {
    Handler mHandler;
    static AudioPlayer ap=null;
    static{
    	Log.d("SCAudio","load jni so");
        System.loadLibrary("audio");
        System.loadLibrary("meff");
    }


    public AudioPlayer(Handler h) {
        mHandler=h;
        native_init();
    }

    public static AudioPlayer getInstance(Handler h){
        if(ap==null)
            ap=new AudioPlayer(h);
        return ap;
    }
    // 支持 public native void init();
    public static native void native_init();

    //支持public native int setData(String s);
    public native int native_setDataSource(String path);

    /*下面这俩暂时不知道�?�么实现�?*/
    //public native void _setDataSource(String path,String[] keys, String[] values) throws IOException,IllegalArgumentException, IllegalStateException;
    //public native void setDataSource(FileDescriptor fileDescriptor) throws IOException,IllegalArgumentException,IllegalStateException;




    /*播放控制*/
  // 支持 public native boolean isPlaying();
  public native boolean native_isPlaying(); /*这里改为了boolean*/

 //  暂停 支持 public native void pause();
 public native int native_pause() throws IllegalStateException; /*是使用void还是int呢？MP的jni用的是void�?*/


    /*这里怎么实现�?*/
   // public native  int native_prePare() throws IOException,IllegalStateException;
   // public native  int native_prePareAsync() throws IllegalStateException;

    /*mp的start是指�?么呢�?
    *
    * Pauses playback. Call start() to resume.
    * To start the playback, start() must be called. After start() returns successfully, the MediaPlayer object is in the Started state.
    * isPlaying() can be called to test whether the MediaPlayer object is in the Started state.
    * */
    public native int native_start() throws IllegalStateException;

    /*停止不支持？*/
    public native int native_stop() throws IllegalStateException;


    /*这个貌似在mp里头木有�?*/
    public native int native_play();

    /*这个要支持啊*/
    public native int native_seekTo(int time);

    /*
    After release(), the object is no longer available.
    * */
    // 支持   public native void release();
    public native int native_release();

    /*After reset(), the object is like being just created.
    * */
    public native int native_reset();


    ///////////////////////////////////////////////
    /*状�??*/

   //支持 public native long getcur();
   public native int native_getCurrentPosition();


   // public native long gettotal();
    /*返回long*/
   public native long native_getDuration();

    /*获取文件信息*/
    public native String native_getInfo(String s);


    /*视频相关*/
//    public native void native_release_surface();
//    public native void native_set_video_surface(Surface surface);
//    public native int native_setVideoMode(int mode);
//    public native int native_setVideoSize(int w, int h);
//    public native int native_getVideoWidth();
//    public native int native_getVideoHeight();
//    public native void native_release_surface();
//    public native void native_set_video_surface(Surface surface);
    /*缩略图？*/
    //public native Bitmap getCurrentFrame();


    /*码流相关*/
    //public native void setAdaptiveStream(boolean adaptive);


    /*硬件加�??*/
    //public native int native_hw_enable(int enable);

    /*dtplayer的新增？*/
//    //opengl esv2
//    public native int native_onSurfaceCreated();
//    public native int native_onSurfaceChanged(int w, int h);
//    public native int native_onDrawFrame();
//
//    public native int native_setAudioEffect(int t);
//    /**
//     * Set whether cache the online playback file
//     * @param cache
//     * about future
//     */
//    public native void setUseCache(boolean cache);
//    public native void setCacheDirectory(String directory);
}
