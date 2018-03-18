package cn.fwwb.utils.pay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.impl.jam.mutable.MParameter;

public class PayProperties {
	public static String appid;
	public static String appSecret;
	public static String mch_id;
	public static String key;
	public static String redirect_uri;
	public static String getAccessTokenUrl;
	public static String token;
	public static String EncodingAESKey;
	public static String getUserInfoUrl;
	static {
		Properties properties = new Properties();
		InputStream inStream = PayProperties.class.getClassLoader().getResourceAsStream("wx.properties");
		try {
			properties.load(inStream);
			appid = properties.getProperty("appid");
			appSecret = properties.getProperty("appSecret");
			mch_id = properties.getProperty("mch_id");
			key = properties.getProperty("key");
			redirect_uri = properties.getProperty("redirect_uri");
			getAccessTokenUrl = properties.getProperty("getAccessTokenUrl");
			token = properties.getProperty("token");
			getUserInfoUrl = properties.getProperty("getUserInfoUrl");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inStream);
		}
	}
}
