package com.zzb.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NIOTest {
    public static void main(String[] args) throws IOException {
        // 1.创建一个selector对象
        Selector selector = Selector.open();
        // 2.建立channel对象，并绑定在8080端口上
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
        ssc.socket().bind(address);
        // 3.将channel设定为非阻塞方式
        ssc.configureBlocking(false);
        // 向selector注册channel以及我们感兴趣的事件
        SelectionKey skey = ssc.register(selector, SelectionKey.OP_ACCEPT);// 这边注册了accept，服务器接受到client连接事件
        // 4、简单模拟下轮询过程
        while (true) {
            // selector通过select方法，通知我们感兴趣的事件发生了
            int nKeys = selector.select();
            // 当nKeys>0表示事件发生了
            // 这时候可以通过selector.selectedKeys();方法拿到key集合
            Set<SelectionKey> selectKeys = selector.selectedKeys();
            // 5、迭代遍历keys对象，分别做适配业务逻辑处理
            // 比如：
            SelectionKey s = (SelectionKey) (selectKeys.iterator()).next();
            // 判断为注册的OP_ACCEPT事件
            if (s.isAcceptable()){
                // 从channel中获取我们刚才注册的channel
                Socket socket = ((ServerSocketChannel) s.channel()).accept().socket();
                SocketChannel sc = socket.getChannel();
                // 设置为非阻塞
                sc.configureBlocking(false);
                // 注册read/write事件
                sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            }
        }
    }
}
