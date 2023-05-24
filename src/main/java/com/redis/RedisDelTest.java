package com.redis;

import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisDelTest {
    static Map<String,String> userRef=new HashMap<>();

    public static void main(String[] args) {
        String pattern="dy:query:public:cache:columns:*";
        Set<String> keys = RedisUtil.patternKey(pattern);
        for(String key:keys){
            System.out.println(key);
            String newKey = key.replace("dy:query:public:cache:columns:", "");
            String[] split = newKey.split(":");
            String userId=split[0];

            String newUserId = userRef.get(userId);
            if(StringUtils.isBlank(newUserId)){
                newUserId=userId;
            }
            String tlUserKey = key.replace(userId, newUserId);
            System.out.println(tlUserKey);
            String value=RedisUtil.getString(key);
            System.out.println(value);
            RedisUtil.tlPutWithStringKey(tlUserKey,value,-1);
        }
    }

    static {
        userRef.put("1","T0001");
        userRef.put("9501","T0002");
        userRef.put("100","T0003");
        userRef.put("97","T0004");
        userRef.put("96","T0005");
        userRef.put("5596","T0006");
        userRef.put("1918","T0007");
        userRef.put("1878","T0008");
        userRef.put("9391","T0009");
        userRef.put("9502","T0010");
        userRef.put("9506","T0011");
        userRef.put("3939","T0012");
        userRef.put("1162","T0013");
        userRef.put("278","T0014");
        userRef.put("1769","T0015");
        userRef.put("230","T0016");
        userRef.put("9316","T0017");
        userRef.put("9230","T0018");
        userRef.put("9448","T0019");
        userRef.put("9182","T0020");
        userRef.put("9522","T0021");
        userRef.put("9605","T0022");
        userRef.put("9601","T0023");
        userRef.put("9598","T0024");
        userRef.put("3950","T0025");
        userRef.put("8955","T0026");
        userRef.put("8078","T0027");
        userRef.put("525","T0028");
        userRef.put("1140","T0029");
        userRef.put("8614","T0030");
        userRef.put("325","T0031");
        userRef.put("108","T0032");
        userRef.put("9258","T0033");
        userRef.put("2626","T0035");
        userRef.put("2187","T0036");
        userRef.put("212","T0037");
        userRef.put("178","T0038");
        userRef.put("9508","T0039");
        userRef.put("9233","T0040");
        userRef.put("1586","T0041");
        userRef.put("183","T0042");
        userRef.put("117","T0043");
        userRef.put("977","T0044");
        userRef.put("333","T0045");
        userRef.put("2912","T0047");
        userRef.put("2027","T0048");
        userRef.put("8754","T0049");
        userRef.put("4095","T0050");
        userRef.put("1272","T0052");
        userRef.put("8571","T0053");
        userRef.put("5255","T0054");
        userRef.put("485","T0060");
        userRef.put("5413","T0061");
        userRef.put("3081","T0062");
        userRef.put("4005","T0063");
        userRef.put("8212","T0066");
        userRef.put("9582","T0068");
        userRef.put("9486","T0069");
        userRef.put("9281","T0070");
        userRef.put("4069","T0071");
        userRef.put("3499","T0072");
        userRef.put("2977","T0073");
        userRef.put("2371","T0074");
        userRef.put("2665","T0075");
        userRef.put("8554","T0076");
        userRef.put("8664","T0077");
        userRef.put("3491","T0078");
        userRef.put("3429","T0079");
        userRef.put("9600","T0081");
        userRef.put("7177","T0082");
        userRef.put("9441","T0083");
        userRef.put("9567","T0084");
        userRef.put("9610","T0085");
        userRef.put("9596","T0086");
        userRef.put("9737","T0087");
        userRef.put("2366","T0088");
        userRef.put("8184","T0089");
        userRef.put("3689","T0090");
        userRef.put("2752","T0091");
        userRef.put("9882","T0092");
        userRef.put("9269","T0093");
        userRef.put("8956","T0094");
        userRef.put("8776","T0095");
        userRef.put("8444","T0096");
        userRef.put("4124","T0097");
        userRef.put("8902","T0098");
        userRef.put("8957","T0099");
        userRef.put("7195","T0100");
        userRef.put("1615","T0103");
        userRef.put("642","T0104");
        userRef.put("755","T0105");
        userRef.put("8576","T0106");
        userRef.put("1959","T0107");
        userRef.put("310","T0108");
        userRef.put("7224","T0109");
        userRef.put("1668","T0110");
        userRef.put("8790","T0114");
        userRef.put("8850","T0115");
        userRef.put("8798","T0116");
        userRef.put("8781","T0119");
        userRef.put("5893","T0122");
        userRef.put("6440","T0123");
        userRef.put("9720","T0124");
        userRef.put("9778","T0125");
        userRef.put("8970","T0127");
        userRef.put("9400","T0128");
        userRef.put("9743","T0129");
        userRef.put("8832","T0131");
        userRef.put("8982","T0132");
        userRef.put("8997","T0133");
        userRef.put("9046","T0134");
        userRef.put("9065","T0135");
        userRef.put("9201","T0136");
        userRef.put("9781","T0137");
        userRef.put("9647","T0140");
        userRef.put("9263","T0143");
        userRef.put("9780","T0144");
        userRef.put("9075","T0145");
        userRef.put("9418","T0146");
        userRef.put("9323","T0147");
        userRef.put("9237","T0148");
        userRef.put("2943","T0149");
        userRef.put("8135","T0150");
        userRef.put("8186","T0151");
        userRef.put("249","T0152");
        userRef.put("240","T0153");
        userRef.put("1571","T0154");
        userRef.put("237","T0155");
        userRef.put("414","T0156");
        userRef.put("2944","T0157");
        userRef.put("9789","T0158");
        userRef.put("6373","T0159");
        userRef.put("5907","T0160");
        userRef.put("519","T0161");
        userRef.put("9416","T0162");
        userRef.put("6009","T0163");
        userRef.put("8180","T0164");
        userRef.put("3098","T0165");
        userRef.put("2808","T0166");
        userRef.put("1316","T0167");
        userRef.put("5970","T0168");
        userRef.put("2181","T0169");
        userRef.put("2151","T0170");
        userRef.put("9018","T0171");
        userRef.put("9010","T0172");
        userRef.put("9718","T0173");
        userRef.put("9100","T0174");
        userRef.put("5764","T0175");
        userRef.put("8215","T0176");
        userRef.put("8630","T0177");
        userRef.put("9608","T0178");
        userRef.put("1106","T0179");
        userRef.put("9607","T0180");
        userRef.put("7004","T0181");
        userRef.put("7453","T0182");
        userRef.put("3597","T0183");
        userRef.put("9606","T0184");
        userRef.put("5969","T0185");
        userRef.put("9217","T0186");
        userRef.put("8198","T0187");
        userRef.put("3488","T0188");
        userRef.put("3899","T0190");
        userRef.put("4024","T0191");
        userRef.put("8419","T0192");
        userRef.put("9328","T0193");
        userRef.put("9302","T0194");
        userRef.put("9595","T0195");
        userRef.put("7790","T0196");
        userRef.put("9184","T0197");
        userRef.put("3278","T0198");
        userRef.put("3526","T0199");
        userRef.put("6806","T0200");
        userRef.put("3535","T0201");
        userRef.put("9116","T0202");
        userRef.put("555","T0203");
        userRef.put("102","T0204");
        userRef.put("8746","T0205");
        userRef.put("3158","T0206");
        userRef.put("3170","T0207");
        userRef.put("9527","T0208");
        userRef.put("9538","T0209");
        userRef.put("9552","T0210");
        userRef.put("9528","T0211");
        userRef.put("9548","T0212");
        userRef.put("9609","T0213");
        userRef.put("9439","T0214");
        userRef.put("9613","T0215");
        userRef.put("9623","T0216");
        userRef.put("9611","T0217");
        userRef.put("9631","T0218");
        userRef.put("272","T0219");
        userRef.put("1147","T0220");
        userRef.put("3487","T0221");
        userRef.put("9183","T0222");
        userRef.put("8991","T0223");
        userRef.put("9453","T0224");
        userRef.put("9784","T0225");
        userRef.put("9301","T0226");
        userRef.put("9304","T0227");
        userRef.put("8817","T0228");
        userRef.put("9019","T0229");
        userRef.put("9361","T0230");
        userRef.put("8923","T0231");
        userRef.put("8961","T0232");
        userRef.put("8963","T0233");
        userRef.put("8985","T0234");
        userRef.put("9093","T0235");
        userRef.put("9095","T0236");
        userRef.put("9097","T0237");
        userRef.put("9098","T0238");
        userRef.put("9099","T0239");
        userRef.put("9419","T0240");
        userRef.put("7051","T0241");
        userRef.put("8636","T0242");
        userRef.put("9009","T0243");
        userRef.put("9055","T0244");
        userRef.put("9056","T0245");
        userRef.put("9057","T0246");
        userRef.put("9058","T0247");
        userRef.put("9144","T0248");
        userRef.put("9804","T0249");
        userRef.put("9808","T0250");
        userRef.put("8971","T0251");
        userRef.put("8972","T0252");
        userRef.put("9041","T0253");
        userRef.put("9202","T0254");
        userRef.put("9393","T0255");
        userRef.put("9902","T0256");
        userRef.put("9909","T0257");
        userRef.put("9067","T0258");
        userRef.put("9716","T0259");
        userRef.put("9785","T0260");
        userRef.put("9793","T0261");
        userRef.put("9794","T0262");
        userRef.put("9795","T0263");
        userRef.put("9796","T0264");
        userRef.put("9797","T0265");
        userRef.put("9303","T0266");
        userRef.put("2413","T0267");
        userRef.put("9336","T0268");
        userRef.put("9711","T0269");
        userRef.put("9421","T0270");
        userRef.put("9880","T0271");
        userRef.put("9897","T0272");
        userRef.put("9918","T0273");
        userRef.put("9919","T0274");
        userRef.put("2811","T0276");
        userRef.put("5784","T0277");
        userRef.put("5993","T0278");
        userRef.put("8213","T0279");
        userRef.put("8642","T0280");
        userRef.put("8921","T0281");
        userRef.put("9822","T0282");
        userRef.put("2155","T0284");
        userRef.put("328","T0285");
        userRef.put("3608","T0286");
        userRef.put("412","T0287");
        userRef.put("5052","T0288");
        userRef.put("5054","T0289");
        userRef.put("5065","T0290");
        userRef.put("5912","T0291");
        userRef.put("6105","T0292");
        userRef.put("6108","T0293");
        userRef.put("7056","T0294");
        userRef.put("7206","T0295");
        userRef.put("7394","T0296");
        userRef.put("7402","T0297");
        userRef.put("7511","T0298");
        userRef.put("7512","T0299");
        userRef.put("7567","T0300");
        userRef.put("7641","T0301");
        userRef.put("7714","T0302");
        userRef.put("7718","T0303");
        userRef.put("7725","T0304");
        userRef.put("7742","T0305");
        userRef.put("7938","T0306");
        userRef.put("819","T0307");
        userRef.put("8362","T0308");
        userRef.put("8363","T0309");
        userRef.put("8411","T0310");
        userRef.put("8661","T0311");
        userRef.put("8822","T0312");
        userRef.put("9022","T0313");
        userRef.put("9062","T0314");
        userRef.put("9140","T0315");
        userRef.put("9207","T0316");
        userRef.put("9221","T0317");
        userRef.put("9264","T0318");
        userRef.put("9290","T0319");
        userRef.put("9314","T0320");
        userRef.put("9326","T0321");
        userRef.put("9383","T0322");
        userRef.put("9394","T0323");
        userRef.put("9431","T0324");
        userRef.put("9814","T0325");
        userRef.put("9846","T0326");
        userRef.put("9849","T0327");
        userRef.put("9851","T0328");
        userRef.put("9853","T0329");
        userRef.put("9854","T0330");
        userRef.put("9855","T0331");
        userRef.put("9856","T0332");
        userRef.put("9857","T0333");
        userRef.put("9858","T0334");
        userRef.put("9859","T0335");
        userRef.put("9860","T0336");
        userRef.put("9861","T0337");
        userRef.put("9862","T0338");
        userRef.put("9863","T0339");
        userRef.put("9864","T0340");
        userRef.put("9865","T0341");
        userRef.put("9866","T0342");
        userRef.put("9867","T0343");
        userRef.put("9868","T0344");
        userRef.put("9892","T0345");
        userRef.put("9893","T0346");
        userRef.put("9895","T0347");
        userRef.put("9896","T0348");
        userRef.put("9903","T0349");
        userRef.put("9904","T0350");
        userRef.put("9905","T0351");
        userRef.put("7653","T0353");
        userRef.put("7654","T0354");
        userRef.put("7668","T0355");
        userRef.put("7679","T0356");
        userRef.put("7687","T0357");
        userRef.put("7803","T0358");
        userRef.put("7935","T0359");
        userRef.put("8657","T0360");
        userRef.put("8658","T0361");
        userRef.put("8687","T0362");
        userRef.put("8753","T0363");
        userRef.put("8820","T0364");
        userRef.put("8909","T0365");
        userRef.put("8946","T0366");
        userRef.put("9061","T0367");
        userRef.put("7178","T0369");
        userRef.put("7365","T0370");
        userRef.put("9742","T0371");
        userRef.put("9800","T0372");
        userRef.put("9801","T0373");
        userRef.put("9847","T0374");
        userRef.put("9898","T0375");
        userRef.put("9899","T0376");
        userRef.put("7854","T0378");
        userRef.put("9815","T0379");
        userRef.put("9410","T0380");
        userRef.put("9205","T0381");
        userRef.put("9358","T0382");
        userRef.put("9385","T0383");
        userRef.put("8988","T0385");
        userRef.put("5072","T0393");
        userRef.put("9409","T0394");
        userRef.put("256","T0395");
        userRef.put("2619","T0396");
        userRef.put("3315","T0397");
        userRef.put("5102","T0398");
        userRef.put("3610","T0399");
        userRef.put("5564","T0400");
        userRef.put("8763","T0401");
        userRef.put("9131","T0402");
        userRef.put("9354","T0403");
        userRef.put("9355","T0404");
        userRef.put("9337","T0405");
        userRef.put("9376","T0406");
        userRef.put("7184","T0407");
        userRef.put("9828","T0408");
        userRef.put("5142","T0410");
        userRef.put("6090","T0411");
        userRef.put("8559","T0412");
        userRef.put("8845","T0413");
        userRef.put("8858","T0414");
        userRef.put("9030","T0415");
        userRef.put("9312","T0416");
        userRef.put("9341","T0417");
        userRef.put("5302","T0418");
        userRef.put("2174","T0421");
        userRef.put("5175","T0422");
        userRef.put("7328","T0423");
        userRef.put("9774","T0424");
        userRef.put("9085","T0427");
        userRef.put("9364","T0428");
        userRef.put("2139","T0429");
        userRef.put("2629","T0430");
        userRef.put("2974","T0432");
        userRef.put("729","T0433");
        userRef.put("618","T0434");
        userRef.put("643","T0435");
        userRef.put("9408","T0438");
        userRef.put("9028","T0439");
        userRef.put("2479","T0441");
        userRef.put("5634","T0442");
        userRef.put("9024","T0443");
        userRef.put("9127","T0444");
        userRef.put("9134","T0445");
        userRef.put("9159","T0446");
        userRef.put("9407","T0447");
        userRef.put("9802","T0448");
        userRef.put("9869","T0449");
        userRef.put("2948","T0451");
        userRef.put("2968","T0452");
        userRef.put("5134","T0453");
        userRef.put("5154","T0454");
        userRef.put("5157","T0455");
        userRef.put("582","T0456");
        userRef.put("5933","T0457");
        userRef.put("6130","T0458");
        userRef.put("8147","T0459");
        userRef.put("8271","T0460");
        userRef.put("8282","T0461");
        userRef.put("8310","T0462");
        userRef.put("8401","T0463");
        userRef.put("8596","T0464");
        userRef.put("8760","T0465");
        userRef.put("8847","T0466");
        userRef.put("8935","T0467");
        userRef.put("9032","T0468");
        userRef.put("9042","T0469");
        userRef.put("9346","T0470");
        userRef.put("1777","T0472");
        userRef.put("1982","T0473");
        userRef.put("3542","T0474");
        userRef.put("5064","T0475");
        userRef.put("5070","T0476");
        userRef.put("5079","T0477");
        userRef.put("5094","T0478");
        userRef.put("5095","T0479");
        userRef.put("5128","T0480");
        userRef.put("5136","T0481");
        userRef.put("5150","T0482");
        userRef.put("5153","T0483");
        userRef.put("5335","T0484");
        userRef.put("5480","T0485");
        userRef.put("5542","T0486");
        userRef.put("6012","T0487");
        userRef.put("6902","T0488");
        userRef.put("8003","T0489");
        userRef.put("8309","T0490");
        userRef.put("8313","T0491");
        userRef.put("8590","T0492");
        userRef.put("8592","T0493");
        userRef.put("8611","T0494");
        userRef.put("9297","T0495");
        userRef.put("9298","T0496");
        userRef.put("9349","T0497");
        userRef.put("9397","T0498");
        userRef.put("9415","T0499");
        userRef.put("9424","T0500");
        userRef.put("9776","T0501");
        userRef.put("9803","T0502");
        userRef.put("3602","T0504");
        userRef.put("3723","T0505");
        userRef.put("5106","T0506");
        userRef.put("5152","T0507");
        userRef.put("5155","T0508");
        userRef.put("5164","T0509");
        userRef.put("5258","T0510");
        userRef.put("5575","T0511");
        userRef.put("5696","T0512");
        userRef.put("5700","T0513");
        userRef.put("6084","T0514");
        userRef.put("7753","T0515");
        userRef.put("8161","T0516");
        userRef.put("8307","T0517");
        userRef.put("8579","T0518");
        userRef.put("8888","T0519");
        userRef.put("9165","T0520");
        userRef.put("9295","T0521");
        userRef.put("9366","T0522");
        userRef.put("9413","T0523");
        userRef.put("9870","T0524");
        userRef.put("1779","T0526");
        userRef.put("239","T0527");
        userRef.put("5126","T0528");
        userRef.put("5132","T0529");
        userRef.put("5158","T0530");
        userRef.put("5162","T0531");
        userRef.put("5361","T0532");
        userRef.put("5694","T0533");
        userRef.put("6080","T0534");
        userRef.put("7240","T0535");
        userRef.put("7772","T0536");
        userRef.put("8390","T0537");
        userRef.put("8569","T0538");
        userRef.put("8597","T0539");
        userRef.put("8759","T0540");
        userRef.put("8949","T0541");
        userRef.put("9160","T0542");
        userRef.put("9371","T0543");
        userRef.put("9372","T0544");
        userRef.put("9395","T0545");
        userRef.put("234","T0547");
        userRef.put("5093","T0548");
        userRef.put("5176","T0549");
        userRef.put("5561","T0550");
        userRef.put("5577","T0551");
        userRef.put("7237","T0552");
        userRef.put("7636","T0553");
        userRef.put("9151","T0554");
        userRef.put("9377","T0555");
        userRef.put("9712","T0556");
        userRef.put("9871","T0557");
        userRef.put("9874","T0558");
        userRef.put("3323","T0560");
        userRef.put("5087","T0561");
        userRef.put("8343","T0562");
        userRef.put("8471","T0563");
        userRef.put("8634","T0564");
        userRef.put("8688","T0565");
        userRef.put("8926","T0566");
        userRef.put("8977","T0567");
        userRef.put("9154","T0568");
        userRef.put("9225","T0569");
        userRef.put("9342","T0570");
        userRef.put("9375","T0571");
        userRef.put("6360","T0572");
        userRef.put("9769","T0573");
        userRef.put("9287","T0574");
        userRef.put("9285","T0575");
        userRef.put("9831","T0576");
        userRef.put("9286","T0577");
        userRef.put("9273","T0578");
        userRef.put("9254","T0579");
        userRef.put("9434","T0580");
        userRef.put("9277","T0581");
        userRef.put("无","T0582");
        userRef.put("9852","T0583");
        userRef.put("9917","T0584");
        userRef.put("7649","T0585");
        userRef.put("8127","T0586");
        userRef.put("9654","T0587");
        userRef.put("3705","T0588");
        userRef.put("8123","T0589");
        userRef.put("6100","T0590");
        userRef.put("3563","T0591");
        userRef.put("6040","T0592");
        userRef.put("2131","T0593");
        userRef.put("6275","T0594");
        userRef.put("6885","T0595");
        userRef.put("9516","T0596");
        userRef.put("5824","T0597");
        userRef.put("5347","T0598");
        userRef.put("9480","T0599");
        userRef.put("9450","T0600");
        userRef.put("9500","T0601");
        userRef.put("9494","T0602");
        userRef.put("541","T0603");
        userRef.put("5603","T0604");
        userRef.put("7199","T0605");
        userRef.put("5774","T0606");
        userRef.put("8119","T0608");
        userRef.put("5412","T0609");
        userRef.put("2797","T0610");
        userRef.put("120","T0611");
        userRef.put("9515","T0612");
        userRef.put("9338","T0613");
        userRef.put("9357","T0614");
        userRef.put("9692","T0615");
        userRef.put("9693","T0616");
        userRef.put("9694","T0617");
        userRef.put("9695","T0618");
        userRef.put("2502","T0619");
        userRef.put("9420","T0620");
        userRef.put("9812","T0621");
        userRef.put("2330","T0622");
        userRef.put("2331","T0623");
        userRef.put("2890","T0624");
        userRef.put("6073","T0625");
        userRef.put("8863","T0626");
        userRef.put("7730","T0627");
        userRef.put("5558","T0628");
        userRef.put("8524","T0629");
        userRef.put("9689","T0630");
        userRef.put("9690","T0631");
        userRef.put("9691","T0632");
        userRef.put("9697","T0633");
        userRef.put("9020","T0634");
        userRef.put("8010","T0635");
        userRef.put("9839","T0636");
        userRef.put("3130","T0637");
        userRef.put("9469","T0638");
        userRef.put("8133","T0639");
        userRef.put("9923","T0640");
        userRef.put("9850","T0641");
        userRef.put("8137","T0642");
        userRef.put("8441","T0643");
        userRef.put("2625","T0644");
        userRef.put("6474","T0645");
        userRef.put("6078","T0646");
        userRef.put("8686","T0647");
        userRef.put("9119","T0648");
        userRef.put("7310","T0649");
        userRef.put("7348","T0650");
        userRef.put("9115","T0651");
        userRef.put("8296","T0652");
        userRef.put("8705","T0653");
        userRef.put("9329","T0654");
        userRef.put("9330","T0655");
        userRef.put("9331","T0656");
        userRef.put("9333","T0657");
        userRef.put("9911","T0658");
        userRef.put("9744","T0659");
        userRef.put("9745","T0660");
        userRef.put("9209","T0661");
        userRef.put("8520","T0662");
        userRef.put("9615","T0663");
        userRef.put("9616","T0664");
        userRef.put("9617","T0665");
        userRef.put("9725","T0666");
        userRef.put("9726","T0667");
        userRef.put("9727","T0668");
        userRef.put("9728","T0669");
        userRef.put("9729","T0670");
        userRef.put("9730","T0671");
        userRef.put("9791","T0672");
        userRef.put("9714","T0673");
        userRef.put("9741","T0674");
        userRef.put("9798","T0675");
        userRef.put("5217","T0676");
        userRef.put("9648","T0677");
        userRef.put("9622","T0678");
        userRef.put("9646","T0679");
        userRef.put("9626","T0680");
        userRef.put("9627","T0681");
        userRef.put("9628","T0682");
        userRef.put("9638","T0683");
        userRef.put("9639","T0684");
        userRef.put("9641","T0685");
        userRef.put("9632","T0686");
        userRef.put("9634","T0687");
        userRef.put("9635","T0688");
        userRef.put("9636","T0689");
        userRef.put("9637","T0690");
        userRef.put("3562","T0691");
        userRef.put("7414","T0692");
        userRef.put("8219","T0693");
        userRef.put("9688","T0694");
        userRef.put("1238","T0695");
        userRef.put("393","T0696");
        userRef.put("9387","T0697");
        userRef.put("9934","T0698");
        userRef.put("9933","T0699");
        userRef.put("3087","T0700");
        userRef.put("3524","T0701");
        userRef.put("8297","T0702");
        userRef.put("8676","T0703");
        userRef.put("8452","T0704");
        userRef.put("8318","T0705");
        userRef.put("284","T0706");
        userRef.put("9389","T0707");
        userRef.put("722","T0708");
        userRef.put("2303","T0709");
        userRef.put("1031","T0710");
        userRef.put("1335","T0711");
        userRef.put("3065","T0712");
        userRef.put("8543","T0713");
        userRef.put("9931","T0714");
        userRef.put("9932","T0715");
        userRef.put("2404","T0716");
        userRef.put("9070","T0717");
        userRef.put("190","T0718");
        userRef.put("1029","T0719");
        userRef.put("1877","T0720");
        userRef.put("2572","T0721");
        userRef.put("144","T0722");
        userRef.put("6390","T0723");
        userRef.put("6385","T0724");
        userRef.put("9574","T0725");
        userRef.put("9575","T0726");
        userRef.put("9577","T0727");
        userRef.put("9578","T0728");
        userRef.put("9579","T0729");
        userRef.put("9570","T0730");
        userRef.put("9581","T0731");
        userRef.put("9573","T0732");
        userRef.put("3089","T0733");
        userRef.put("518","T0734");
        userRef.put("6511","T0735");
        userRef.put("2846","T0736");
        userRef.put("7903","T0737");
        userRef.put("5765","T0738");
        userRef.put("5761","T0739");
        userRef.put("4142","T0740");
        userRef.put("9929","T0741");
        userRef.put("5791","T0742");
        userRef.put("9576","T0743");
        userRef.put("9571","T0744");
        userRef.put("9572","T0745");
        userRef.put("1305","T0746");
        userRef.put("2762","T0747");
        userRef.put("3540","T0748");
        userRef.put("4064","T0749");
        userRef.put("9390","T0750");
        userRef.put("2184","T0751");
        userRef.put("2220","T0752");
        userRef.put("2308","T0753");
        userRef.put("7889","T0754");
        userRef.put("6491","T0755");
        userRef.put("694","T0756");
        userRef.put("8529","T0757");
        userRef.put("3085","T0758");
        userRef.put("3060","T0759");
        userRef.put("6267","T0760");
        userRef.put("101","T0761");
        userRef.put("6401","T0762");
        userRef.put("193","T0763");
        userRef.put("5807","T0764");
        userRef.put("8531","T0765");
        userRef.put("9806","T0766");
        userRef.put("8544","T0767");
        userRef.put("3882","T0768");
        userRef.put("9699","T0769");
        userRef.put("9363","T0770");
        userRef.put("1410","T0771");
        userRef.put("1564","T0772");
        userRef.put("5741","T0773");
        userRef.put("5742","T0774");
        userRef.put("5743","T0775");
        userRef.put("5746","T0776");
        userRef.put("5747","T0777");
        userRef.put("7591","T0778");
        userRef.put("2918","T0779");
        userRef.put("224","T0780");
        userRef.put("2200","T0781");
        userRef.put("2402","T0782");
        userRef.put("2844","T0783");
        userRef.put("3576","T0784");
        userRef.put("3084","T0785");
        userRef.put("8274","T0786");
        userRef.put("7902","T0787");
        userRef.put("9805","T0788");
        userRef.put("7906","T0789");
        userRef.put("3067","T0790");
        userRef.put("3673","T0791");
        userRef.put("903","T0792");
        userRef.put("3527","T0793");
        userRef.put("2655","T0794");
        userRef.put("3163","T0795");
        userRef.put("2304","T0796");
        userRef.put("7275","T0797");
        userRef.put("6917","T0798");
        userRef.put("3584","T0799");
        userRef.put("9180","T0800");
        userRef.put("9912","T0801");
        userRef.put("7299","T0802");
        userRef.put("3363","T0803");
        userRef.put("4001","T0804");
        userRef.put("9545","T0805");
        userRef.put("9541","T0806");
        userRef.put("9554","T0807");
        userRef.put("9553","T0808");
        userRef.put("9555","T0809");
        userRef.put("9531","T0810");
        userRef.put("9529","T0811");
        userRef.put("9533","T0812");
        userRef.put("9530","T0813");
        userRef.put("9532","T0814");
        userRef.put("9841","T0815");
        userRef.put("8691","T0816");
        userRef.put("8747","T0817");
        userRef.put("8748","T0818");
        userRef.put("9179","T0819");
        userRef.put("9546","T0820");
        userRef.put("8701","T0821");
        userRef.put("8692","T0822");
        userRef.put("8702","T0823");
        userRef.put("9935","T0824");
        userRef.put("8472","T0825");
        userRef.put("8755","T0826");
        userRef.put("2856","T0827");
        userRef.put("9121","T0828");
        userRef.put("7336","T0829");
        userRef.put("7657","T0830");
        userRef.put("9122","T0831");
        userRef.put("7242","T0832");
        userRef.put("6935","T0833");
        userRef.put("2735","T0836");
        userRef.put("4023","T0837");
        userRef.put("1621","T0838");
        userRef.put("185","T0839");
        userRef.put("3321","T0840");
        userRef.put("3513","T0841");
        userRef.put("3586","T0842");
        userRef.put("4041","T0843");
        userRef.put("4043","T0844");
        userRef.put("6097","T0845");
        userRef.put("6208","T0846");
        userRef.put("6210","T0847");
        userRef.put("6211","T0848");
        userRef.put("8015","T0849");
        userRef.put("8326","T0850");
        userRef.put("8669","T0851");
        userRef.put("8723","T0852");
        userRef.put("8854","T0853");
        userRef.put("8890","T0854");
        userRef.put("9049","T0855");
        userRef.put("9112","T0856");
        userRef.put("9117","T0857");
        userRef.put("9137","T0858");
        userRef.put("9152","T0859");
        userRef.put("9403","T0860");
        userRef.put("9429","T0861");
        userRef.put("9879","T0862");
        userRef.put("5026","T0863");
        userRef.put("7364","T0864");
        userRef.put("8418","T0865");
        userRef.put("8953","T0866");
        userRef.put("9037","T0867");
        userRef.put("9102","T0868");
        userRef.put("9105","T0869");
        userRef.put("9036","T0870");
        userRef.put("9220","T0871");
        userRef.put("9665","T0872");
        userRef.put("9666","T0873");
        userRef.put("2281","T0874");
        userRef.put("9145","T0875");
        userRef.put("9309","T0876");
        userRef.put("8919","T0877");
        userRef.put("9143","T0878");
        userRef.put("9211","T0879");
        userRef.put("9212","T0880");
        userRef.put("9213","T0881");
        userRef.put("9228","T0882");
        userRef.put("9289","T0883");
        userRef.put("9291","T0884");
        userRef.put("9299","T0885");
        userRef.put("9306","T0886");
        userRef.put("9307","T0887");
        userRef.put("9308","T0888");
        userRef.put("9381","T0889");
        userRef.put("9594","T0890");
        userRef.put("9779","T0891");
        userRef.put("7721","T0892");
        userRef.put("9717","T0893");
        userRef.put("9816","T0894");
        userRef.put("7185","T0895");
        userRef.put("9174","T0896");
        userRef.put("9210","T0897");
        userRef.put("9840","T0898");
        userRef.put("9452","T0899");
        userRef.put("9496","T0900");
        userRef.put("9497","T0901");
        userRef.put("9498","T0902");
        userRef.put("9499","T0903");
        userRef.put("5260","T0904");
        userRef.put("6550","T0905");
        userRef.put("8525","T0906");
        userRef.put("7498","T0907");
        userRef.put("7830","T0908");
        userRef.put("8806","T0909");
        userRef.put("5190","T0910");
        userRef.put("6051","T0911");
        userRef.put("8410","T0912");
        userRef.put("8667","T0913");
        userRef.put("9104","T0914");
        userRef.put("9171","T0915");
        userRef.put("9218","T0916");
        userRef.put("9468","T0917");
        userRef.put("9908","T0918");
        userRef.put("9454","T0919");
        userRef.put("9455","T0920");
        userRef.put("9466","T0921");
        userRef.put("9467","T0922");
        userRef.put("9470","T0923");
        userRef.put("9471","T0924");
        userRef.put("9472","T0925");
        userRef.put("9473","T0926");
        userRef.put("9474","T0927");
        userRef.put("9475","T0928");
        userRef.put("9444","T0929");
        userRef.put("9462","T0930");
        userRef.put("9476","T0931");
        userRef.put("9477","T0932");
        userRef.put("9478","T0933");
        userRef.put("9842","T0934");
        userRef.put("492","T0935");
        userRef.put("7727","T0936");
        userRef.put("7737","T0937");
        userRef.put("9380","T0938");
        userRef.put("8583","T0939");
        userRef.put("8907","T0940");
        userRef.put("8947","T0941");
        userRef.put("9033","T0942");
        userRef.put("9138","T0943");
        userRef.put("7987","T0944");
        userRef.put("8799","T0945");
        userRef.put("9123","T0946");
        userRef.put("9698","T0947");
        userRef.put("5876","T0948");
        userRef.put("8638","T0949");
        userRef.put("8277","T0951");
        userRef.put("353","T0952");
        userRef.put("9172","T0953");
        userRef.put("1874","T0954");
        userRef.put("2528","T0955");
        userRef.put("6265","T0956");
        userRef.put("9907","T0957");
        userRef.put("9652","T0958");
        userRef.put("9271","T0959");
        userRef.put("无","T0960");
        userRef.put("无","T0961");
        userRef.put("无","T0962");
        userRef.put("无","T0963");
        userRef.put("无","T0964");
        userRef.put("9275","T0970");
        userRef.put("9563","T0971");
        userRef.put("9559","T0972");
        userRef.put("9244","T0973");
        userRef.put("9246","T0974");
        userRef.put("9561","T0975");
        userRef.put("无","T0976");
        userRef.put("9243","T0977");
        userRef.put("9810","T0978");
        userRef.put("9547","T0979");
        userRef.put("无","T0980");
        userRef.put("9556","T0981");
        userRef.put("9560","T0982");
        userRef.put("无","T0983");
        userRef.put("9438","T0984");
        userRef.put("9433","T0985");
        userRef.put("9544","T0986");
        userRef.put("9543","T0987");
        userRef.put("9566","T0988");
        userRef.put("9565","T0989");
        userRef.put("9542","T0990");
        userRef.put("9540","T0991");
        userRef.put("9537","T0992");
        userRef.put("9536","T0993");
        userRef.put("9539","T0994");
        userRef.put("9833","T0995");
        userRef.put("9834","T0996");
        userRef.put("9835","T0997");
        userRef.put("9837","T0998");
        userRef.put("9768","T0999");
        userRef.put("9251","T1000");
        userRef.put("9252","T1001");
        userRef.put("9770","T1002");
        userRef.put("9883","T1003");
        userRef.put("9884","T1004");
        userRef.put("9267","T1005");
        userRef.put("8793","T1006");
        userRef.put("8466","T1007");
        userRef.put("5236","T1008");
        userRef.put("8839","T1009");
        userRef.put("9106","T1010");
        userRef.put("5310","T1011");
        userRef.put("6119","T1012");
        userRef.put("8837","T1013");
        userRef.put("4085","T1014");
        userRef.put("9910","T1015");
        userRef.put("9927","T1016");
        userRef.put("9177","T1017");
        userRef.put("9824","T1018");
        userRef.put("8968","T1019");
        userRef.put("9823","T1020");
        userRef.put("9890","T1021");
        userRef.put("9825","T1022");
        userRef.put("5013","T1023");
        userRef.put("无","T1024");
        userRef.put("576","T1025");
        userRef.put("3672","T1026");
        userRef.put("927","T1027");
        userRef.put("244","T1028");
        userRef.put("9568","T1029");
        userRef.put("9569","T1030");
        userRef.put("9524","T1031");
        userRef.put("9525","T1032");
        userRef.put("9687","T1033");
        userRef.put("9688","T1034");
        userRef.put("9629","T1035");
        userRef.put("9630","T1036");
        userRef.put("9620","T1037");
        userRef.put("9621","T1038");
        userRef.put("9624","T1039");
        userRef.put("9625","T1040");
        userRef.put("9651","T1041");
        userRef.put("9481","T1042");
        userRef.put("9442","T1043");
        userRef.put("9443","T1044");
        userRef.put("9464","T1045");
        userRef.put("9446","T1046");
        userRef.put("9447","T1047");
        userRef.put("9616","T1048");
        userRef.put("9617","T1049");
        userRef.put("9584","T1050");
        userRef.put("9585","T1051");
        userRef.put("无","T1052");
        userRef.put("9517","T1053");
        userRef.put("9234","T1054");
        userRef.put("9236","T1055");
        userRef.put("9231","T1056");
        userRef.put("9247","T1057");
        userRef.put("9256","T1058");
        userRef.put("9255","T1059");
        userRef.put("9253","T1060");
        userRef.put("9219","T1061");
        userRef.put("9819","T1062");
        userRef.put("9242","T1063");
        userRef.put("9894","T1064");
        userRef.put("9900","T1065");
        userRef.put("2720","T1066");
        userRef.put("488","T1067");
        userRef.put("7366","T1068");
        userRef.put("8264","T1069");
        userRef.put("9782","T1070");
        userRef.put("9886","T1071");
        userRef.put("8833","T1072");
        userRef.put("9319","T1073");
        userRef.put("8842","T1074");
        userRef.put("9334","T1075");
        userRef.put("9818","T1076");
        userRef.put("9930","T1077");
        userRef.put("9916","T1078");
        userRef.put("8445","T1079");
        userRef.put("9820","T1080");
        userRef.put("9826","T1081");
        userRef.put("9891","T1082");
        userRef.put("9920","T1083");
        userRef.put("9928","T1084");
        userRef.put("8011","T1085");

    }
}