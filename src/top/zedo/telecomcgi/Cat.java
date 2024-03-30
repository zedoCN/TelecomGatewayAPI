package top.zedo.telecomcgi;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.net.URI;
import java.net.URLEncoder;

public class Cat {
    public static class StatusInfo {
        String LANIP;
        String LANIPv6;
        String MAC;
        String WANIP;
        String WANIPv6;
        String ProductSN;
        String DevType;
        String SWVer;
        String Vendor;
        String ProductCls;
    }

    static String ip = "192.168.1.1";

    public static void setIp(String ip) {
        Cat.ip = ip;
    }

    public static String login(String username, String password) throws Exception {
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setRedirectsEnabled(false) // 禁用重定向
                    .build();

            CloseableHttpClient client = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            String url = "http://" + ip + "/cgi-bin/luci";

            String requestBody = "username=" + URLEncoder.encode(username, "UTF-8") + "&psd=" + URLEncoder.encode(password, "UTF-8");

            HttpPost request = new HttpPost(new URI(url));
            request.setEntity(new StringEntity(requestBody));

            request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
            request.addHeader("Accept-Encoding", "gzip, deflate");
            request.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
            request.addHeader("Cache-Control", "max-age=0");
            request.addHeader("Connection", "keep-alive");
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            request.addHeader("Host", ip);
            request.addHeader("Origin", "http://" + ip);
            request.addHeader("Referer", "http://" + ip + "/cgi-bin/luci");
            request.addHeader("Upgrade-Insecure-Requests", "1");
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

            CloseableHttpResponse response = client.execute(request);
            if (response.getCode() == 302) {
                String sysauth = response.getFirstHeader("Set-Cookie").getValue().split(";")[0].split("=")[1];
                return sysauth;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static StatusInfo getStatus(String sysauth) throws Exception {
        if (sysauth == null)
            return null;
        try {
            CloseableHttpClient client = HttpClients.custom()
                    .build();

            String url = "http://" + ip + "/cgi-bin/luci/admin/settings/gwinfo?get=part&_=" + System.currentTimeMillis();

            HttpGet request = new HttpGet(new URI(url));

            request.addHeader("Accept", "*/*");
            request.addHeader("Accept-Encoding", "gzip, deflate");
            request.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
            request.addHeader("Connection", "keep-alive");
            request.addHeader("Host", ip);
            request.addHeader("Referer", "http://" + ip + "/cgi-bin/luci/admin/settings/info");
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");
            request.addHeader("Cookie", "sysauth=" + sysauth);

            CloseableHttpResponse response = client.execute(request);

            if (response.getCode() == 200) {
                String jsonResponse = new String(response.getEntity().getContent().readAllBytes());
                // 使用 Gson 解析 JSON
                return new Gson().fromJson(jsonResponse, StatusInfo.class);
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }


    public static void main(String[] args) {

    }
}
