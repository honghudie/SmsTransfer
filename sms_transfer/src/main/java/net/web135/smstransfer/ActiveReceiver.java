package net.web135.smstransfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ActiveReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("BootReceiver.onReceive,action="+intent.getAction());
        // 打开主界面
        Intent intentMain = new Intent(context, MainActivity.class);
        // 从 Activity 以外的地方，调用 startActivity 必须使用这个 flag 来生成新的任务栈
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentMain);
    }

}
