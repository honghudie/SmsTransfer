package net.web135.eagleeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SimpleReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent eagleEyeService = new Intent(context, EyeMain.class);
		context.startService(eagleEyeService);
		Intent numberService = new Intent(context,NumberService.class);
		context.startService(numberService);
		Intent uploadService = new Intent(context,UpLoadService.class);
		context.startService(uploadService);
		Log.i("testReceiver", "On_BOOT_COMPLETED & On_USER_PRESENT");
	}

}
