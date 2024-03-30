package top.zedo.telecomcgi;

import com.aliyun.sdk.service.alidns20150109.models.DescribeDomainRecordsResponse;
import com.aliyun.sdk.service.alidns20150109.models.DescribeDomainRecordsResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args_) throws Exception {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Path.of("./config.ini")));
        String sysauth = null;

        DescribeDomainRecordsResponse domainRecordsResponse = Aliyun.getDnsIp();
        String rr = properties.getProperty("rr", "");
        String ip = properties.getProperty("ip", "192.168.1.1");

        DescribeDomainRecordsResponseBody.Record targetRecord = Aliyun.getDomainRecord(rr);

        String lastWanIp = null;

        while (true) {
            try {
                //获取光猫状态
                Cat.StatusInfo statusInfo = Cat.getStatus(sysauth);

                if (statusInfo == null) {
                    //获取光猫登录令牌
                    sysauth = Cat.login(properties.getProperty("username", ""), properties.getProperty("password", "password"));
                    continue;
                }

                if (lastWanIp == null)
                    lastWanIp = statusInfo.WANIP;

                if (targetRecord == null)
                    targetRecord = Aliyun.getDomainRecord(rr);


                if (!statusInfo.WANIP.equals(lastWanIp) || !targetRecord.getValue().equals(statusInfo.WANIP)) {
                    System.out.println("IP地址发生变更 " + lastWanIp + " -> " + statusInfo.WANIP);
                    lastWanIp = statusInfo.WANIP;
                    //更新域名解析值

                    try {
                        Aliyun.updateDomainRecord(targetRecord.getRecordId(), targetRecord.getType(), targetRecord.getRr(), statusInfo.WANIP);
                    } catch (Exception e) {
                        System.out.println("更新记录异常：" + e.getMessage());
                    }
                    Aliyun.updateDomainRecordRemark(targetRecord.getRecordId(), "发生变更 " + formatTime());
                    targetRecord = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(40000);
        }
    }

    public static String formatTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d号 H时m分s秒");
        return sdf.format(new Date());
    }
}