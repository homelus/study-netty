package homelus.study.netty.ch6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author playjun
 * @since 2019 10 16
 */
class PooledHeapBufferTest {

    @Test
    void pooledHeapBufferTest() {
        ByteBuf buf = Unpooled.buffer();
        assertEquals(ByteOrder.BIG_ENDIAN, buf.order());

        buf.writeShort(1);

        buf.markReaderIndex();
        assertEquals(1, buf.readShort());

        buf.resetReaderIndex();

        ByteBuf lettleEndianBuf = buf.order(ByteOrder.LITTLE_ENDIAN);
        assertEquals(256, lettleEndianBuf.readShort());
    }

}
