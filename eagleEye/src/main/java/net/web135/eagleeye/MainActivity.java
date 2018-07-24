package net.web135.eagleeye;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btnStart).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent service = new Intent(MainActivity.this,EyeMain.class);
		Intent numberService = new Intent(MainActivity.this,NumberService.class);
		switch (v.getId()) {
		case R.id.btnStart:
			startService(service);
			startService(numberService);
			Intent uploadService = new Intent(this,UpLoadService.class);
			startService(uploadService);
			Log.i("bdtrace","===============> button is click! ");
			Toast.makeText(getApplicationContext(), "所有设备正常，如有疑问请联系售后服务中心。", Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}
	}
}
