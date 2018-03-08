package me.zuhr.demo.basis;


import me.zuhr.demo.basis.utils.SystemInfo;

/**
 * @author zurun
 * @date 2018/3/9 00:52:11
 */
public class SystemInfoTest {
    public static void main(String[] args) {
        SystemInfo syso = SystemInfo.getInstance();
        System.out.println("IP地址:"+syso.getIP());
        System.out.println("主机名称:"+syso.getHostName());
        System.out.println("Mac地址:"+syso.getMac());
        System.out.println("获取当前系统名称:"+syso. getSystemName());
    }
}