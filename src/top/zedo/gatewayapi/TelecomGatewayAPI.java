package top.zedo.gatewayapi;

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
import top.zedo.gatewayapi.info.AllInfo;
import top.zedo.gatewayapi.info.GWInfo;
import top.zedo.gatewayapi.info.GWStatus;
import top.zedo.gatewayapi.info.PMRules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelecomGatewayAPI {
    private String token;
    private final HeaderGroup headerGroup = new HeaderGroup() {
        {
            addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
        }
    };
    private String hostIp = "192.168.1.1";
    private String url;
    private final RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
    private final CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

    public TelecomGatewayAPI() {
        setHostIp(hostIp);
    }

    private void setHeader(String name, String value) {
        headerGroup.setHeader(new BasicHeader(name, value));
    }

    /**
     * 设置主机ip地址
     *
     * @param hostIp 默认为 "192.168.1.1"
     */
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
        setHeader("Host", hostIp);
        url = "http://" + hostIp;
    }

    /**
     * 获取网关状态
     *
     * @return 网关状态
     */
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

    /**
     * 获取网关信息
     *
     * @return 网关信息
     */
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

    /**
     * 获取端口映射列表
     *
     * @return 端口映射列表
     */
    public PMRules getPMRules() {
        HttpGet request = new HttpGet(url + "/cgi-bin/luci/admin/settings/pmDisplay");
        request.setHeaders(headerGroup.getHeaders());
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            return GsonManager.fromJson(EntityUtils.toString(responseEntity), PMRules.class);
        } catch (ProtocolException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有信息
     *
     * @return 所有信息
     */
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

    /**
     * 增加端口映射规则
     *
     * @param name     规则名
     * @param ip       目标Ip地址
     * @param protocol 协议
     * @param exPort   外部端口
     * @param inPort   内部端口
     */
    public void addPMRule(String name, String ip, Protocol protocol, int exPort, int inPort) {
        pmSetSingle("add", name, ip, protocol.name(), exPort, inPort);
    }

    /**
     * 启用端口映射规则
     *
     * @param name 规则名
     */
    public void enablePMRule(String name) {
        pmSetSingle("enable", name, null, null, 0, 0);
    }

    /**
     * 禁用端口映射规则
     *
     * @param name 规则名
     */
    public void disablePMRule(String name) {
        pmSetSingle("disable", name, null, null, 0, 0);
    }

    /**
     * 移除端口映射规则
     *
     * @param name 规则名
     */
    public void removePMRule(String name) {
        pmSetSingle("del", name, null, null, 0, 0);
    }

    /**
     * 移除所有端口映射规则
     */
    public void removeAllPMRules() {
        pmSetAll("del");
    }

    /**
     * 禁用所有端口映射规则
     */
    public void disableAllPMRules() {
        pmSetAll("disable");
    }

    /**
     * 启用所有端口映射规则
     */
    public void enableAllRules() {
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
            if (GsonManager.fromJson(EntityUtils.toString(responseEntity), JsonObject.class).get("retVal").getAsInt() == -1)
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
                String sysAuth = response.getFirstHeader("Set-Cookie").getValue().split(";")[0].split("=")[1];
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
