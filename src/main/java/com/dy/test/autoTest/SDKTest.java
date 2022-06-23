package com.dy.test.autoTest;

public class SDKTest {
	public static void main(String[] args) throws Exception {
		
		
		
//		BOX_boxCar_infoVO  person=new BOX_boxCar_infoVO ();
//		Map<String,String> headers=new HashMap<String,String>();
//		headers.put("x-deviceid-header", "testESApi");
//		headers.put("x-guuid-header", "842f5caf-6093-4054-9e6b-0d8ae6465434");
//		
//		
//		
//		
//		
//		BOX_boxCar_infoDatas gms_test = DY_boxAPI.box_boxCar_info_searchCarList(headers, person);
//		if(gms_test.getData()!=null) {
//			System.out.println(gms_test.getData());
//		}
//		System.out.println(gms_test.getErrorCode());
//		System.out.println(gms_test.getMsg());
//		
//		String loginEsb = DY_boxAPI.loginEsb("admin", "1111");
//		System.out.println(loginEsb);
		
		
		String value="'";
		value=value.replaceAll("'", "\\\\\\\\'");
		System.out.println(value);
		
		
		String newValue="'?'";
		
		System.out.println(newValue.replaceFirst("\\?", value));
		

	}
	

	
}
