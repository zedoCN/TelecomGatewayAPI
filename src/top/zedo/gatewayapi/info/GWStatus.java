package top.zedo.gatewayapi.info;

public class GWStatus {
    /**
     * cpu占用 范围:0-100
     */
    public int cpuUsed;
    /**
     * iTV业务
     */
    public boolean itv;
    /**
     * 存储设备数
     */
    public int scount;
    /**
     * 运行时间
     */
    public long sysTime;
    /**
     * 上网时间
     */
    public long upTime;
    /**
     * 电话业务
     */
    public boolean voip;
    /**
     * 有线设备数
     */
    public int wcount;
    /**
     * 无线设备数
     */
    public int wlcount;
}
