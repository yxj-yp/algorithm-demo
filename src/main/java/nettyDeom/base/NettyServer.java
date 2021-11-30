package nettyDeom.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nettyDeom.MyHandler;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 10:39 2021/11/18
 *Description : 
 *Version     : 
 ***/

public class NettyServer {
    public static void main(String[] args) throws  Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(10);

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024) //6.设置线程队列中等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) //7.保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // 创建一个通道初始化对象
                        public void initChannel(SocketChannel sc){   //往Pipeline链中添加自定义的handler类
                            sc.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("netty 服务器启动");
            //绑定端口 同时异步返回ChannelFuture对象 启动netty服务端
            ChannelFuture cf = serverBootstrap.bind(8003).sync();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
