package com.wx.data;
/** 
* @author fukaijian
* @since 2018年6月22日
* @Description 
* 微信发送消息返回结果
*/
public class MMS_wechat_result {

	/**
	 * 错误编码，0表示正常
	 */
	private Integer errcode;
	
	/**
	 * 错误信息
	 */
	private String errmsg;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	@Override
	public String toString() {
		return "MMS_wechat_result{" +
				"errcode=" + errcode +
				", errmsg='" + errmsg + '\'' +
				'}';
	}
}
