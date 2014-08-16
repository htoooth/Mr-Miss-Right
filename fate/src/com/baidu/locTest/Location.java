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
		//位置提醒相关代码
//		mNotifyer = new NotifyLister();
//		mNotifyer.SetNotifyLocation(40.047883,116.312564,3000,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
//		mLocationClient.registerNotify(mNotifyer);
		
		super.onCreate(); 
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
	}
	
	/**
	 * 显示字符串
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
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		private String baseUrl = "http://42.120.10.191/yuan/auto_save";
		private HttpResponse httpResponse = null;//响应对象
		private HttpEntity httpEntity = null;//取出响应内容的消息对象
		InputStream inputStream = null;//输入流对象
		
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("手机定位: ");
			sb.append("\n定位时间 : ");
			sb.append(location.getTime());
			//sb.append("\nerror code : ");
			//sb.append(location.getLocType());
			sb.append("\n纬度 : ");
			sb.append(location.getLatitude());
			sb.append("\n经度 : ");
			sb.append(location.getLongitude());
			//sb.append("\nradius : ");
			//sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				//sb.append("\nspeed : ");
				//sb.append(location.getSpeed());
				//sb.append("\nsatellite : ");
				//sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\n省：");
//				sb.append(location.getProvince());
//				sb.append("\n市：");
//				sb.append(location.getCity());
//				sb.append("\n区/县：");
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
            NameValuePair nameValuePair = new BasicNameValuePair("userid", "huangtao");//键值对
            nameValuePairs.add(nameValuePair);//将键值对放入到列表中
            
            NameValuePair nameValuePair2 = new BasicNameValuePair("date", location.getTime());//键值
            NameValuePair nameValuePair3 = new BasicNameValuePair("posx",""+location.getLongitude());//键值对
            NameValuePair nameValuePair4 = new BasicNameValuePair("posy", ""+location.getLongitude());//键值对
            //NameValuePair nameValuePair5 = new BasicNameValuePair("islike", "1");//键值对
            
            nameValuePairs.add(nameValuePair2);//将键值对放入到列表中
            nameValuePairs.add(nameValuePair3);//将键值对放入到列表中
            nameValuePairs.add(nameValuePair4);//将键值对放入到列表中
            //nameValuePairs.add(nameValuePair5);//将键值对放入到列表中
            
            try {
                HttpEntity requestHttpEntity = new UrlEncodedFormEntity(nameValuePairs);//对参数进行编码操作
                //生成一个post请求对象
                HttpPost httpPost = new HttpPost(baseUrl);
                httpPost.setEntity(requestHttpEntity);
                //生成一个http客户端对象
                HttpClient httpClient = new DefaultHttpClient();//发送请求
                try {
                    httpResponse = httpClient.execute(httpPost);//接收响应
                    httpEntity = httpResponse.getEntity();//取出响应
                    //客户端收到响应的信息流
                    inputStream = httpEntity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String result = "";
                    String line = "";
                    while((line = reader.readLine()) != null){
                        result = result + line;
                    }
                    //Log.i("huangtao",result);
                    System.out.println(result);
                    
                    sb.append("\n通过网络返回数据：\n");
                    sb.append(result);
        			logMsg(sb.toString());
        			Log.i(TAG, sb.toString());
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally{//最后一定要关闭输入流
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