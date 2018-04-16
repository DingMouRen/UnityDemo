## Unity3D与Android间的相互调用

将Unity3D集成到Android项目中有好几种方式，这里说的是将Unity3D以module的方式集成到android studio开发的项目中。
Unity3D与Android间的交互基本套路就是在android中调起Unity3D,Unity3D展示出效果后，Unity3D再调用android中协定好的方法，来实现交互。这里就按照这个套路讲一下它们之间的交互以及其中出现的坑。

![](https://github.com/DingMouRen/UnityDemo/raw/master/screenshot/u3d展示.gif)
### Android调起Unity3D（这是Android工程师的工作）

ShowU3DActivity
```
  private String str = "这里是需要传的json";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnitySplashSDK.getInstance().onCreate(savedInstanceState, mUnityPlayer, 1);
        mUnityPlayer.UnitySendMessage("Manager", "Manager", str);
    }
```
* mUnityPlayer.UnitySendMessage("Manager", "Manager", str);

这个方法就是调起Unity3D的方法,与Unity工程师协定好传递的参数,这里的str就是一段json,Unity工程师去自己解析json，获取到数据，接着就调起Unity3D的动画。注意：与Unity工程师协定好的方法，都必须声明到对应的Activity中，否则就调不起3D界面，处于一直加载状态。
UnityPlayer是一个FrameLayout,3D的展示主要是这个类，一些FrameLayout的操作都适用于UnityPlayer,也就为我们下面为它添加过渡界面提供的可行性。

* UnitySplashSDK.getInstance().onCreate(savedInstanceState, mUnityPlayer, 1);
加载3D动画时间较长，为了更好的用户体验，自己实现了UnitySplashSDK这个类，主要是用来展示一个过渡界面和加载完成时隐藏过渡界面。

### 在Unity3D中调用Android（这是Unity工程师的工作）

* Unity工程师想要调用Android项目中的方法，首先要有这个方法，所以Android工程师还要做件事情：与Unity工程师一起协定好方法的名称。

ShowU3DActivity
```
 /**
     * 展示红包，与Unity工程师协定的方法名。（Unity调用android的方法）
     */
    public void ShowRedPacket() {
        Log.e(TAG,"ShowRedPacket所在线程："+Thread.currentThread().getName());//这里的线程是Unity main线程，需要转换到android的主线程中操作自己的业务逻辑
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMyDialog();
            }
        });
    }


    /**
     * 隐藏过渡界面，与Unity工程师协定的方法名。（Unity调用android的方法）
     */
    public void hideSplash() {
        UnitySplashSDK.getInstance().onHideSplash();
    }

```

* 协定好方法名后，就是Unity工程师调用的事情。

```
AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
jo.Call("ShowRedPacket"); 
```
通过上面的方式，Unity工程就可以调用到我们在Android项目中定义好的方法。
Unity3D调用Android接口的api
|方法名|方法类型|说明
|---|---|---|
|Call|实例|调用实例方法
|Get|实例|获取实例变量（非静态）
|Set|实例|设置实例变量（非静态）
|CallStatic|静态|调用静态方法
|GetStatic|静态|获取静态变量
|SetStatic|静态|设置静态变量

上面的ShowRedPacket（）是不需要参数的，举一个需要参数的例子

android中定义的方法需要String类型的参数和一个int类型的参数:
```
public void showMsg(String name,int age){
				//android的业务逻辑
}
```
Unity工程中：
```
AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
jo.Call("showMsg","小明",18); 
```

还有一种情况就是Unity调用android的方法，需要android返回数据，也就是有返回值
android中定义一个有返回值的方法：
```
public String getName(){
	return "小明";
}
```

Unity工程中,name就是android中返回的数据
```
AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
string name = jo.Call("getName"); 
```
### 注意

* UnityPlayer.quit()调用的时候会杀死当前进程，所以我们需要为界面创建新的进程
```
        <activity android:name=".ShowU3DActivity"
            android:process=":showu3d"
            />
```
* android为Unity定义的方法，被调用的线程不是android的主线程，需要转换到Android的主线中进行操作
```
    /**
     * 展示红包，与Unity工程师协定的方法名。（Unity调用android的方法）
     */
    public void ShowRedPacket() {
        Log.e(TAG,"ShowRedPacket所在线程："+Thread.currentThread().getName());//这里的线程是Unity main线程，需要转换到android的主线程中操作自己的业务逻辑
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMyDialog();
            }
        });
    }
```

