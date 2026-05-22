package com.junit;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
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
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FallHttpLoginTest {
    private static final String LOGIN_PAGE_URL = "https://fall.wlhn.com/fallapp-main-welcome/ext/pLogin?areaId=88a93c7727944754ab2e1ff01934cf70";
    private static final String LOGIN_ACTION_URL = "https://fall.wlhn.com/fallapp-main-welcome/pcAccount/pLogin";
    private static final String ADMIN_URL = "https://fall.wlhn.com/fallapp-main-welcome/sys/admin";
    private static final String USERNAME = "008驮龙";
    private static final String PASSWORD = "123456";

    @Test
    public void shouldLoginFallPageByHttp() throws Exception {
        LoginResult result = login();

        Assert.assertEquals("登录接口返回应为 true", "true", result.loginResponseBody);
        Assert.assertTrue("登录后应能访问管理页", result.adminPageBody.contains("Ext") || result.adminPageBody.contains("logout"));
    }

    public static void main(String[] args) throws Exception {
        FallHttpLoginTest test = new FallHttpLoginTest();
        LoginResult result = test.login();
        System.out.println("loginResponseBody=" + result.loginResponseBody);
        System.out.println("cookies=" + result.cookieHeader);
        System.out.println("adminPageLoaded=" + (result.adminPageBody.contains("Ext") || result.adminPageBody.contains("logout")));
    }

    private LoginResult login() throws Exception {
        List<HttpCookie> cookies = new ArrayList<HttpCookie>();
        getLoginPage(cookies);

        String areaId = extractAreaId(LOGIN_PAGE_URL);
        String loginResponseBody = postLogin(cookies, areaId);
        String adminPageBody = getAdminPage(cookies);
        return new LoginResult(loginResponseBody, adminPageBody, joinCookies(cookies));
    }

    private void getLoginPage(List<HttpCookie> cookies) throws Exception {
        HttpURLConnection connection = openConnection(LOGIN_PAGE_URL, "GET", cookies);
        try {
            int statusCode = connection.getResponseCode();
            Assert.assertTrue("登录页访问失败，status=" + statusCode, statusCode >= 200 && statusCode < 400);
            storeCookies(connection, cookies);
            String body = readBody(connection);
            Assert.assertTrue("登录页未返回账号输入框", body.contains("id=\"uname\""));
            Assert.assertTrue("登录页未返回密码输入框", body.contains("id=\"pwd\""));
        } finally {
            connection.disconnect();
        }
    }

    private String postLogin(List<HttpCookie> cookies, String areaId) throws Exception {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("uname", USERNAME);
        form.put("pwd", md5Hex(PASSWORD));
        form.put("vk", "");
        form.put("vcode", "");
        form.put("area", areaId);
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

        byte[] requestBody = buildFormData(form).getBytes(StandardCharsets.UTF_8);
        HttpURLConnection connection = openConnection(LOGIN_ACTION_URL, "POST", cookies);
        try {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody);
            outputStream.flush();
            outputStream.close();

            int statusCode = connection.getResponseCode();
            Assert.assertTrue("登录请求失败，status=" + statusCode, statusCode >= 200 && statusCode < 400);
            storeCookies(connection, cookies);
            return readBody(connection).trim();
        } finally {
            connection.disconnect();
        }
    }

    private String getAdminPage(List<HttpCookie> cookies) throws Exception {
        HttpURLConnection connection = openConnection(ADMIN_URL, "GET", cookies);
        try {
            int statusCode = connection.getResponseCode();
            Assert.assertTrue("管理页访问失败，status=" + statusCode, statusCode >= 200 && statusCode < 400);
            storeCookies(connection, cookies);
            return readBody(connection);
        } finally {
            connection.disconnect();
        }
    }

    private HttpURLConnection openConnection(String url, String method, List<HttpCookie> cookies) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Referer", LOGIN_PAGE_URL);
        if (!cookies.isEmpty()) {
            connection.setRequestProperty("Cookie", joinCookies(cookies));
        }
        return connection;
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
            builder.append(line).append('\n');
        }
        reader.close();
        return builder.toString();
    }

    private String extractAreaId(String loginPageUrl) throws Exception {
        String query = new URI(loginPageUrl).getQuery();
        if (query == null || query.length() == 0) {
            throw new IllegalArgumentException("登录地址缺少 areaId");
        }
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

    private static class LoginResult {
        private final String loginResponseBody;
        private final String adminPageBody;
        private final String cookieHeader;

        private LoginResult(String loginResponseBody, String adminPageBody, String cookieHeader) {
            this.loginResponseBody = loginResponseBody;
            this.adminPageBody = adminPageBody;
            this.cookieHeader = cookieHeader;
        }
    }
}
