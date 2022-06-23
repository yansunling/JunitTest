package com.dy.test.string;

public class SqlPrint {
    public static void main(String[] args) {
        String formID="-58";
        String customid="1";
        String requestId="1";
        String getSql="SELECT\n" +
                "\toderbyOP.requestid,\n" +
                "\toderbyOP.logtype,\n" +
                "\toderbyOP.ischeck,\n" +
                "\toderbyOP.lcbh,\n" +
                "\toderbyOP.xtzdh,\n" +
                "\toderbyOP.pch,\n" +
                "\toderbyOP.operatedate,\n" +
                "\toderbyOP.operatetime,\n" +
                "\trequestbase.requestname,\n" +
                "\toderbyOP.receivedPersons,\n" +
                "\toderbyOP.isSendToTMS,\n" +
                "\toderbyOP.nodeid,\n" +
                "\toderbyOP.lastname\n" +
                "FROM(\n" +
                "SELECT\n" +
                "\tmain.id,\n" +
                "\tmain.requestid,\n" +
                "\trequestLog.logtype,\n" +
                "\tmain.ischeck,\n" +
                "\tmain.lcbh,\n" +
                "\tmain.xtzdh,\n" +
                "\tmain.pch,\n" +
                "\trequestLog.operatedate,\n" +
                "\trequestLog.operatetime,\n" +
                "\trequestLog.receivedPersons,\n" +
                "\trequestLog.isSendToTMS,\n" +
                "\trequestLog.nodeid,\n" +
                "\thrmResource.lastname\n" +
                "FROM\n" +
                "\tformtable_main_188 main\n" +
                "\tLEFT JOIN workflow_requestLog requestLog ON requestLog.requestid = main.requestId\n" +
                "\tleft join HrmResource hrmResource ON hrmResource.id = main.sqr\n" +
                "WHERE\n" +
                "\tmain.requestid=" +
                requestId+
                "\tAND ( requestLog.logtype = '0' or requestLog.logtype = '2' or requestLog.logtype = '3')\n" +
                "\t) oderbyOP\n" +
                "\tLEFT JOIN workflow_requestbase requestbase on requestbase.requestid = oderbyOP.requestid\n"+
                "ORDER BY\n" +
                "\toderbyOP.operatedate desc,"+
                "\toderbyOP.operatetime desc;";
        System.out.println(getSql);
    }
}
