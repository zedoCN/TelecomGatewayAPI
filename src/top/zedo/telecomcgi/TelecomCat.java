package top.zedo.telecomcgi;

import com.google.gson.JsonObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.ProtocolException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.HeaderGroup;
import top.zedo.telecomcgi.info.AllInfo;
import top.zedo.telecomcgi.info.GWInfo;
import top.zedo.telecomcgi.info.GWStatus;
import top.zedo.telecomcgi.info.PMDisplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelecomCat {
    private String token;
    private final HeaderGroup headerGroup = new HeaderGroup() {
        {
            addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
        }
    };
    private String hostIp = "192.168.1.1";
    private String url;
    private String sysAuth;
    RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
    CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();


    public TelecomCat() {
        setHostIp(hostIp);
    }

    private void setHeader(String name, String value) {
        headerGroup.setHeader(new BasicHeader(name, value));
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
        setHeader("Host", hostIp);
        url = "http://" + hostIp;
    }

    public GWStatus getGWStatus() {
        HttpGet request = new HttpGet(url + "/cgi-bin/luci/admin/settings/gwstatus");
        request.setHeaders(headerGroup.getHeaders());
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            return GsonManager.fromJson(EntityUtils.toString(responseEntity), GWStatus.class);
        } catch (ProtocolException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GWInfo getGWInfo() {
        HttpGet request = new HttpGet(url + "/cgi-bin/luci/admin/settings/gwinfo?get=all");
        request.setHeaders(headerGroup.getHeaders());
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            return GsonManager.fromJson(EntityUtils.toString(responseEntity), GWInfo.class);
        } catch (ProtocolException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PMDisplay getPMDisplay() {
        HttpGet request = new HttpGet(url + "/cgi-bin/luci/admin/settings/pmDisplay");
        request.setHeaders(headerGroup.getHeaders());
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            return GsonManager.fromJson(EntityUtils.toString(responseEntity), PMDisplay.class);
        } catch (ProtocolException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AllInfo getAllInfo() {
        HttpGet request = new HttpGet(url + "/cgi-bin/luci/admin/allInfo");
        request.setHeaders(headerGroup.getHeaders());
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            return GsonManager.fromJson(EntityUtils.toString(responseEntity), AllInfo.class);
        } catch (ProtocolException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSingle(String name, String ip, Protocol protocol, int exPort, int inPort) {
        pmSetSingle("add", name, ip, protocol.name(), exPort, inPort);
    }

    public void enableSingle(String name) {
        pmSetSingle("enable", name, null, null, 0, 0);
    }

    public void disableSingle(String name) {
        pmSetSingle("disable", name, null, null, 0, 0);
    }

    public void removeSingle(String name) {
        pmSetSingle("del", name, null, null, 0, 0);
    }

    public void realDeleteAll() {
        pmSetAll("del");
    }

    public void realDisableAll() {
        pmSetAll("disable");
    }

    public void realEnableAll() {
        pmSetAll("enable");
    }

    /**
     * 登出
     */
    public void logout() {
        HttpPost request = new HttpPost(url + "/cgi-bin/luci/admin/logout");
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicHeader("token", token));
        request.setHeaders(headerGroup.getHeaders());
        request.setEntity(new UrlEncodedFormEntity(form));
        try {
            httpclient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重启
     */
    public void reboot() {
        HttpPost request = new HttpPost(url + "/cgi-bin/luci/admin/reboot");
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicHeader("token", token));
        request.setHeaders(headerGroup.getHeaders());
        request.setEntity(new UrlEncodedFormEntity(form));
        try {
            httpclient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void pmSetAll(String op) {
        HttpPost request = new HttpPost(url + "/cgi-bin/luci/admin/settings/pmSetAll");
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicHeader("token", token));
        form.add(new BasicHeader("op", op));
        request.setHeaders(headerGroup.getHeaders());
        request.setEntity(new UrlEncodedFormEntity(form));
        try {
            httpclient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void pmSetSingle(String op, String srvname, String client, String protocol, int exPort, int inPort) {
        HttpPost request = new HttpPost(url + "/cgi-bin/luci/admin/settings/pmSetSingle");
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicHeader("token", token));
        form.add(new BasicHeader("op", op));
        if (srvname != null)
            form.add(new BasicHeader("srvname", srvname));
        if (client != null)
            form.add(new BasicHeader("client", client));
        if (protocol != null)
            form.add(new BasicHeader("protocol", protocol));
        if (exPort != 0)
            form.add(new BasicHeader("exPort", exPort));
        if (inPort != 0)
            form.add(new BasicHeader("inPort", inPort));
        request.setHeaders(headerGroup.getHeaders());
        request.setEntity(new UrlEncodedFormEntity(form));
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            System.out.println(GsonManager.fromJson(EntityUtils.toString(responseEntity), JsonObject.class).get("retVal").getAsInt());
            if (GsonManager.fromJson(EntityUtils.toString(responseEntity), JsonObject.class).get("retVal").getAsInt() != 0)
                throw new RuntimeException("操作执行失败: " + op);
        } catch (ProtocolException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录
     *
     * @param username 用户名 通常为 "useradmin"
     * @param password 密码 光猫后面有写
     */
    public void login(String username, String password) {
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicHeader("username", username));
        form.add(new BasicHeader("psd", password));

        HttpPost request = new HttpPost(url + "/cgi-bin/luci");
        request.setHeaders(headerGroup.getHeaders());
        request.setEntity(new UrlEncodedFormEntity(form));


        try (CloseableHttpResponse response = httpclient.execute(request)) {
            if (response.getCode() == 302) {
                sysAuth = response.getFirstHeader("Set-Cookie").getValue().split(";")[0].split("=")[1];
                setHeader("Cookie", "sysauth=" + sysAuth);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getToken();
    }

    private void getToken() {
        Pattern pattern = Pattern.compile("[a-z0-9]{32}");
        HttpGet request = new HttpGet(url + "/cgi-bin/luci/");
        request.setHeaders(headerGroup.getHeaders());
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            if (response.getCode() == 200) {
                String html = EntityUtils.toString(response.getEntity());
                Matcher matcher = pattern.matcher(html);
                if (matcher.find()) {
                    token = matcher.group();
                    return;
                }
            }
            throw new RuntimeException("获取Token失败");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
