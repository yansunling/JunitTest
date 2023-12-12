package com.excel.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class QUERYConstant {

    //普通字段
    public static final String C_BACK_COLUMN_TYPE_1 = "1";
    //表达式
    public static final String C_BACK_COLUMN_TYPE_2 = "2";
    //and连接
    public static final String C_CONNECT_SIGN_AND = "and";
    //or连接
    public static final String C_CONNECT_SIGN_OR = "or";
    //前台查询
    public static final String C_QUERY_COLUMN_TYPE_1 = "1";
    //后台参数
    public static final String C_QUERY_COLUMN_TYPE_2 = "2";
    //固定值
    public static final String C_QUERY_COLUMN_TYPE_3 = "3";
    //后台配置OR
    public static final String C_QUERY_COLUMN_TYPE_4 = "4";
    //特殊后台配置
    public static final String C_QUERY_COLUMN_TYPE_5 = "5";
    //缓存配置
    public static final String C_CONFIG_TYPE_CACHE = "cache";
    
    
    //mysql数据库类型
    public static final String C_SOURCE_TYPE_MYSQL = "mysql";

    public static final String C_SOURCE_TYPE_SQL_SERVER = "sqlServer";
    
    //mongo数据库类型
    public static final String C_SOURCE_TYPE_MONGO = "mongo";
    //elasticsearch数据库类型
	public static String C_SOURCE_TYPE_ELASTICSEARCH = "elasticsearch";
    
    
    //状态true
    public static final String C_STATUS_TRUE = "TRUE";
    
    //状态false
    public static final String C_STATUS_FALSE = "FALSE";
    
    public static final int C_OPERATOR_TYPE_EQUAL=0;
    public static final int C_OPERATOR_TYPE_GREAT_THAN=1;
    public static final int C_OPERATOR_TYPE_LESS_THAN=2;
    public static final int C_OPERATOR_TYPE_GREAT_EQUAL=3;
    public static final int C_OPERATOR_TYPE_LESS_EQUAL=4;
    public static final int C_OPERATOR_TYPE_NOT_EQUAL=5;
    public static final int C_OPERATOR_TYPE_BETWEEN=6;
    public static final int C_OPERATOR_TYPE_IN=7;
    public static final int C_OPERATOR_TYPE_LIKE=8;
    //空操作符
    public static final int C_OPERATOR_TYPE_IS_NULL=10;
    //非空操作符
    public static final int C_OPERATOR_TYPE_IS_NOT_NULL=11;
	//not in操作符
    public static final int C_OPERATOR_TYPE_NOT_IN=12;
    
    public static final String ELASTIC_SEARCH_FLAG = "elasticsearch::elasticsearch";
    public static final String ELASTIC_SEARCH_SIZE = "size";
    public static final String ELASTIC_SEARCH_FROM = "from";
    public static final String ELASTIC_SEARCH_INDEX = "index";
    public static final String ELASTIC_SEARCH_SOURCE = "_source";
   
    //查询配置
    public static final String QUERY_CONF_REGISTER = "register";
    public static final String QUERY_CONF_TABLES = "tables";
    public static final String QUERY_CONF_WHERES = "wheres";
    public static final String QUERY_CONF_COLUMNS = "columns";
    
    //排序方式
    public static final String QUERY_ORDER_TYPE_DESC="desc";
    public static final String QUERY_ORDER_TYPE_ASC="asc";
    
    //参数连接标识
    public static final String C_CONNECT_TYPE_2 = "2";
    //条件和参数连接标识
    public static final String C_CONNECT_TYPE_3 = "3";
    //条件OR连接标识
    public static final String C_CONNECT_TYPE_4 = "4";
    //表参数替换添加单引号
    public static final String C_DATA_FORMAT_1 = "1";
    //表参数替换不添加单引号
    public static final String C_DATA_FORMAT_2 = "2";
    //默认版本号
    public static final String DEFUAL_VERSION="V1.0.0";
    //默认参数
    public static final String DEFUAL_PARAM_QUERY_ID="queryId";
    public static final String DEFUAL_PARAM_VERSION="version";

    //高亮
    public static final String HIGHT_LIGHT = "hightlight";
    //广播推送队列
    public static final String FANOUNT_EXCHANGE="QUERY_FANOUT_DATASOURCE";
    //es join前缀
    public static final String ELASTICSEARCH_JOIN_PRIX="dy:query:public:cache:elasticSearch_join_";
    //redis cache前缀
    public static final String REDIS_CACHE_PRIX="dy:query:public:cache:";


    //是
    public static final String YES_Y="Y";
    //否
    public static final String YES_N="N";
    //启用
    public static final String STATUS_ENABLE="ENABLE";
    //禁用
    public static final String STATUS_DISABLE="DISABLE";
    //结果为空
    public static final String JOB_SUCCESS_FLAG_0="0";
    //结果有值
    public static final String JOB_SUCCESS_FLAG_1="1";
    //query-job默认数据源Id
    public static final String JOB_ELASTICSEARCH_SOURCE_ID="query-job-elastic";
    //query-job查询结果数据
    public static final String JOB_ELASTICSEARCH_CONTENT_FILED="content";
    //query-job默认索引
    public static final String JOB_ELASTICSEARCH_INDEX="query-job-search-result";
    //成功状态
    public static final String STATUS_SUCCESS="SUCCESS";
    //失败状态
    public static final String STATUS_FAIL="FAIL";

    public static final String NOTICE_CONFIG_ERROR="error";
    //execl读取列数
    public static final int EXECL_DEFAULT_SIZE=50;
    //任务定时状态
    public static final String JOB_LOG_QUERY_ID="query_job_log_es_list";

    public static final String EXPORT_NUMBER="NUMBER";
    public static final String EXPORT_VARCHAR="VARCHAR";

    //任务定时状态
    public static final String QUERY_STATUS_SAVA="save";


    //简单查询类型
    public static final String WHERE_TYPE_SIMPLE="simple";

    //复杂查询类型
    public static final String WHERE_TYPE_COMPLEX="complex";

    //权限控制类型
    public static final String WHERE_TYPE_AUTH="auth";
    //左模糊
    public static final String QUERY_OPERATE_LIKE_LEFT="like %";
    //全模糊
    public static final String QUERY_OPERATE_LIKE_ALL="like %%";
    //为空
    public static final String QUERY_OPERATE_IS_NULL="is null";
    //不为空
    public static final String QUERY_OPERATE_IS_NOT_NULL="is not null";
    //in
    public static final String QUERY_OPERATE_IN="in";
    //in
    public static final String QUERY_OPERATE_NOT_IN="not in";

    //新增操作类型
    public static final String OPERATE_MODE_ADD="add";


    //关键字keyname
    public static final String WORD_KEYNAME="keyname";

    //关键字valuename
    public static final String WORD_VALUENAME="valuename";
    //关键字domain_id
    public static final String WORD_DOMAINID="domain_id";

    //默认数据源ID名称
    public static final String DEFAULT_SOURCE_ID="query_cip_admin_codes";
    //查询缓存使用类型
    public static final String USE_TYPE_SELECT="select";

    //查询接口使用类型
    public static final String USE_TYPE_INTERFACE="interface";

    //主表标记，用于合法性校验
    public static final String MAIN_TABLE_MARKED="main_table_marked";


    //有问题查询标记
    public static final String QUERY_STATUS_ERROR="error";

    //数据源字典redis缓存前缀，完整字典map
    public static String REDIS_CIP_ADMIN_CODES_REDIS_PREX= REDIS_CACHE_PRIX+"cip_admin_code_";

    //查询结果redis缓存前缀，完整字典map
    public static String REDIS_QUERY_RESULT_PREX= QUERYConstant.REDIS_CACHE_PRIX+"query_base_cache_new_";

    //数据源字典redis缓存前缀，keyvalue拼接map
    public static String REDIS_CIP_ADMIN_CODES_MAP_REDIS_PREX= REDIS_CACHE_PRIX+"map_cip_admin_code_";

    //数据源redis doaminList 前缀
    public static String REDIS_CIP_ADMIN_CODES_DOMAIN_LIST_REDIS_PREX= REDIS_CACHE_PRIX+"domain_list_prex_";


    //redis字典表缓存前缀
    public static String QUERY_NEW_CACHE_DATA_CODES_PREX= "cip_admin_codes_cache_prex_";

    //redis字典缓存排序前缀
    public static String QUERY_NEW_CACHE_DATA_CODES_ORDER_PREX= "cip_admin_codes_order_prex_";


    //接口登录校验规则
    public static String REDIS_AUTH_FILTER_PREX= REDIS_CACHE_PRIX+"login_flag:";



    public static String FORMAT_TYPE_DATE="DATE";

    public static String FORMAT_TYPE_AMOUNT="AM";

    public static String FORMAT_TYPE_YH="YH";

    public static String FORMAT_TYPE_CACHE_NULL="CACHE_NULL";


    public static String USE_TYPE_UI="ui";//使用类型界面

    public static String USE_TYPE_FORM="form"; //使用类型报表


    public static String DOWN_TYPE_ALL="all";

    public static String LIMIT_AMOUNT_DEFAUT="15";


    public static String ALIAN_TYPE_CENTER="center";



    //自定义排序redis缓存新版
    public static final String REDIS_ORDER_BY_PRIX_VUE="dy:query:public:cache:order_vue:";


    //自定义排序redis缓存旧版
    public static final String REDIS_ORDER_BY_PRIX="dy:query:public:cache:order:";

    //默认值all
    public static final String DEFAULT_VALUE_ALL="all";
    //default默认值
    public static final String DEFAULT_VALUE="default";
    //defaultFlag默认值
    public static final String DEFAULT_FLAG_VALUE="defaultFlag";

    //请求参数userId
    public static final String REQUEST_PARAM_USER_ID="userId";

    //跳转类型QUERY
    public static final String TARGET_TYPE_QUERY="QUERY";
    //跳转类型url
    public static final String TARGET_TYPE_URL="URL";

    //跳转参数类型
    public static final String DEFAULT_TYPE_COLUMNS="columns";

    public static final String DEFAULT_TYPE_PARAMS="params";


    public static final String ES_OPERATOR_MUST="must";

    public static final String ES_OPERATOR_MUST_NOT="must_not";


    public static final String ES_OPERATOR_SHOULD="should";

    //默认个性自定义方案
    public static final String QUERY_DEFINE_DEFAULT_USER="default_user";
    //查询方案参数
    public static final String REQUEST_PARAM_PLAIN_ID="planId";
    //自定义列加载方式
    public static final String REQUEST_PARAM_LOAD_COLUMN_TYPE="loadColumnType";
    //用户名称缓存queryId
    public static final String QUERY_CIP_ADMIN_USER_CACHE="query_cip_admin_user_cache";


    //query通用帮助映射redis key
    public static final String QUERY_LINK_REF_REDIS_KEY="dy:query:public:cache:link:ref";
    //null空字符串
    public static final String QUERY_NULL_STRING="null";




    private static Map<Integer, String> queryOpertor = getOpertor();

    public static Map<Integer, String> getOpertor() {
        if (queryOpertor == null)
            queryOpertor = new LinkedHashMap<Integer, String>();
        else
            return queryOpertor;
        queryOpertor.put(C_OPERATOR_TYPE_EQUAL," ='?' ");
        queryOpertor.put(C_OPERATOR_TYPE_GREAT_THAN," >'?' ");
        queryOpertor.put(C_OPERATOR_TYPE_LESS_THAN," <'?' ");
        queryOpertor.put(C_OPERATOR_TYPE_GREAT_EQUAL," >='?' ");
        queryOpertor.put(C_OPERATOR_TYPE_LESS_EQUAL," <='?' ");
        queryOpertor.put(C_OPERATOR_TYPE_NOT_EQUAL," !='?' ");
        queryOpertor.put(C_OPERATOR_TYPE_BETWEEN," between '?' and '?' ");
        queryOpertor.put(C_OPERATOR_TYPE_IN," in('?') ");
        queryOpertor.put(C_OPERATOR_TYPE_LIKE," like '?' ");

        return queryOpertor;
    }


    private static Map<Integer, String> queryOpertorRef = getOpertorRef();

    public static Map<Integer, String> getOpertorRef() {
        if (queryOpertorRef == null)
            queryOpertorRef = new LinkedHashMap<Integer, String>();
        else
            return queryOpertorRef;
        queryOpertorRef.put(C_OPERATOR_TYPE_EQUAL,"=");
        queryOpertorRef.put(C_OPERATOR_TYPE_GREAT_THAN,">");
        queryOpertorRef.put(C_OPERATOR_TYPE_LESS_THAN,"<");
        queryOpertorRef.put(C_OPERATOR_TYPE_GREAT_EQUAL,">=");
        queryOpertorRef.put(C_OPERATOR_TYPE_LESS_EQUAL,"<=");
        queryOpertorRef.put(C_OPERATOR_TYPE_NOT_EQUAL,"!=");
        queryOpertorRef.put(C_OPERATOR_TYPE_BETWEEN,"between");
        queryOpertorRef.put(C_OPERATOR_TYPE_IN,"in");
        queryOpertorRef.put(C_OPERATOR_TYPE_IS_NULL,"is null");
        queryOpertorRef.put(C_OPERATOR_TYPE_IS_NOT_NULL,"is not null");
        queryOpertorRef.put(C_OPERATOR_TYPE_NOT_IN,"not in");

        return queryOpertorRef;
    }






    
}
