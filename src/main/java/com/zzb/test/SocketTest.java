package com.zzb.test;

import java.io.*;
import java.net.Socket;

public class SocketTest {
    public static void main(String[] args) throws IOException {
        //构造方法指定了远程主机的IP和端口成功创建对象之后，客户端和服务器就建立了一个可靠的连接。可以相互发送和接收消息了。
        Socket socket = new Socket("127.0.0.1", 8888);
        /**
         * 使用输出流发送消息
         */
        OutputStream outputStream = socket.getOutputStream();// 获取输出管道
        //末端必须加终止符，否则另一端的bufferReader.readLine()方法会处于阻塞状态，直到流关闭
        outputStream.write("Hello,server!\r".getBytes());
        outputStream.flush();

        /**
         * 使用输入流接收消息
         */
        InputStream inputStream = socket.getInputStream();// 获取输入管道
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // readLine()是阻塞方法，直到读到内容并且遇到终止符（“\r”、“\n”、“\r\n”等等）或者到达流的末尾（返回Null）才返回
        System.out.println(reader.readLine());

        outputStream.close();
        inputStream.close();
        socket.close();

    }
}
