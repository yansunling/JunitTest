package com.dy.test.ops;

import com.dy.components.mqutil.config.TaskChannelMessage;

import java.io.Serializable;

public class OPS_ADD_OR_UPDATE_APP extends TaskChannelMessage implements Serializable {
    private static final long serialVersionUID = 6310377875545486832L;
    private String app_code;
    private String owner_cust_code;
    private String project_id;

    private String env_type;

    private String app_name;

    private String apply_date;

    private String apply_user_id;
    private String apply_user;

    /**
     * 实例id
     */
    private String instance_id;

    /**
     * cpu配置
     */
    private String cpu_capacity;

    /**
     * 内存空间
     */
    private String memory_space;

    /**
     * 磁盘空间
     */
    private String storage_space;

    /**
     * 网络带宽
     */
    private String net_space;
    /**
     * 是否单例
     */
    private String onlyone_instance = "0";
    /**
     * 系统类型 默认为linux
     */
    private String os_type = "L";
    /**
     * 是否docker部署 默认为是
     */
    private String docker_flag = "1";


    public String getOnlyone_instance() {
        return onlyone_instance;
    }

    public void setOnlyone_instance(String onlyone_instance) {
        this.onlyone_instance = onlyone_instance;
    }

    public String getOs_type() {
        return os_type;
    }

    public void setOs_type(String os_type) {
        this.os_type = os_type;
    }

    public String getDocker_flag() {
        return docker_flag;
    }

    public void setDocker_flag(String docker_flag) {
        this.docker_flag = docker_flag;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getCpu_capacity() {
        return cpu_capacity;
    }

    public void setCpu_capacity(String cpu_capacity) {
        this.cpu_capacity = cpu_capacity;
    }

    public String getMemory_space() {
        return memory_space;
    }

    public void setMemory_space(String memory_space) {
        this.memory_space = memory_space;
    }

    public String getStorage_space() {
        return storage_space;
    }

    public void setStorage_space(String storage_space) {
        this.storage_space = storage_space;
    }

    public String getNet_space() {
        return net_space;
    }

    public void setNet_space(String net_space) {
        this.net_space = net_space;
    }

    public String getApply_date() {

        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public String getApp_code() {
        return app_code;
    }

    public void setApp_code(String app_code) {
        this.app_code = app_code;
    }

    public String getOwner_cust_code() {
        return owner_cust_code;
    }

    public void setOwner_cust_code(String owner_cust_code) {
        this.owner_cust_code = owner_cust_code;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }


    public String getEnv_type() {
        return env_type;
    }

    public void setEnv_type(String env_type) {
        this.env_type = env_type;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApply_user_id() {
        return apply_user_id;
    }

    public void setApply_user_id(String apply_user_id) {
        this.apply_user_id = apply_user_id;
    }

    public String getApply_user() {
        return apply_user;
    }

    public void setApply_user(String apply_user) {
        this.apply_user = apply_user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"app_code\":\"")
                .append(app_code).append('\"');
        sb.append(",\"owner_cust_code\":\"")
                .append(owner_cust_code).append('\"');
        sb.append(",\"project_id\":\"")
                .append(project_id).append('\"');
        sb.append(",\"env_type\":\"")
                .append(env_type).append('\"');
        sb.append(",\"app_name\":\"")
                .append(app_name).append('\"');
        sb.append(",\"apply_date\":\"")
                .append(apply_date).append('\"');
        sb.append(",\"apply_user_id\":\"")
                .append(apply_user_id).append('\"');
        sb.append(",\"apply_user\":\"")
                .append(apply_user).append('\"');
        sb.append(",\"instance_id\":\"")
                .append(instance_id).append('\"');
        sb.append(",\"cpu_capacity\":\"")
                .append(cpu_capacity).append('\"');
        sb.append(",\"memory_space\":\"")
                .append(memory_space).append('\"');
        sb.append(",\"storage_space\":\"")
                .append(storage_space).append('\"');
        sb.append(",\"net_space\":\"")
                .append(net_space).append('\"');
        sb.append(",\"onlyone_instance\":\"")
                .append(onlyone_instance).append('\"');
        sb.append(",\"os_type\":\"")
                .append(os_type).append('\"');
        sb.append(",\"docker_flag\":\"")
                .append(docker_flag).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
