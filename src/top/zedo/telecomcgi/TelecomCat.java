package top.zedo.telecomcgi;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.net.URI;
import java.net.URLEncoder;

public class TelecomCat {
    private static String ip;
    public static void main(String[] args) {
        ip="127.0.0.1";
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
}
