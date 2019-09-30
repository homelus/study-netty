package homelus.study.netty.ch1.server.discard;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author playjun
 * @since 2019 09 27
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        // 아무것도 하지 않음
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
