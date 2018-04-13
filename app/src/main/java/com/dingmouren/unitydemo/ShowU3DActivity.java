package com.dingmouren.unitydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 * Created by Administrator on 2018/4/12.
 */

public class ShowU3DActivity extends AppCompatActivity{
    private String str = "{\"scene\":\"1\",\"getuser\":{\"name\":\"钉子\",\"id\":\"754\",\"password\":\"4565206\",\"url\":\"https:\\/\\/yomo-test.oss-cn-hangzhou.aliyuncs.com\\/image\\/5de4727d-7e87-415b-b062-c7a0dcb5e906\",\"info\":\"\"},\"giveuser\":{\"number\":\"1.215\",\"packetid\":3748,\"info\":\"\",\"imageurl\":\"https:\\/\\/yomo-test.oss-cn-hangzhou.aliyuncs.com\\/image\\/65987595-cbc5-45ce-825d-74b89d3adb4b\",\"name\":\"钉子\",\"id\":\"754\",\"url\":\"https:\\/\\/yomo-test.oss-cn-hangzhou.aliyuncs.com\\/image\\/5de4727d-7e87-415b-b062-c7a0dcb5e906\"},\"other\":{\"latitude\":30.180322,\"longitude\":120.151592,\"address\":\"高新商务酒店(南环路)\",\"walknumber\":\"\"}}";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        UnitySplashSDK.getInstance().onCreate(savedInstanceState, mUnityPlayer, 1);
//        mUnityPlayer.UnitySendMessage("Manager", "Manager", str);
    }

    public void ShowRedPacket() {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(ShowU3DActivity.this,"哈哈",Toast.LENGTH_SHORT).show();
           }
       });
    }
}
