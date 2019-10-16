package homelus.study.netty.ch6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author playjun
 * @since 2019 10 16
 */
public class OrderedByteBufferTest {

    final String source = "Hello world";

    @Test
    void convertNettyBufferToJavaBuffer() {
        ByteBuf buf = Unpooled.buffer(11);

        buf.writeBytes(source.getBytes());
        assertEquals(source, buf.toString(Charset.defaultCharset()));

        ByteBuffer nioByteBuffer = buf.nioBuffer();
        assertNotNull(nioByteBuffer);
        assertEquals(source, new String(nioByteBuffer.array(), nioByteBuffer.arrayOffset(), nioByteBuffer.remaining()));
    }

    @Test
    void convertJavaBufferToNettyBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(source.getBytes());
        ByteBuf nettyBuffer = Unpooled.wrappedBuffer(byteBuffer);

        assertEquals(source, nettyBuffer.toString(Charset.defaultCharset()));
    }

}
