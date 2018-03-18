package cn.fwwb.utils.pay;

import java.io.Serializable;
/**
 * 
 * @author 16224
 * @Description: 微信前端js需要的参数
 * @date 2018年1月2日 下午8:08:03
 */
public class JsAPI implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String padkage;
	private String signType;
	private String paySign;
	
	public String getPadkage() {
		return padkage;
	}

	public void setPadkage(String padkage) {
		this.padkage = padkage;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	

}
