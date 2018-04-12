package cn.com.yomo.unity;

import android.content.Context;
import android.widget.Toast;

import com.unity3d.player.GoogleVrApi;
import com.unity3d.player.UnityPlayer;

import java.io.Serializable;


/**
 * Created by xiongyu on 2017/8/7.
 */

public class MyUnityPlayer extends UnityPlayer implements Serializable {
    public MyUnityPlayer(Context context) {
        super(context);
    }

    @Override
    protected void kill() {
    }
}
