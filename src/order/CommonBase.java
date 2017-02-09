package order;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommonBase {
	String targetUrl="http://192.168.8.205:8080/OMS/orderInsert.action";
	String urlParams;
	
	public String getTargetUrl() {
		return targetUrl;
	}


	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}


	public String getUrlParams() {
		return urlParams;
	}


	public void setUrlParams(String urlParams) {
		this.urlParams = urlParams;
	}


	public void sendPost() throws Exception {
		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("authorize_token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJvcGVyYXRpb25fb3BlcmF0b3IiLCJleHAiOjE0ODA0MDY2NDksImNpZCI6Ik9NUyJ9.IHvKA6oopOy_O3KTjWSFnYAsB8iGhBTadao6AigBd-E");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(this.urlParams);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("send post request to url:"+this.targetUrl);
		System.out.println("post parameters:"+this.getUrlParams());
		System.out.println("response code:"+responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
	}
}
