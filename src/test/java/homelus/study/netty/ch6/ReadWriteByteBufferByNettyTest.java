package homelus.study.netty.ch6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author playjun
 * @since 2019 10 16
 */
class ReadWriteByteBufferByNettyTest {

    @Test
    void createUnpooledHeapBufferTest() {
        ByteBuf buf = Unpooled.buffer(11);
        testBuffer(buf, false);
    }

    @Test
    void createUnpooledDirectBufferTest() {
        ByteBuf buf = Unpooled.directBuffer(11);
        testBuffer(buf, true);
    }

    @Test
    void createPooledHeapBufferTest() {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
        testBuffer(buf, false);
    }

    @Test
    void createPooledDirectBufferTest() {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
        testBuffer(buf, true);
    }

    private void testBuffer(ByteBuf buf, boolean isDirect) {
        assertEquals(11, buf.capacity());
        assertEquals(isDirect, buf.isDirect());

        String sourceData = "hello world";
        buf.writeBytes(sourceData.getBytes());

        assertEquals(11, buf.readableBytes());
        assertEquals(0, buf.writableBytes());

        assertEquals(sourceData, buf.toString(Charset.defaultCharset()));

        buf.capacity(6);
        assertEquals("hello ", buf.toString(Charset.defaultCharset()));
        assertEquals(6, buf.capacity());

        buf.capacity(13);
        assertEquals("hello ", buf.toString(Charset.defaultCharset()));

        buf.writeBytes("world".getBytes());
        assertEquals(sourceData, buf.toString(Charset.defaultCharset()));

        assertEquals(13, buf.capacity());
        assertEquals(2, buf.writableBytes());
    }

}
