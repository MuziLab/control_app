package com.example.ble_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectedDevices {
    public static List<String> getConnectedIPAddresses() {
        List<String> ipList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts != null && parts.length >= 4) {
                    String ip = parts[0];
                    ipList.add(ip);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ipList;
    }
}
