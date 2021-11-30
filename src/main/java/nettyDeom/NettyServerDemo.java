package nettyDeom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.channels.Selector;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 9:32 2021/11/18
 *Description : 
 *Version     : 
 ***/

public class NettyServerDemo {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(10);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class) //5.使用NioServerSocketChannel作为服务器端通道的实现
                    .option(ChannelOption.SO_BACKLOG,128) //6.设置线程队列中等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) //7.保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {  //8. 创建一个通道初始化对象
                        public void initChannel(SocketChannel sc){   //9. 往Pipeline链中添加自定义的handler类
                            sc.pipeline().addLast(new MyHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.bind(8009).sync();


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
