package com.zzb.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class UDPSocketTest {
    // 最多重发次数
    private static final int MAXTRIES = 5;

    // 最大超时时间
    private static final int TIMEOUT = 3000;
    public static void main(String[] args) {
        try {
            byte[] msg = new String("Hello job").getBytes();
            InetAddress serverAddress = InetAddress.getLocalHost();

            DatagramSocket socket = new DatagramSocket();
            //设置超时时间
            socket.setSoTimeout(TIMEOUT);
            DatagramPacket sendPacket = new DatagramPacket(msg, msg.length,serverAddress, 8850);
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

            boolean receivedResponse = false;
            int tries = 0;
            do{

                socket.send(sendPacket);
                try{
                    socket.receive(receivePacket);

                    // 判断发送与接收的数据报包是否不属于同一个IP地址
                    if (!receivePacket.getAddress().equals(serverAddress))
                    {
                        throw new IOException(
                                "Received pakcet from a unknown source");
                    }

                    // 用于标识数据包接收是否成功
                    receivedResponse = true;
                }
                catch (SocketTimeoutException e){
                    tries += 1;
                    System.out.println("Timed out," + (MAXTRIES - tries)
                            + "more tries......");
                }
            }
            while ((!receivedResponse) && (tries < MAXTRIES));
            if (receivedResponse){
                /*
                 * 返回的缓存数组的长度可能比接收的数据报文内部长度更长 (也就是说：在接收的byte[]数组缓冲区中，有可能
                 * 没有被发送的数据报文消息填满)
                 */
                byte[] receiveMsg = Arrays.copyOfRange(receivePacket.getData(),
                        0, receivePacket.getOffset()
                                + receivePacket.getLength());
                System.out.println("Client receive data:" + new String(receiveMsg));
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
