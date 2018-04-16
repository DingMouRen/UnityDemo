package cn.com.yomo.unity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.unity3d.player.UnityPlayer;

/**
 * Created by Administrator on 2018/4/16.
 * 加载Unity3D时会有一个加载的过程，我们添加一个过渡界面来提高用户体验
 */

public class UnitySplashSDK {

    private ImageView mBgPic ;//过渡界面的背景图片
    private UnityPlayer mUnityPlayer;
    private int mType = 0;//类型，根据业务来展示不同的背景图片
    private static UnitySplashSDK sInstance;//单例对象

    /**
     * 私有构造函数
     */
    private UnitySplashSDK(){}

    /**
     * 单例方式获取单例实例
     * @return
     */
    public static UnitySplashSDK getInstance(){
        if (null == sInstance){
            synchronized (UnitySplashSDK.class){
                if (null == sInstance) sInstance = new UnitySplashSDK();
            }
        }
        return sInstance;
    }

    /**
     * 初始化
     * @param savedInstanceState
     * @param unityPlayer
     * @param type
     */
    public void onCreate(Bundle savedInstanceState,UnityPlayer unityPlayer,int type){
        this.mUnityPlayer = unityPlayer;
        this.mType = type;
        mBgPic = new ImageView(UnityPlayer.currentActivity);//过渡界面的背景图片
        onShowSplash();
    }

    /**
     * 展示过渡界面
     */
    public void onShowSplash(){
        switch (mType){
            case 1://展示ar红包业务的背景图片
                showArRedBg();
                break;
            default:
                break;
        }
    }

    /**
     * 展示ar红包业务的背景图片
     */
    private void showArRedBg() {
        mBgPic.setImageResource(R.mipmap.ar_start);
        int bgPicWidth = getScreenWidth();
        int bgPicHeight = getScreenHeight();
        mUnityPlayer.addView(mBgPic,bgPicWidth,bgPicHeight);
    }

    public void onHideSplash(){
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != mBgPic.getDrawable()){
                    Bitmap bitmap = ((BitmapDrawable)mBgPic.getDrawable()).getBitmap();
                    mBgPic.setImageDrawable(null);
                    if (null != bitmap){
                        bitmap.recycle();
                        bitmap = null;
                    }
                }
                mBgPic = null;
                System.gc();
            }
        });
    }

    /**
     * 获取屏幕高度
     * @return
     */
    private int getScreenHeight() {
        WindowManager windowManager = UnityPlayer.currentActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        WindowManager windowManager = UnityPlayer.currentActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }
}
