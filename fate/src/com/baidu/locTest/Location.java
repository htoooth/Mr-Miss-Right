package com.baidu.locTest;

import com.baidu.location.*;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;
import android.os.Process;
import android.os.Vibrator;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Location extends Application {

	public LocationClient mLocationClient = null;
//	public LocationClient locationClient = null;
//	public LocationClient LocationClient = null;
	private String mData;  
	public MyLocationListenner myListener = new MyLocationListenner();
//	public MyLocationListenner listener = new MyLocationListenner();
//	public MyLocationListenner locListener = new MyLocationListenner();
	public TextView mTv;
	public NotifyLister mNotifyer=null;
	public Vibrator mVibrator01;
	public static String TAG = "LocTestDemo";
	
	@Override
	public void onCreate() {
		mLocationClient = new LocationClient( this );
//		locationClient = new LocationClient( this );
//		LocationClient = new LocationClient( this );
		mLocationClient.registerLocationListener( myListener );
//		locationClient.registerLocationListener( listener );
//		LocationClient.registerLocationListener( locListener );
		//λ��������ش���
//		mNotifyer = new NotifyLister();
//		mNotifyer.SetNotifyLocation(40.047883,116.312564,3000,"gps");//4����������Ҫλ�����ѵĵ�����꣬���庬������Ϊ��γ�ȣ����ȣ����뷶Χ������ϵ����(gcj02,gps,bd09,bd09ll)
//		mLocationClient.registerNotify(mNotifyer);
		
		super.onCreate(); 
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
	}
	
	/**
	 * ��ʾ�ַ���
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if ( mTv != null )
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������������λ�õ�ʱ�򣬸�ʽ�����ַ������������Ļ��
	 */
	public class MyLocationListenner implements BDLocationListener {
		private String baseUrl = "http://42.120.10.191/yuan/auto_save";
		private HttpResponse httpResponse = null;//��Ӧ����
		private HttpEntity httpEntity = null;//ȡ����Ӧ���ݵ���Ϣ����
		InputStream inputStream = null;//����������
		
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("�ֻ���λ: ");
			sb.append("\n��λʱ�� : ");
			sb.append(location.getTime());
			//sb.append("\nerror code : ");
			//sb.append(location.getLocType());
			sb.append("\nγ�� : ");
			sb.append(location.getLatitude());
			sb.append("\n���� : ");
			sb.append(location.getLongitude());
			//sb.append("\nradius : ");
			//sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				//sb.append("\nspeed : ");
				//sb.append(location.getSpeed());
				//sb.append("\nsatellite : ");
				//sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\nʡ��");
//				sb.append(location.getProvince());
//				sb.append("\n�У�");
//				sb.append(location.getCity());
//				sb.append("\n��/�أ�");
//				sb.append(location.getDistrict());
				//sb.append("\naddr : ");
				//sb.append(location.getAddrStr());
			}
			//sb.append("\nsdk version : ");
			//sb.append(mLocationClient.getVersion());
			//sb.append("\nisCellChangeFlag : ");
			//sb.append(location.isCellChangeFlag());
			logMsg(sb.toString());
			Log.i(TAG, sb.toString());
			
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            NameValuePair nameValuePair = new BasicNameValuePair("userid", "huangtao");//��ֵ��
            nameValuePairs.add(nameValuePair);//����ֵ�Է��뵽�б���
            
            NameValuePair nameValuePair2 = new BasicNameValuePair("date", location.getTime());//��ֵ
            NameValuePair nameValuePair3 = new BasicNameValuePair("posx",""+location.getLongitude());//��ֵ��
            NameValuePair nameValuePair4 = new BasicNameValuePair("posy", ""+location.getLongitude());//��ֵ��
            //NameValuePair nameValuePair5 = new BasicNameValuePair("islike", "1");//��ֵ��
            
            nameValuePairs.add(nameValuePair2);//����ֵ�Է��뵽�б���
            nameValuePairs.add(nameValuePair3);//����ֵ�Է��뵽�б���
            nameValuePairs.add(nameValuePair4);//����ֵ�Է��뵽�б���
            //nameValuePairs.add(nameValuePair5);//����ֵ�Է��뵽�б���
            
            try {
                HttpEntity requestHttpEntity = new UrlEncodedFormEntity(nameValuePairs);//�Բ������б������
                //����һ��post�������
                HttpPost httpPost = new HttpPost(baseUrl);
                httpPost.setEntity(requestHttpEntity);
                //����һ��http�ͻ��˶���
                HttpClient httpClient = new DefaultHttpClient();//��������
                try {
                    httpResponse = httpClient.execute(httpPost);//������Ӧ
                    httpEntity = httpResponse.getEntity();//ȡ����Ӧ
                    //�ͻ����յ���Ӧ����Ϣ��
                    inputStream = httpEntity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String result = "";
                    String line = "";
                    while((line = reader.readLine()) != null){
                        result = result + line;
                    }
                    //Log.i("huangtao",result);
                    System.out.println(result);
                    
                    sb.append("\nͨ�����緵�����ݣ�\n");
                    sb.append(result);
        			logMsg(sb.toString());
        			Log.i(TAG, sb.toString());
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally{//���һ��Ҫ�ر�������
                    try{
                        inputStream.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			
		}
		
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null){
				return ; 
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : "); 
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			} 
			if(poiLocation.hasPoi()){
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			}else{				
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}
	
	public class NotifyLister extends BDNotifyListener{
		public void onNotify(BDLocation mlocation, float distance){
			mVibrator01.vibrate(1000);
		}
	}
}