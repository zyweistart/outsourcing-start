package com.start.utils;

import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.start.common.Constant;
import com.start.common.NetException;

public class HttpUtils {
	/**
	 * 检测网络连接
	 */
	public static boolean checkNet(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	/**
	 * <pre>
	 * @param requestHeader
	 * 		请求头
	 * @param requestContent
	 * 		请求主体内容
	 * </pre>
	 */
	public static InputStream requestServer(Map<String,String> requestHeader,String requestContent) throws NetException{
		try {
            HttpResponse response =requestDownServer(requestHeader,requestContent);
            return response.getEntity().getContent();
        } catch (Exception e) {
        		throw new NetException(e);
        }
	}
	
	public static HttpResponse requestDownServer(Map<String,String> requestHeader,String requestContent) throws NetException{
		HttpClient client = new DefaultHttpClient();
		//设置超时时间为10秒
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		HttpPost post = new HttpPost(Constant.HOST);
		try {
			//对请求头的数据进行URL编码
			for(String key:requestHeader.keySet()){
				post.addHeader(key, StringUtils.encode(requestHeader.get(key)));
			}
			AbstractHttpEntity entity = new ByteArrayEntity(requestContent.getBytes(Constant.ENCODE));
			post.setEntity(entity);
            return client.execute(post);
        } catch (Exception e) {
        		throw new NetException(e);
        }
	}
	
}