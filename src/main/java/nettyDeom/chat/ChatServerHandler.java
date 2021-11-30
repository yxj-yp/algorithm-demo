package nettyDeom.chat;


import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;


/***
 *Auth        : yanxiaojin
 *Date        : Create in 10:46 2021/11/18
 *Description : 
 *Version     : 
 ***/

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");



    //上线
    @Override
   public void channelActive(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[客户端]"+ channel.remoteAddress()+"上线了"+sdf.format(new Date()));
        channelGroup.add(channel);//将channel放入channelGroup
        System.out.println("客户端简历链接成功："+ctx.name());
       //处理客户端链接之后自己的业务逻辑
   }

    //下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[客户端]"+ channel.remoteAddress()+"下线了"+sdf.format(new Date()));
        System.out.println(channel.remoteAddress()+"下线了"+"\n");
        System.out.println("channelGroup size："+channelGroup.size());

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();//pipeline本质上是一个双向链接  出栈入栈
        ByteBuf bf = (ByteBuf) msg;
        System.out.println("收到客户端消息："+bf.toString(CharsetUtil.UTF_8));
        //收到客户端的请求 做出对应处理
        channelGroup.forEach(ch ->{
            if(channel != ch){
                ch.writeAndFlush("[客户端]" + channel.remoteAddress()+"发送了消息:"+msg+"\n");
            }else{
                ch.writeAndFlush("[自己]" + "发送了消息:"+msg+"\n");
            }
        });
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }


}
