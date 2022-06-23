package com.dy.test.ops;

import com.dy.components.mqutil.config.TaskChannelMessage;
import com.yd.utils.mq.ChannelMessage;
import javafx.concurrent.Task;

import java.io.Serializable;

public class OPS_info extends TaskChannelMessage implements Serializable {
    private static final long serialVersionUID = -1914932144452997480L;

    private String error_code;
    private String pack_serial_no;
    private String error_msg;
    private String ops_apply_deploy_serial_no;
    private String env_type;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getPack_serial_no() {
        return pack_serial_no;
    }

    public void setPack_serial_no(String pack_serial_no) {
        this.pack_serial_no = pack_serial_no;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getOps_apply_deploy_serial_no() {
        return ops_apply_deploy_serial_no;
    }

    public void setOps_apply_deploy_serial_no(String ops_apply_deploy_serial_no) {
        this.ops_apply_deploy_serial_no = ops_apply_deploy_serial_no;
    }

    public String getEnv_type() {
        return env_type;
    }

    public void setEnv_type(String env_type) {
        this.env_type = env_type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"error_code\":\"")
                .append(error_code).append('\"');
        sb.append(",\"pack_serial_no\":\"")
                .append(pack_serial_no).append('\"');
        sb.append(",\"error_msg\":\"")
                .append(error_msg).append('\"');
        sb.append(",\"ops_apply_deploy_serial_no\":\"")
                .append(ops_apply_deploy_serial_no).append('\"');
        sb.append(",\"env_type\":\"")
                .append(env_type).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
