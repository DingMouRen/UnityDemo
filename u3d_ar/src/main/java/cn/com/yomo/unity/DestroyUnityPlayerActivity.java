package cn.com.yomo.unity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2018/4/12.
 */

public class DestroyUnityPlayerActivity extends AppCompatActivity {

    private MyUnityPlayer myUnityPlayer;

    public static void getInstance(Context context,MyUnityPlayer unityPlayer){
        Intent intent = new Intent(context,DestroyUnityPlayerActivity.class);
        intent.putExtra("unityplayer",unityPlayer);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destroy_unity);
        if (getIntent() != null) myUnityPlayer = (MyUnityPlayer) getIntent().getSerializableExtra("unityplayer");
        myUnityPlayer.quit();
        myUnityPlayer.removeOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                finish();
            }
        });
    }
}
