package com.junit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FallTruckExportHttpTest {
    private static final String LOGIN_PAGE_URL = "https://fall.wlhn.com/fallapp-main-welcome/ext/pLogin?areaId=88a93c7727944754ab2e1ff01934cf70";
    private static final String LOGIN_ACTION_URL = "https://fall.wlhn.com/fallapp-main-welcome/pcAccount/pLogin";
    private static final String EXPORT_LIST_DATA_URL = "https://fall.wlhn.com/fallapp-child-export/ExcelExport/listData";
    private static final String EXPORT_PROGRESS_URL = "https://fall.wlhn.com/fallapp-child-export/ExcelExport/getProgress";
    private static final String RESOURCE_BASE_PATH = "https://sping-cloud-fall.oss-cn-shanghai.aliyuncs.com/wlhn";
    private static final String USERNAME = "008驮龙";
    private static final String PASSWORD = "123456";

    private static final String EXPORT_FIELDS =
            "payable_check_state,bill_date,departure_date,bill_state,state,departure_batch,truck_vehicle_length," +
            "flight_number,trailer_vehicle_number,premium_amount,driver_name,driver_mobile,total_order_count," +
            "handling_mode,consignment_station_name,receiving_station_name,receiving_station_name_line,premium_amount1," +
            "premium_amount2,expected_arrival_date,actual_arrival_date,total_packing_quantity,total_weight,total_volume," +
            "total_consignee_arrive_pay,total_consigner_spot_pay,total_consigner_moon_pay,total_freight," +
            "total_business_commission,total_business_sale_spot,total_business_sale_wait,total_commission," +
            "total_commission_wait,total_commission_spot,total_receiving_cost,total_receiving_charge,total_else_cost," +
            "total_send_charge,charged_packing_quantity,charged_weight,total_premium_amount,payable_amount," +
            "driver_spot_pay_amount,unloading_station_payment,charged_volume,end_branch_id.name,pay_channel_type," +
            "pay_mode,pay_amount,pay_state,pay_date,driver_collection_freight,driver_collection_goods_payment," +
            "total_driver_warehouse_entry_fee,total_agent_warehouse_entry_fee,credit_amount,unloading_payable_amount," +
            "total_less_commission_freight,total_net_freight,unloading_remaining_amount,total_transit_charge," +
            "total_basic_freight_amount,total_preferential_amount,total_basic_freight_charges,total_actual_freight_amount," +
            "truck_carrier_account,truck_carrier_name,contact_man,contact_way,is_charging_create,consol_bill_no,app_area," +
            "create_user.user_nickname,is_insurance,insurance_company_account,insurance_company_name," +
            "insurance_product_id.name,insured_amount,insurance_rate,Insurance_state,Insurance_date," +
            "truck_load_unload_charge,truck_business_cost,charge_state,start_branch_id.name,charge_user.user_nickname," +
            "create_date,no,remark,charge_date,is_choose,finished.truck_paid_amount_finished,total_consigner_receipt_pay," +
            "insured_amount1,insured_amount2,total_order_else_cost,total_consignee_moon_pay,total_landing_cost," +
            "total_branch_pickup_fee,driver_commission,driver_commission_spot,driver_commission_wait," +
            "choose_spot_pay_amount,choose_moon_pay_amount,choose_receipt_pay_amount,choose_collection_freight," +
            "choose_collection_goods_payment,choose_next_transit_charge,choose_next_send_charge,choose_next_other_amount," +
            "choose_profit,choose_cash_payment,choose_cost,customs_broker_name,customs_clearing_name,container_self_code," +
            "container_number,container_size,sea_loading_date,sea_shipping_date,sea_arrival_date," +
            "customs_declaration_fee,customs_clearance_fee,containe_profit_amount,truck_carrier_id,driver_two_name,id";

    private static final String EXPORT_WHERES =
            "[{\"field\":\"type\",\"op\":\"=\",\"value\":\"120\"}," +
            "{\"field\":\"receiving_mode\",\"op\":\"=\",\"value\":\"2\"}," +
            "{\"field\":\"root_member_area\",\"op\":\"=\",\"value\":\"88a93c7727944754ab2e1ff01934cf70\"}," +
            "{\"field\":\"app_area\",\"op\":\"=\",\"value\":\"65925cc768a442ae8bac6fa50200f1e3\"}," +
            "{\"field\":\"bill_date\",\"op\":\">=\",\"value\":\"2026-05-06 00:00:00\"}," +
            "{\"field\":\"bill_date\",\"op\":\"<=\",\"value\":\"2026-05-12 23:59:59\"}," +
            "{\"field\":\"is_choose\",\"op\":\"=\",\"value\":\"0\"}]";

    private static final String COLUMN_CONFIG =
            "payable_check_state|是否费用确认\n" +
            "bill_date|配载日期\n" +
            "departure_date|发车时间\n" +
            "bill_state|单据状态\n" +
            "state|配载状态\n" +
            "departure_batch|车次\n" +
            "truck_vehicle_length|车长\n" +
            "flight_number|车号\n" +
            "trailer_vehicle_number|车挂号\n" +
            "premium_amount|整车货保\n" +
            "driver_name|司机姓名\n" +
            "driver_mobile|司机手机\n" +
            "total_order_count|运单数\n" +
            "handling_mode|装卸方式\n" +
            "consignment_station_name|始发站\n" +
            "receiving_station_name|终到站\n" +
            "receiving_station_name_line|途经到站\n" +
            "premium_amount1|货保保费\n" +
            "premium_amount2|森马保费\n" +
            "expected_arrival_date|预计到达时间\n" +
            "actual_arrival_date|实际到达时间\n" +
            "total_packing_quantity|总件数\n" +
            "total_weight|总重量\n" +
            "total_volume|总体积\n" +
            "total_consignee_arrive_pay| ￥    到付\n" +
            "total_consigner_spot_pay| ￥    现付\n" +
            "total_consigner_moon_pay| ￥    月结\n" +
            "total_freight|合计运费\n" +
            "total_business_commission|业务费\n" +
            "total_business_sale_spot|付业务费\n" +
            "total_business_sale_wait|欠业务费\n" +
            "total_commission|佣金\n" +
            "total_commission_wait|欠佣金\n" +
            "total_commission_spot|付佣金\n" +
            "total_receiving_cost|转入代办费\n" +
            "total_receiving_charge|取货费\n" +
            "total_else_cost|其他费\n" +
            "total_send_charge|送到费\n" +
            "charged_packing_quantity|计费件数\n" +
            "charged_weight|计费重量\n" +
            "total_premium_amount|保险费\n" +
            "payable_amount|大车运费\n" +
            "driver_spot_pay_amount|司机现付\n" +
            "unloading_station_payment|卸货站到付\n" +
            "charged_volume|计费体积\n" +
            "pay_channel_type|支付渠道\n" +
            "end_branch_id.name|目的网点\n" +
            "pay_mode|支付方式\n" +
            "pay_amount|支付金额\n" +
            "pay_state|支付状态\n" +
            "pay_date|支付时间\n" +
            "driver_collection_freight|司机代收运费\n" +
            "driver_collection_goods_payment|司机代收货款\n" +
            "total_driver_warehouse_entry_fee|司机垫付进仓费\n" +
            "total_agent_warehouse_entry_fee|站点垫付进仓费\n" +
            "credit_amount|尚欠承运金额\n" +
            "unloading_payable_amount|卸货站费用\n" +
            "total_less_commission_freight|减回扣运费\n" +
            "total_net_freight|净运费\n" +
            "total_transit_charge|中转费\n" +
            "unloading_remaining_amount|卸货站余下\n" +
            "total_basic_freight_amount|基本运费\n" +
            "total_preferential_amount|优惠金额\n" +
            "total_basic_freight_charges|基础运费收入\n" +
            "total_actual_freight_amount|实际运费合计\n" +
            "truck_carrier_account|承运商卡号\n" +
            "truck_carrier_name|承运商名称\n" +
            "contact_man|联系人\n" +
            "contact_way|联系方式\n" +
            "is_charging_create|是否已计费\n" +
            "consol_bill_no|主单号\n" +
            "app_area|所属域\n" +
            "create_user.user_nickname|创建人\n" +
            "is_insurance|是否投保\n" +
            "insurance_company_account|保险公司卡号\n" +
            "insurance_company_name|保险公司名称\n" +
            "insurance_product_id.name|险种\n" +
            "insured_amount|保额\n" +
            "insurance_rate|保险费率(%)\n" +
            "Insurance_state|投保状态\n" +
            "Insurance_date|投保时间\n" +
            "truck_load_unload_charge|本车装车费\n" +
            "truck_business_cost|本车业务支出\n" +
            "charge_state|是否记账\n" +
            "charge_user.user_nickname|记账人\n" +
            "start_branch_id.name|出发网点\n" +
            "remark|备注\n" +
            "create_date|创建日期\n" +
            "no|配载装车单号\n" +
            "charge_date|记账时间\n" +
            "is_choose|是否分流\n" +
            "finished.truck_paid_amount_finished|司机现付核销\n" +
            "total_consigner_receipt_pay|回单付合计\n" +
            "insured_amount1|货保保额\n" +
            "insured_amount2|森马保额\n" +
            "total_order_else_cost|运单其他费合计\n" +
            "total_consignee_moon_pay|到月结合计\n" +
            "total_landing_cost|代理落地费合计\n" +
            "total_branch_pickup_fee|网点接货费\n" +
            "driver_commission|分流佣金\n" +
            "driver_commission_spot|分流佣金现返\n" +
            "driver_commission_wait|分流佣金欠返\n" +
            "choose_spot_pay_amount|分流现付\n" +
            "choose_moon_pay_amount|分流月结\n" +
            "choose_receipt_pay_amount|分流回付\n" +
            "choose_collection_freight|分流到付\n" +
            "choose_collection_goods_payment|分流代收款\n" +
            "choose_next_transit_charge|分流中转费\n" +
            "choose_next_send_charge|分流送货费\n" +
            "choose_next_other_amount|分流其他费\n" +
            "choose_profit|分流利润\n" +
            "choose_cash_payment|分流现金收支\n" +
            "choose_cost|分流成本\n" +
            "customs_broker_name|报关公司名称\n" +
            "customs_clearing_name|清关公司名称\n" +
            "container_self_code|货柜自编号\n" +
            "container_number|货柜号\n" +
            "container_size|货柜尺寸\n" +
            "sea_loading_date|装柜日期\n" +
            "sea_shipping_date|开船日期\n" +
            "sea_arrival_date|到港日期\n" +
            "customs_declaration_fee|报关费\n" +
            "customs_clearance_fee|清关费\n" +
            "containe_profit_amount|本货柜利润";

    @Test
    public void shouldExportTruckExcelByHttp() throws Exception {
        Path output = exportTruckExcel();
        Assert.assertTrue("导出文件应存在", Files.exists(output));
        Assert.assertTrue("导出文件大小应大于 0", Files.size(output) > 0);
    }

    public static void main(String[] args) throws Exception {
        FallTruckExportHttpTest test = new FallTruckExportHttpTest();
        Path output = test.exportTruckExcel();
        System.out.println("exportFile=" + output.toAbsolutePath());
    }

    private Path exportTruckExcel() throws Exception {
        List<HttpCookie> cookies = new ArrayList<HttpCookie>();
        openLoginPage(cookies);
        String loginResult = login(cookies);
        if (!"true".equals(loginResult)) {
            throw new IllegalStateException("登录失败，返回值=" + loginResult);
        }

        JSONObject startExportResult = startExport(cookies);
        if (!startExportResult.getBooleanValue("success")) {
            throw new IllegalStateException("导出任务启动失败：" + startExportResult.toJSONString());
        }

        ProgressResult progressResult = waitExportReady(cookies);
        JSONArray rows = downloadExportRows(cookies, progressResult.downloadUrl);
        if (rows.isEmpty()) {
            throw new IllegalStateException("导出结果没有数据");
        }

        Path output = Paths.get("target", "fall_truck_export.xlsx");
        writeExcel(rows, output);
        return output;
    }

    private void openLoginPage(List<HttpCookie> cookies) throws Exception {
        HttpURLConnection connection = openConnection(LOGIN_PAGE_URL, "GET", cookies);
        try {
            int statusCode = connection.getResponseCode();
            if (statusCode < 200 || statusCode >= 400) {
                throw new IllegalStateException("登录页访问失败，status=" + statusCode);
            }
            storeCookies(connection, cookies);
            String body = readBody(connection);
            if (!body.contains("id=\"uname\"")) {
                throw new IllegalStateException("登录页返回异常，未找到账号输入框");
            }
        } finally {
            connection.disconnect();
        }
    }

    private String login(List<HttpCookie> cookies) throws Exception {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("uname", USERNAME);
        form.put("pwd", md5Hex(PASSWORD));
        form.put("vk", "");
        form.put("vcode", "");
        form.put("area", extractAreaId(LOGIN_PAGE_URL));
        form.put("source", LOGIN_PAGE_URL);
        form.put("operating_system", resolveOperatingSystem());
        form.put("app_code", "web");
        form.put("app_version_code", "0");
        form.put("mac", "");
        form.put("operating_system_version", "");
        form.put("operating_system_api_level", "");
        form.put("device_name", "");
        form.put("device_brand", "");
        form.put("device_model", "");

        HttpURLConnection connection = openConnection(LOGIN_ACTION_URL, "POST", cookies);
        try {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            writeFormBody(connection, form);
            int statusCode = connection.getResponseCode();
            if (statusCode < 200 || statusCode >= 400) {
                throw new IllegalStateException("登录请求失败，status=" + statusCode);
            }
            storeCookies(connection, cookies);
            return readBody(connection).trim();
        } finally {
            connection.disconnect();
        }
    }

    private JSONObject startExport(List<HttpCookie> cookies) throws Exception {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("$_type", "select");
        form.put("$_tableName", "wlhn_shop_transport_truck");
        form.put("$_askAction", "listAction");
        form.put("$_askView", "extListView");
        form.put("$_fields", EXPORT_FIELDS);
        form.put("$_wheres", EXPORT_WHERES);
        form.put("$_orders", "bill_date desc");
        form.put("$_language", "zh-cn");
        form.put("$_js_model_id", "");
        form.put("$_url", "/fallapp-child-wlhn-common/thirdPartyTruck/list");

        String body = requestExportListData(cookies, form, "POST");
        JSONObject result = JSON.parseObject(body);
        if (result.getBooleanValue("success")) {
            return result;
        }
        System.out.println("startExportResponse=" + body);
        throw new IllegalStateException("导出任务启动失败：" + body);
    }

    private String requestExportListData(List<HttpCookie> cookies, Map<String, String> form, String method) throws Exception {
        if ("GET".equalsIgnoreCase(method)) {
            String url = EXPORT_LIST_DATA_URL + "?" + buildFormData(form);
            HttpURLConnection connection = openConnection(url, "GET", cookies);
            try {
                int statusCode = connection.getResponseCode();
                if (statusCode < 200 || statusCode >= 400) {
                    throw new IllegalStateException("启动导出失败，status=" + statusCode + ", method=" + method);
                }
                storeCookies(connection, cookies);
                return readBody(connection);
            } finally {
                connection.disconnect();
            }
        }

        HttpURLConnection connection = openConnection(EXPORT_LIST_DATA_URL, "POST", cookies);
        try {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            writeFormBody(connection, form);
            int statusCode = connection.getResponseCode();
            if (statusCode < 200 || statusCode >= 400) {
                throw new IllegalStateException("启动导出失败，status=" + statusCode + ", method=" + method);
            }
            storeCookies(connection, cookies);
            return readBody(connection);
        } finally {
            connection.disconnect();
        }
    }

    private ProgressResult waitExportReady(List<HttpCookie> cookies) throws Exception {
        for (int i = 0; i < 120; i++) {
            HttpURLConnection connection = openConnection(EXPORT_PROGRESS_URL, "GET", cookies);
            try {
                int statusCode = connection.getResponseCode();
                if (statusCode < 200 || statusCode >= 400) {
                    throw new IllegalStateException("查询导出进度失败，status=" + statusCode);
                }
                storeCookies(connection, cookies);
                JSONObject response = JSON.parseObject(readBody(connection));
                if (!response.getBooleanValue("success")) {
                    throw new IllegalStateException("导出进度返回失败：" + response.toJSONString());
                }

                JSONObject data = response.getJSONObject("data");
                if (data == null) {
                    sleep(2000);
                    continue;
                }

                String absolutePath = data.getString("absolute_path");
                String path = data.getString("path");
                long totalBytes = data.getLongValue("total_bytes");
                long uploadBytes = data.getLongValue("upload_bytes");
                long totalTimes = data.getLongValue("totalTimes");
                long times = data.getLongValue("times");
                System.out.println("progress times=" + times + "/" + totalTimes + ", upload=" + uploadBytes + "/" + totalBytes);

                if (absolutePath != null && absolutePath.length() > 0) {
                    return new ProgressResult(absolutePath);
                }
                if (path != null && path.length() > 0) {
                    if (path.startsWith("http://") || path.startsWith("https://")) {
                        return new ProgressResult(path);
                    }
                    return new ProgressResult(RESOURCE_BASE_PATH + "/" + path);
                }
            } finally {
                connection.disconnect();
            }
            sleep(2000);
        }
        throw new IllegalStateException("等待导出文件超时");
    }

    private JSONArray downloadExportRows(List<HttpCookie> cookies, String downloadUrl) throws Exception {
        HttpURLConnection connection = openConnection(downloadUrl, "GET", cookies);
        try {
            int statusCode = connection.getResponseCode();
            if (statusCode < 200 || statusCode >= 400) {
                throw new IllegalStateException("下载导出数据失败，status=" + statusCode);
            }
            String body = readBody(connection);
            return JSON.parseArray(body);
        } finally {
            connection.disconnect();
        }
    }

    private void writeExcel(JSONArray rows, Path output) throws Exception {
        Files.createDirectories(output.getParent());
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            XSSFSheet sheet = workbook.createSheet("装货发车");
            List<ColumnDef> columns = parseColumns();

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns.get(i).text);
            }

            for (int i = 0; i < rows.size(); i++) {
                JSONObject rowData = rows.getJSONObject(i);
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < columns.size(); j++) {
                    ColumnDef column = columns.get(j);
                    Object value = rowData.get(column.name);
                    if (value == null) {
                        value = "";
                    }
                    row.createCell(j).setCellValue(String.valueOf(value));
                }
            }

            for (int i = 0; i < columns.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            OutputStream outputStream = new FileOutputStream(output.toFile());
            workbook.write(outputStream);
            outputStream.close();
        } finally {
            workbook.close();
        }
    }

    private List<ColumnDef> parseColumns() {
        List<ColumnDef> columns = new ArrayList<ColumnDef>();
        String[] lines = COLUMN_CONFIG.split("\\n");
        for (String line : lines) {
            String[] parts = line.split("\\|", 2);
            columns.add(new ColumnDef(parts[0], parts[1]));
        }
        return columns;
    }

    private HttpURLConnection openConnection(String url, String method, List<HttpCookie> cookies) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(20000);
        connection.setReadTimeout(120000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Referer", LOGIN_PAGE_URL);
        if (!cookies.isEmpty()) {
            connection.setRequestProperty("Cookie", joinCookies(cookies));
        }
        return connection;
    }

    private void writeFormBody(HttpURLConnection connection, Map<String, String> form) throws Exception {
        byte[] requestBody = buildFormData(form).getBytes(StandardCharsets.UTF_8);
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody);
        outputStream.flush();
        outputStream.close();
    }

    private void storeCookies(HttpURLConnection connection, List<HttpCookie> cookies) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<String> setCookies = headerFields.get("Set-Cookie");
        if (setCookies == null || setCookies.isEmpty()) {
            setCookies = headerFields.get("set-cookie");
        }
        if (setCookies == null) {
            return;
        }
        for (String header : setCookies) {
            List<HttpCookie> parsedCookies = HttpCookie.parse(header);
            for (HttpCookie parsedCookie : parsedCookies) {
                upsertCookie(cookies, parsedCookie);
            }
        }
    }

    private void upsertCookie(List<HttpCookie> cookies, HttpCookie newCookie) {
        for (int i = 0; i < cookies.size(); i++) {
            HttpCookie oldCookie = cookies.get(i);
            if (oldCookie.getName().equalsIgnoreCase(newCookie.getName())) {
                cookies.set(i, newCookie);
                return;
            }
        }
        cookies.add(newCookie);
    }

    private String joinCookies(List<HttpCookie> cookies) {
        StringBuilder builder = new StringBuilder();
        for (HttpCookie cookie : cookies) {
            if (builder.length() > 0) {
                builder.append("; ");
            }
            builder.append(cookie.getName()).append("=").append(cookie.getValue());
        }
        return builder.toString();
    }

    private String buildFormData(Map<String, String> form) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : form.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return builder.toString();
    }

    private String readBody(HttpURLConnection connection) throws Exception {
        InputStream stream = null;
        try {
            stream = connection.getInputStream();
        } catch (IOException e) {
            stream = connection.getErrorStream();
            if (stream == null) {
                throw e;
            }
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    private String extractAreaId(String loginPageUrl) throws Exception {
        String query = new URI(loginPageUrl).getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2 && "areaId".equals(keyValue[0])) {
                return URLDecoder.decode(keyValue[1], "UTF-8");
            }
        }
        throw new IllegalArgumentException("登录地址未找到 areaId");
    }

    private String md5Hex(String text) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] digest = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for (byte value : digest) {
            String hex = Integer.toHexString(value & 0xff);
            if (hex.length() == 1) {
                builder.append('0');
            }
            builder.append(hex);
        }
        return builder.toString();
    }

    private String resolveOperatingSystem() {
        String osName = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (osName.contains("win")) {
            return "Windows";
        }
        if (osName.contains("mac")) {
            return "Mac OS";
        }
        if (osName.contains("nix") || osName.contains("nux") || osName.contains("linux")) {
            return "Linux";
        }
        return osName;
    }

    private void sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    private static class ColumnDef {
        private final String name;
        private final String text;

        private ColumnDef(String name, String text) {
            this.name = name;
            this.text = text;
        }
    }

    private static class ProgressResult {
        private final String downloadUrl;

        private ProgressResult(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }
    }
}
