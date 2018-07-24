package net.web135.smstransfer;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {

    String content = "";
    String receivetime = "";
    String sendernumber = "";
    String reciverNumber = "";
    MyAsyncTask myAsync = null;

    Runnable test = new Runnable() {
        public void run() {
            sendSMS();
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("testReceiver", "=========================>On_SMS_RECEIVED");
        content = "";
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object p : pdus) {
            byte[] pdu = (byte[]) p;
            SmsMessage message = SmsMessage.createFromPdu(pdu);
            content += message.getMessageBody();
            Log.i("content+= : ==>>   ", content.toString());
            Date date = new Date(message.getTimestampMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            receivetime = format.format(date);
//            sendernumber = message.getOriginatingAddress();
//            reciverNumber = getLocalNumber(context);
        }
        //如果允许短信上传，则上传短信内容
        if (Define.enableSmsUpload ) {
            if(content.contains("【陕西电力】")||content.contains("国网短信平台")){

                myAsync = new MyAsyncTask();
                MyAsyncTask.execute(test);

            }
        }
    }

    private boolean sendSMS() {
        try {
            String params = "{\"content\":\""
                    + content.replace("\n", "\\n").replace("\"", "\'")
                    + "\"}";

            Log.i("SendParams:=========>>>", params);
            byte[] entity = params.getBytes();
            String path = Define.uploadSmsPath;
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            conn.setRequestProperty("accept", "*/*"); //此处为暴力方法设置接受所有类型，以此来防范返回415;
//            conn.setRequestProperty("accept","application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
            conn.getOutputStream().write(entity);

            if (conn.getResponseCode() == 200) {
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
//            Log.i("asyncTask", "doInBackground");
            return "";
        }
    }

//    private String getLocalNumber(Context context){
//        TelephonyManager mTelephonyMgr;
//        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return mTelephonyMgr.getLine1Number();
//    }
}
