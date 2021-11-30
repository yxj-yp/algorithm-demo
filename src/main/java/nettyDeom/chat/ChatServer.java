package nettyDeom.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import nettyDeom.base.NettyServerHandler;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 11:04 2021/11/18
 *Description : 
 *Version     : 
 ***/

public class ChatServer  {

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
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());//加入解码器
                        pipeline.addLast("encoder",new StringEncoder());//加入编码器
                        pipeline.addLast(new ChatServerHandler());//自己的业务处理

                    }
                });
        System.out.println("netty 服务器启动");
        //绑定端口 同时异步返回ChannelFuture对象 启动netty服务端
        ChannelFuture cf = serverBootstrap.bind(8003).sync();
        cf.channel().closeFuture().sync();//关闭通道

    }catch (Exception e){
        e.printStackTrace();
    }finally {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
}
