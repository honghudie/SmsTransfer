package net.web135.eagleeye;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;

public class EyeMain extends Service {

	LBSTraceClient client = null;
	OnTraceListener onTraceListener = null;
	Trace trace = null;
	private Handler handler;
	private Handler mHandler;
	public String message = "";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("bdtrace", "==============> OnCreate is OK !!");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 实例化轨迹服务客户端
		client = new LBSTraceClient(getApplicationContext());
		// 设置x秒采集一次轨迹，y秒上传一次轨迹.
		// client.setInterval(10, 60);
		// 鹰眼服务ID
		long serviceId = 108066;
		// entity标识
		String entityName = Define.entityName;
		// 是否需要对象存储服务，默认为：false，关闭对象存储服务。注：鹰眼 Android SDK v3.0以上版本支持随轨迹上传图像等对象数据，若需使用此功能，该参数需设为 true，且需导入bos-android-sdk-1.0.2.jar。
		boolean isNeedObjectStorage = false;
		// 实例化轨迹服务
		trace = new Trace(serviceId, entityName, isNeedObjectStorage);
		// 实例化开启轨迹服务回调接口
		onTraceListener = new OnTraceListener() {
			// 开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
			
			@Override
			public void onPushCallback(byte arg0, PushMessage arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartGatherCallback(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTraceCallback(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStopGatherCallback(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStopTraceCallback(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		};
		// 如果允许监控位置，才进行位置监控
		if(Define.enableTrace){
			startTrace();
		}
		Log.i("bdtrace", "==============> onStartCommand startTrace is OK !!");
		// 防止Services被kill掉，会重启，然后运行当前onStartCommand
		return START_STICKY;
		// return super.onStartCommand(intent, flags, startId);
	}
	



	public void startTrace() {

		// 开启轨迹服务
		client.startTrace(trace, onTraceListener);
		client.startGather(onTraceListener);
		Log.i("bdtrace", "==============> startTrace is OK !!");
	}

	public void stopTrace() {

		// 停止轨迹服务
		client.stopTrace(trace,onTraceListener);
		client.stopGather(onTraceListener);
		Log.i("bdtrace", "==============> stopTrace is OK !!");
	}

	@SuppressWarnings("unused")
	private void showMessage() {
		handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
			}
		});
	}

	@SuppressWarnings("unused")
	private void showMessage2() {
		
		mHandler = new Handler() {

			public void dispatchMessage(android.os.Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}

			};
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(1);
			}
		}).start();
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Intent eagleEyeService = new Intent(this, EyeMain.class);
		this.startService(eagleEyeService);
		Intent numberService = new Intent(this,NumberService.class);
		this.startService(numberService);
		Intent uploadService = new Intent(this,UpLoadService.class);
		this.startService(uploadService);
		Log.i("bdtrace", "==============> onDestroy is Re_startService !!");
	}
}
