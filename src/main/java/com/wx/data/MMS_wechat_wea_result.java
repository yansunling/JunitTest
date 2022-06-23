package com.wx.data;
/** 
* @author fukaijian
* @since 2018年6月22日
* @Description 
* 微信企业号发送消息返回结果
*/
public class MMS_wechat_wea_result extends MMS_wechat_result {

	
	/**
	 * 无效的userid列表
	 */
	private String invaliduser;
	
	/**
	 * 无效的部门id列表
	 */
	private String invalidparty;
	
	/**
	 * 无效的标签ID列表
	 */
	private String invalidtag;

	public String getInvaliduser() {
		return invaliduser;
	}

	public void setInvaliduser(String invaliduser) {
		this.invaliduser = invaliduser;
	}

	public String getInvalidparty() {
		return invalidparty;
	}

	public void setInvalidparty(String invalidparty) {
		this.invalidparty = invalidparty;
	}

	public String getInvalidtag() {
		return invalidtag;
	}

	public void setInvalidtag(String invalidtag) {
		this.invalidtag = invalidtag;
	}

	@Override
	public String toString() {
		return "MMS_wea_result{" +
				"invaliduser='" + invaliduser + '\'' +
				", invalidparty='" + invalidparty + '\'' +
				", invalidtag='" + invalidtag + '\'' +
				'}';
	}
}
