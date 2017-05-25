package com.changxx.schedule.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class IpUtil {

    public static List<String> getServerIps(boolean incluedOuter) throws Exception {
        List<String> list = new ArrayList<String>();
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        for (; netInterfaces.hasMoreElements(); ) {
            NetworkInterface netInterface = netInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = netInterface.getInetAddresses();
            for (; inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress instanceof Inet4Address) {
                    String localIp = inetAddress.getHostAddress();
                    if (localIp.equals("127.0.0.1")) {
                        continue;
                    }
                    if (incluedOuter || isInnerIp(localIp)) {
                        list.add(localIp);
                    }
                }
            }
        }
        return list;
    }

    private static Pattern p = Pattern.compile("^(10\\.|172\\.(1[6-9]|2[0-9]|3[01])\\.|192\\.168\\.)");

    private static boolean isInnerIp(String ip) {
        return p.matcher(ip).find();
    }

    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getServerIps(true));
        System.out.println("==============================");
        System.out.println(getServerIps(false));
        System.out.println(getLocalHostName());
    }
}
