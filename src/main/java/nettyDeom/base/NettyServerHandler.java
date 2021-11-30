package nettyDeom.base;


import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;


/***
 *Auth        : yanxiaojin
 *Date        : Create in 10:46 2021/11/18
 *Description : 
 *Version     : 
 ***/

public class NettyServerHandler  extends ChannelHandlerAdapter {

    @Override
   public void channelActive(ChannelHandlerContext cx){
       System.out.println("客户端简历链接成功："+cx.name());
       //处理客户端链接之后自己的业务逻辑
   }
    @Override
    public void channelRead(ChannelHandlerContext cx, Object msg){
        Channel channel = cx.channel();
        ChannelPipeline pipeline = cx.pipeline();//pipeline本质上是一个双向链接  出栈入栈
        ByteBuf bf = (ByteBuf) msg;
        System.out.println("收到客户端消息："+bf.toString(CharsetUtil.UTF_8));
        //收到客户端的请求 做出对应处理
    }


}
