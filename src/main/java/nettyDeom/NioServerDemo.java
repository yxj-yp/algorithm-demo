package nettyDeom;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 10:08 2021/11/18
 *Description : 
 *Version     : 
 ***/

public class NioServerDemo {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        
        serverSocketChannel.configureBlocking(false);//非阻塞
        //打开Selector处理channel 底层调用epoll_create创建epoll
        Selector selector = Selector.open();//获取多路复用器对象linux系统调用的是EpollSelectorProvider
        //将serverSocketChannel注册到Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//OP_ACCEPT:连接客户端 可接受操作

        while (true){
            selector.select();//阻塞等待需要处理的事件发生

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT);//客户端链接
                }
                else if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(6);
                    int len = socketChannel.read(byteBuffer);
                    if(len>0){
                        System.out.println("接收到消息:"+ new String(byteBuffer.array()));
                    }
                    else if(len == -1){//客户端断开链接  关闭socket
                        System.out.println("客户端断开连接");
                        socketChannel.close();
                    }
                }


            }

        }
        
    }
    
}
