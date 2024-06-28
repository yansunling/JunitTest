package com.str.string;

public class LoveMain {
    public static void main(String[] args) throws Exception{

     //   System.out.println(new Timestamp(1692247783000L));


        /*Double str = 400000.0;
        str=str*100;
        System.out.println(str.intValue() );*/
        int count=0;
        float x,y,a;
        for(y=2.5f;y>-2.0f;y-=0.12f){
            for(x=-2.3f;x<2.3f;x+=0.041f){
                a=x*x+y*y-4f;
                if(a*a*a-x*x*y*y*y<0.0f){
                    String str="I LOVE YOU";
                    int num=count%str.length();
                    System.err.print(str.charAt(num));
                    count++;
                }else{
                    System.err.print(" ");
                }
            }
            System.out.println();
            Thread.sleep(100);
        }

    }
}
