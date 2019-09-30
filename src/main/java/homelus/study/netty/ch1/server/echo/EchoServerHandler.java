package homelus.study.netty.ch1.server.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * 입력된 데이터를 처리하는 이벤트 핸들러 상속
 * @author playjun
 * @since 2019 09 27
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
    /**
     * 수신 이벤트 처리 메서드
     * 데이터가 수신되면 자동 호출
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset()); // 수신된 데이터를 가진 바이트 버퍼로부터 문자열을 읽는다.
        System.out.println("수신 문자열 [" + readMessage + "]");
        ctx.write(msg); // 채널 파이프라인에 대한 이벤트를 처리한다. 서버에 연결된 클라이언트 채널로 입력받은 데이터를 그대로 전송한다.
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush(); // 이벤트 처리가 완료된 후 자동으로 수행된다. 채널 파이프라인에 저장된 버퍼를 전송한다.
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
