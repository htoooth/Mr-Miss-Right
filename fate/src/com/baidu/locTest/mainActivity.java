package com.baidu.locTest;

import java.io.InputStream;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Process;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class mainActivity extends Activity {
	private TextView mTv = null;
	private EditText mSpanEdit;
	private EditText mCoorEdit;
	private CheckBox mGpsCheck;
	private CheckBox mPriorityCheck;
	private Button   mStartBtn;
	private Button	 mSetBtn;
	private Button 	 mLocBtn;
	private Button 	 mPoiBtn;
	private Button 	 mOfflineBtn;
	private CheckBox mIsAddrInfoCheck;
	private boolean  mIsStart;
	private static int count = 1;
	private Vibrator mVibrator01 =null;
	private LocationClient mLocClient;
	   private String baseUrl = "http://42.120.10.191/yuan/search?";
	    
	    private HttpResponse httpResponse = null;//��Ӧ����
	    private HttpEntity httpEntity = null;//ȡ����Ӧ���ݵ���Ϣ����
	    InputStream inputStream = null;//����������

	public static String TAG = "LocTestDemo";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		mTv = (TextView)findViewById(R.id.textview);
		mSpanEdit = (EditText)findViewById(R.id.edit);
		mCoorEdit = (EditText)findViewById(R.id.coorEdit);
		mGpsCheck = (CheckBox)findViewById(R.id.gpsCheck);
		mPriorityCheck = (CheckBox)findViewById(R.id.priorityCheck);
		mStartBtn = (Button)findViewById(R.id.StartBtn);
		mLocBtn = (Button)findViewById(R.id.locBtn);
    
		mPoiBtn = (Button)findViewById(R.id.PoiReq);
		mOfflineBtn  = (Button)findViewById(R.id.offLineBtn);
		mIsAddrInfoCheck = (CheckBox)findViewById(R.id.isAddrInfocb);
		mIsStart = false;

		
		mLocClient = ((Location)getApplication()).mLocationClient;
		((Location)getApplication()).mTv = mTv;
		mVibrator01 =(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		((Location)getApplication()).mVibrator01 = mVibrator01;
		
		setLocationOption();
		mLocClient.start();
		mLocClient.requestLocation();
		
		//��ʼ/ֹͣ��ť 
		mStartBtn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				

					//mStartBtn.setText("��ʼ");
				
				Log.d(TAG, "... mStartBtn onClick... pid="+Process.myPid()+" count="+count++);
			}
		});

		//��λ��ť
		mLocBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				

				    
			}
		});


		mPoiBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLocClient.requestPoi();
			}
		});  
		
		//���߻�վ��λ��ť
		mOfflineBtn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				int req = mLocClient.requestOfflineLocation();
				Log.d("req","req:"+req);
			}
		});
	}   

	@Override
	public void onDestroy() {
		mLocClient.stop();
		((Location)getApplication()).mTv = null;
		super.onDestroy();
	}

	//������ز���
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(mGpsCheck.isChecked());				//��gps
		option.setCoorType(mCoorEdit.getText().toString());		//������������
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(mIsAddrInfoCheck.isChecked());	
		option.setAddrType("all");
		option.setScanSpan(3600000);
		option.setPriority(LocationClientOption.GpsFirst);      //������������
		option.disableCache(true);		
		mLocClient.setLocOption(option);
	}

	protected boolean isNumeric(String str) {   
		Pattern pattern = Pattern.compile("[0-9]*");   
		return pattern.matcher(str).matches();   
	}  


}