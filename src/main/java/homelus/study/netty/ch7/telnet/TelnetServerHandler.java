package homelus.study.netty.ch7.telnet;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * @author playjun
 * @since 2019 10 17
 */
@ChannelHandler.Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(InetAddress.getLocalHost().getHostName() + " 서버에 접속하셨습니다!\r\n");
        ctx.write("현재 시간은 " + new Date() + " 입니다.\r\n");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        String response;
        boolean close = false;

        if (request.isEmpty()) {
            response = "명령을 입력해주세요.\r\n";
        } else if ("bye".equals(request.toString())) {
            // 종료 문자열 입력
            response = "안녕히 가세요!\r\n";
            close = true;
        }
        else {
            response = "입력하신 명령은 '" + request + "' 입니다.\r\n";
        }

        ChannelFuture future = ctx.write(response);

        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
