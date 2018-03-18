package cn.fwwb.utils.pay;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.wxpay.sdk.WXPayUtil;
import com.thoughtworks.xstream.XStream;

import cn.fwwb.utils.SerializeXmlUtil;

/**
 * 封装支付的常用操作
 * 
 * @author 16224
 *
 */
public class WxUtil {
	/**
	 * 在获取openid前，需要使用户点击该返回的url链接，其中的redirct_url是之后回调的url 一般在项目开始前 获得url后
	 * 在菜单栏中的首页写此url
	 * 
	 * @param encodeUri
	 *            encode后的url
	 * @param isBase
	 *            是否是基础的授权
	 * @param state
	 *            会给回调url传递的参数
	 * @return 返回的最后的url 可以直接写入菜单中
	 */
	public static String getURI(String encodeUri, boolean isBase, String state) {
		String scope = "snsapi_base";
		if (!isBase) {
			scope = "snsapi_userinfo ";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
		sb.append(PayProperties.appid);
		sb.append("&redirect_uri=");
		sb.append(encodeUri);
		sb.append("&response_type=code&scope=");
		sb.append(scope);
		sb.append("&state=");
		sb.append(state);
		sb.append("#wechat_redirect");
		return sb.toString();
	}

	/**
	 * 静默授权的获取openid
	 * 
	 * @param code
	 * @return
	 */
	public static String getOpenId(String code) {

		HttpRequest httprequest = HttpRequest.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ PayProperties.appid + "&code=" + code + "&grant_type=authorization_code");
		String json = httprequest.body();
		JSONObject jsonObject = JSON.parseObject(json);
		Integer error = (Integer) jsonObject.get("errcode");
		String openid = null;
		if (error == null) {
			openid = (String) jsonObject.get("openid");
		}
		return openid;
	}

	/**
	 * 判断微信版本
	 */
	public static boolean isWxCanPay(String User_Agent) {
		boolean flag = true;
		int start = StringUtils.lastOrdinalIndexOf(User_Agent, "/", 3);
		flag = (Double.parseDouble(StringUtils.substring(User_Agent, start + 1, start + 4)) > 5.0);
		return flag;
	}

	/**
	 * 统一下单后获得前端调用微信支付js的对象
	 */
	public static JsAPI placeOrder(String body, int total_fee, String notify_url, String openid) {
		HttpRequest httpRequest = HttpRequest.post("https://api.mch.weixin.qq.com/pay/unifiedorder");
		httpRequest.trustAllCerts();
		httpRequest.trustAllHosts();
		Map<String, String> map = new HashMap<>();
		map.put("appid", PayProperties.appid);
		map.put("mch_id", PayProperties.mch_id);
		map.put("nonce_str", randomString());
		map.put("body", body);
		map.put("out_trade_no", randomOrderId());
		map.put("total_fee", Integer.toString(total_fee));
		map.put("notify_url", notify_url);
		map.put("trade_type", "JSAPI");
		map.put("openid", openid);
		String sign = null;
		try {
			sign = WXPayUtil.generateSignature(map, PayProperties.key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("sign", sign);
		String xml = null;
		try {
			xml = WXPayUtil.mapToXml(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpRequest.send(xml);
		// 得到响应,生成对象
		String response = httpRequest.body();
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(WxJs.class);
		xs.alias("xml", WxJs.class);
		WxJs content = (WxJs) xs.fromXML(response);
		if (content.getReturn_code().equals("SUCCESS")) {
			JsAPI jsAPI = new JsAPI();
			jsAPI.setAppId(content.getAppid());
			String time = Long.toString(Timestamp.valueOf(LocalDateTime.now()).getTime());
			jsAPI.setTimeStamp(time);
			jsAPI.setNonceStr(randomString());
			jsAPI.setPadkage("prepay_id=" + content.getPrepay_id());
			jsAPI.setSignType("MD5");
			Map<String, String> resultmap = new HashMap<>();
			resultmap.put("appId", jsAPI.getAppId());
			resultmap.put("timeStamp", jsAPI.getTimeStamp());
			resultmap.put("nonceStr", jsAPI.getNonceStr());
			resultmap.put("package", jsAPI.getPadkage());
			resultmap.put("signType", "MD5");
			String str1 = null;
			try {
				str1 = WXPayUtil.generateSignature(resultmap, PayProperties.key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsAPI.setPaySign(str1);
			return jsAPI;
		} else {
			return null;
		}
	}

	// 生成随机字符串
	private static String randomString() {
		return RandomStringUtils.randomAlphanumeric(30);
	}

	// 生成唯一订单号
	private static String randomOrderId() {
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String str = RandomStringUtils.randomAlphanumeric(12);
		return time + str;
	}
}
