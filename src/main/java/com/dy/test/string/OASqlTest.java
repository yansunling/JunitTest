package com.dy.test.string;

public class OASqlTest {
    public static void main(String[] args) {
        String paramInt="10442";
        String str1 = " select distinct ";
        /* 8169 */     String str2 = " t1.workflowid ";
        /* 8170 */     String str3 = " from workflow_requestbase t1,workflow_currentoperator t2 ";
        /* 8171 */     String str4 = " where t1.requestid=t2.requestid ";
        /* 8172 */     str4 = str4 + " and (t1.deleted <> 1 or t1.deleted is null or t1.deleted='') ";
        /* 8173 */     str4 = str4 + " and t2.usertype = 0 and t2.userid = " + paramInt;
        /* 8174 */     str4 = str4 + " and (t2.isremark in('2','4') or (t2.isremark=0 and t2.takisremark ='-2')) ";
        /*      */
        /* 8176 */
            /* 8177 */       str4 = str4 + " and t2.iscomplete=0 ";
            /*      */
        /* 8179 */     str4 = str4 + " and  t2.islasttimes=1 ";
        /* 8180 */     str4 = str4 + " and t1.workflowID in(select id from workflow_base where isvalid in('1','3')) ";

        System.out.println(str1+str2+str3+str4);

    }
}
