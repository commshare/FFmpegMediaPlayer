package sc.playservice;

import android.os.Binder;

/**
 * Created by Administrator on 2015/12/4.
 */
/*
* 如果你的service仅被自己的应用使用并且不�?跨进程工作，那么你可以实现你自己的Binder类使得你的客户端能直接使用service的公�?接口方法�?
* 这只在客户端和service位于同一应用和同�?进程中时才能工作，其实大多数都是这种情况．例如，在一个音乐应用需要把它的activity绑定到它自己的播放音乐的后台service时，这种方式就会很好地工作．
* */
public class BackgroundBinder extends Binder {
    /*
    * 创建�?个Binder实例，提供以下三种功能之�?�?

Binder包含�?些可供客户端调用的公�?方法�?

返回当前的Service实例，它具有�?些客户端可以调用的公�?方法�?

或�?�，返回另一个类的实例，这个类具有客户端可调用的公开方法并托管于service�?
    * */
}
