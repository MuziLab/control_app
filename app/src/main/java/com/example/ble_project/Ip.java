package com.example.ble_project;

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import android.util.Log;

public class Ip {
    private static final String TAG = "输出";

    public static HashMap<String, String> getNetIPs() {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement(); // 打印的信息和 ifconfig 的大致对应
                Log.i(TAG, "----》getEtherNetIP inf = " + intf); // eth0、wifi...
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        Log.i(TAG, "----》getEtherNetIP intf.getName() = " + intf.getName());
                        Log.i(TAG, "----》getEtherNetIP inetAddress = " + inetAddress);
                        Log.i(TAG, "----》getEtherNetIP inetAddress getHostAddress = " + inetAddress.getHostAddress());
                        byte[] hardwareAddress = intf.getHardwareAddress();

                        // 节点对应的IP地址
                        hashMap.put(intf.getName(), inetAddress.getHostAddress());
                        // 节点对应的MAC地址，MAC地址是byte数值数据，要转换成字符串
                        String mac = bytesToString(hardwareAddress);
                        hashMap.put(intf.getName() + "-MAC", mac);
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, "getEtherNetIP = " + ex.toString());
        }
        return hashMap;
    }

    // 字节数据转换成字符串
    public static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }
}
