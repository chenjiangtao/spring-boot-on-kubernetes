package cn.topstream.app.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class IPWhiteList {
    // IP的正则
    private static Pattern pattern = Pattern
            .compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");

    /**
     *
     * getAvaliIpList:(根据IP白名单设置获取可用的IP列表).
     *
     * @date 2017-4-17 下午02:50:20
     * @param allowIp
     * @return
     */

    private static Set<String> getAvaliIpList(String allowIp) {

        Set<String> ipList = new HashSet<String>();
        for (String allow : allowIp.replaceAll("\\s", "").split(";")) {
            if (allow.indexOf("*") > -1) {
                String[] ips = allow.split("\\.");
                String[] from = new String[] { "0", "0", "0", "0" };
                String[] end = new String[] { "255", "255", "255", "255" };
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++) {
                    if (ips[i].indexOf("*") > -1) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }
                }
                StringBuffer fromIP = new StringBuffer();
                StringBuffer endIP = new StringBuffer();
                for (int i = 0; i < 4; i++) {
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]",
                            s.split(";")[0])
                            + "-"
                            + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validate(ip)) {
                        ipList.add(ip);
                    }
                }
            } else {
                if (validate(allow)) {
                    ipList.add(allow);
                }
            }

        }

        return ipList;
    }

    /**
     * 对单个IP节点进行范围限定
     *
     * @param arg
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> complete(String arg) {
        List<String> com = new ArrayList<String>();
        if (arg.length() == 1) {
            com.add("0;255");
        } else if (arg.length() == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null){
                com.add(s1);
            }
            String s2 = complete(arg, 2);
            if (s2 != null){
                com.add(s2);
            }
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null){
                com.add(s1);
            }
        }
        return com;
    }

    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.valueOf(from) > 255){
            return null;
        }
        if (Integer.valueOf(end) > 255){
            end = "255";
        }
        return from + ";" + end;
    }

    /**
     * 在添加至白名单时进行格式校验
     *
     * @param ip
     * @return
     */
    private static boolean validate(String ip) {
        for (String s : ip.split("-")){
            if (!pattern.matcher(s).matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * checkLoginIP:(根据IP,及可用Ip列表来判断ip是否包含在白名单之中).
     * @date 2017-4-17 下午03:01:03
     * @param ip
     * @param ipList
     * @return
     */
    private static boolean checkLoginIP(String ip, Set<String> ipList) {
        if (ipList.isEmpty() || ipList.contains(ip)){
            return true;
        }
        else {
            for (String allow : ipList) {
                if (allow.indexOf("-") > -1) {
                    String[] from = allow.split("-")[0].split("\\.");
                    String[] end = allow.split("-")[1].split("\\.");
                    String[] tag = ip.split("\\.");

                    // 对IP从左到右进行逐段匹配
                    boolean check = true;
                    for (int i = 0; i < 4; i++) {
                        int s = Integer.valueOf(from[i]);
                        int t = Integer.valueOf(tag[i]);
                        int e = Integer.valueOf(end[i]);
                        if (!(s <= t && t <= e)) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * checkLoginIP:(根据IP地址，及IP白名单设置规则判断IP是否包含在白名单).
     * @date 2017-4-17 下午03:01:37
     * @param ip
     * @param ipWhiteConfig
     * @return
     */
    public static boolean checkLoginIP(String ip,String ipWhiteConfig){
        Set<String> ipList = getAvaliIpList(ipWhiteConfig);
        return checkLoginIP(ip, ipList);
    }


//    public static void main(String[] args) {
//
//        String ipWhilte = "192.168.1.1;192.168.1.2;" +                 //设置单个IP的白名单
//                "192.168.2.*;" +                 //设置ip通配符,对一个ip段进行匹配
//                "192.168.3.17-192.168.3.38"+";221.168.3.17-221.168.3.38";     //设置一个IP范围
////        boolean flag = IPWhiteList.checkLoginIP("192.168.2.2",ipWhilte);
////        boolean flag2 = IPWhiteList.checkLoginIP("192.168.1.2",ipWhilte);
////        boolean flag3 = IPWhiteList.checkLoginIP("192.168.3.16",ipWhilte);
//        boolean flag4 = IPWhiteList.checkLoginIP("192.168.3.39",ipWhilte);
//        boolean flag5 = IPWhiteList.checkLoginIP("221.168.3.30",ipWhilte);
////        System.out.println(flag);  //true
////        System.out.println(flag2);  //false
////        System.out.println(flag3);  //false
//        System.out.println(flag4);  //false
//        System.out.println(flag5);  //true
//    }
}
