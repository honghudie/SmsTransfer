package net.web135.smstransfer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText smsInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnSave).setOnClickListener(this);
        smsInfo = (EditText)findViewById(R.id.editTxtSmsInfo);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
//                startService(service);
//                startService(numberService);
//                Intent uploadService = new Intent(this,UpLoadService.class);
//                startService(uploadService);
                String sms = smsInfo.getText().toString();
                Log.i("bdtrace","===============> button is click! ");
                Toast.makeText(getApplicationContext(), "短信内容为："+sms, Toast.LENGTH_LONG).show();

                myAsync = new TestSyncTask();
                SmsReceiver.MyAsyncTask.execute(test);
                break;

            default:
                break;
        }
    }

    TestSyncTask myAsync = null;

    Runnable test = new Runnable() {
        public void run() {
            sendSMS();
        }
    };

    private boolean sendSMS() {
        try {
            String sms = smsInfo.getText().toString();
            String params = "{\"content\":\""
                    + sms
                    + "\"}";

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

    public class TestSyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Log.i("asyncTask", "doInBackground");
            return "";
        }
    }
}
