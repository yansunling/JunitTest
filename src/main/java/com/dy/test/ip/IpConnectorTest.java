package com.dy.test.ip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IpConnectorTest {
    public static void main(String[] args) {
        isHostConnectable("192.168.20.98",22122);
    }


    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {

            socket.connect(new InetSocketAddress(host, port),3000);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
