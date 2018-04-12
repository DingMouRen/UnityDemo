package cn.com.yomo.unity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.unity3d.player.UnityPlayer;

import java.io.InputStream;


/**
 * 修改unity启动画面
 * Created by xiongyu on 2017/8/30.
 */

public class UnitySplashSDK {
    // 这是启动画面的图片，这里可以使任意一个View，可以根据自己的需要去自定义
    private ImageView bgView = null;
    private UnityPlayer mUnityPlayer = null;
    int type;

    private static UnitySplashSDK mInstance;
    public static UnitySplashSDK getInstance() {
        if (null == mInstance) {
            synchronized (UnitySplashSDK.class) {
                if (null == mInstance) {
                    mInstance = new UnitySplashSDK();
                }
            }
        }
        return mInstance;
    }
    // 在unity的Activity的onCreate中调用
    public void onCreate(Bundle savedInstanceState, UnityPlayer mUnityPlayer, int type) {
        this.mUnityPlayer = mUnityPlayer;
        this.type=type;
        onShowSplash();
    }

    public void onShowSplash() {
        if (bgView != null)
            return;
        try {
            // 设置启动内容，这里的内容可以自定义区修改
            bgView = new ImageView(UnityPlayer.currentActivity);
            if(type==0){
                bgView.setImageBitmap(readBitMap(R.mipmap.ar_start));
            }else if(type==3){
                bgView.setImageBitmap(readBitMap(R.mipmap.dingge_start));
            }else {
//                bgView.setImageResource(R.mipmap.pet_start);
                bgView.setImageBitmap(readBitMap(R.mipmap.pet_start));
            }
            bgView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Resources r = mUnityPlayer.currentActivity.getResources();
            WindowManager w = mUnityPlayer.currentActivity.getWindowManager();
            Display d = w.getDefaultDisplay();
            DisplayMetrics metrics2 = new DisplayMetrics();
            d.getMetrics(metrics2);
            // since SDK_INT = 1;
            int widthPixels = metrics2.widthPixels;
            int heightPixels = metrics2.heightPixels;
            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
                try {
                    widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                    heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
                } catch (Exception ignored) {
                }
            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= 17)
                try {
                    Point realSize = new Point();
                    Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                    widthPixels = realSize.x;
                    heightPixels = realSize.y;
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            mUnityPlayer.addView(bgView,widthPixels,heightPixels);
//            mUnityPlayer.addView(bgView, r.getDisplayMetrics().widthPixels, r.getDisplayMetrics().heightPixels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**

     * 以最省内存的方式读取本地资源的图片
     * @param resId

     * @return

     */
    public  Bitmap readBitMap( int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = bgView.getContext().getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    // 在unity的Activity中暴露方法给unity，让unity在准备好内容后调用隐藏启动画面
    public void onHideSplash() {
        try {
            if (bgView == null){
                return;
            }
            UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    mUnityPlayer.removeView(bgView);
                   if(bgView != null && bgView.getDrawable() != null){
                        Bitmap oldBitmap = ((BitmapDrawable) bgView.getDrawable()).getBitmap();
                        bgView.setImageDrawable(null);
                        if(oldBitmap != null){
                            oldBitmap.recycle();
                            oldBitmap = null;
                        }
                    }
                    bgView = null;
                    System.gc();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
