package homelus.study.netty.ch6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author playjun
 * @since 2019 10 16
 */
public class UnsignedByteBufferTest {

    final String source = "Hello world";

    @Test
    void unsignedBufferToJavaBuffer() {
        ByteBuf buf = Unpooled.buffer(11);
        buf.writeShort(-1);

        assertEquals(65535, buf.getUnsignedShort(0));
    }

}
