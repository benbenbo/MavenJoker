package com.zzb.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端，必须先开启服务器端，然后客户端的Socket才能连接上
 */
public class ServerSocketTest {
    public static void main(String[] args) {
        try {
            /**
             * 构造方法绑定端口
             */
            ServerSocket serverSocket = new ServerSocket(8888);
            //serverSocket.accept()等待客户端进行连接，是一个阻塞的方法，直到有客户端连接时才返回
            //此方法返回的是包含客户端信息的套接字，该方法成功返回之后，服务器与客户端建立了一个可靠的连接，
            //可以相互发送和接收消息了。
            Socket socket = serverSocket.accept();
            System.out.println("IP:" + socket.getInetAddress() + ",port:"
                    + socket.getPort());
            /**
             * 使用输入流接收消息
             */
            InputStream inputStream = socket.getInputStream();// 获取输入管道
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // readLine()是阻塞方法，直到读到内容并且遇到终止符（“\r”、“\n”、“\r\n”等等）或者到达流的末尾（返回Null）才返回
            System.out.println(reader.readLine());
            /**
             * 使用输出流发送消息
             */
            OutputStream outputStream = socket.getOutputStream();// 获取输出管道
            //末端必须加终止符，否则另一端的bufferReader.readLine()方法会处于阻塞状态，直到流关闭
            outputStream.write("Hello,client!\r".getBytes());
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
