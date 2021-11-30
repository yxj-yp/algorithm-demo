package nettyDeom.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 11:24 2021/11/18
 *Description : 
 *Version     : 
 ***/

public class ChatClient {
    public static void main(String[] args) throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {  // 创建一个通道初始化对象
                        public void initChannel(SocketChannel sc){   //往Pipeline链中添加自定义的handler类
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());//加入解码器
                            pipeline.addLast("encoder",new StringEncoder());//加入编码器
                            pipeline.addLast(new ChatClientHandler());//自己的业务处理

                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8003).sync();//连接到服务端
            Channel channel = channelFuture.channel();
            System.out.println("================="+channel.localAddress()+"================");
            Scanner  scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownNow();
        }


    }
}
