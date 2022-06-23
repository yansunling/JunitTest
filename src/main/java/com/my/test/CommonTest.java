package com.my.test;

public class CommonTest {
    public static void main(String[] args) {
        String sql="AUTHPermissionResult result = AUTHPermissionUtils.getPermissionByItemCode(BMSPAuthConstant.AUTH_SETTING.AUTH_SETTING_SETTLE.sCode());\n" +
                "        List<String> authList = CollUtil.newArrayList();\n" +
                "        Map<String,Object> authMap=CollUtil.newHashMap();\n" +
                "        if (result.isNotEmpty() && CollUtil.isNotEmpty(result.toList())) {\n" +
                "            authList = result.toList();\n" +
                "            authMap.put(BMSPConstant.AUTH_ORG,authList);\n" +
                "        }else{\n" +
                "            String route = RouteUtils.getRoute(op_user.getSubject_org(), op_user.getSubject_id());\n" +
                "            authList.add(route);\n" +
                "            authMap.put(BMSPConstant.AUTH_ORG,authList);\n" +
                "        }\n" +
                "\n" +
                "        Page<BMSP_balance_inorder_searchData> mPage = new Page<>(page.getPage(), page.getRows());\n" +
                "        dataMapper.searchData(mPage,param, CollectionUtil.newHashMap());\n" +
                "        page.setTotal(Math.toIntExact(mPage.getTotal()));\n" +
                "        return mPage.getRecords();";

        System.out.println(sql);

        System.out.println("-----------------------------");

        String returnData="CIPResponseQueryMsg msg = new CIPResponseQueryMsg();\n" +
                "        msg.rows = dataService.searchData(page,param, CIPRuntime.getOperateSubject());\n" +
                "        msg.total=page.getTotal();\n" +
                "        msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;\n" +
                "        msg.msg = CIPErrorCode.CALL_SUCCESS.name;\n" +
                "        return msg;";

        System.out.println(returnData);

        System.out.println("-----------------------------");

        System.out.println("public CIPResponseQueryMsg searchData(CIPPageInfo page, @RequestBody BMSP_balance_outcost_clear_searchParam param)");

        String controller="CIPPageInfo page = new CIPPageInfo(parameter.getPage(), parameter.getRows());\n" +
                "        String conditonStr = parameter.getSearch_condition();\n" +
                "        CIPReqCondition[] conditions = null;\n" +
                "        if (StringUtils.isNotBlank(conditonStr)) {\n" +
                "            conditions = JSONUtils.convertJson2Object(conditonStr, CIPReqCondition[].class);\n" +
                "        }\n" +
                "        BMSP_balance_outorder_clear_searchParam queryVO = new BMSP_balance_outorder_clear_searchParam();\n" +
                "        ConditionUtil.covertFromConditions2Param(conditions, queryVO);\n" +
                "        return actionComponent.searchData(page, queryVO);";

        System.out.println(controller);


        System.out.println("-----------------------------");

        String table="";

        String java="List<BMSP_balance_outcost_detailVO> searchData(BMSP_balance_outcost_detailVO vo, CIPRuntimeOperator op_user);";
        java = java.replaceFirst("VO", "_searchData");
        java = java.replaceFirst("VO", "_searchParam");
        java = java.replaceFirst("\\(", "\\(CIPPageInfo page,");


        System.out.println(java);


    }
}
