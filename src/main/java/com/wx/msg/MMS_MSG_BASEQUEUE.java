package com.wx.msg;

import com.dy.components.mqutil.config.TaskChannelMessage;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author 王鹏
 * @version 1.0
 * @date 2021/2/23 15:12
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class MMS_MSG_BASEQUEUE extends TaskChannelMessage {
    /**
     * 消息序列号
     */
    private String docSerialNo;

    /**
     * 发起人
     */
    private String issueUserId;

    /**
     * 发起人机构
     */
    private String issueOrgId;

    /**
     * 接收人
     */
//    private String recUserId;

    /**
     * 接收人机构
     */
    private String recOrgId;

    /**
     * 消息类型
     */
    private String docType;

    /**
     * 消息标题
     */
    private String docTitle;

    /**
     *
     */
    private String docDesc;

    /**
     * 消息参数,支持List、String数组和逗号分隔的String
     */
    private Object docParams;

    /**
     * 消息通道类型
     */
    private String docChannelType;

    /**
     * 消息通道编码，重发时从原先发送的通道发送
     */
    private String docChannelCode;

    /**
     * 是否转发
     */
    private String forwardFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 接收地址,支持List、String数组和用逗号隔开的String
     */
    private Object receiveAddr;

    /**
     * 系统id
     */
    private String sysId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 上级接送地址，如部门id，那么会查询该部门下所有用户
     */
    private Object supReceiveAddr;

    private String weaAppId;
}
