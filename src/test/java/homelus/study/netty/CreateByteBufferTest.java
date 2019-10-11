package homelus.study.netty;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author playjun
 * @since 2019 10 11
 */
class CreateByteBufferTest {

    @Test
    void createTest() {
        CharBuffer heapBuffer = CharBuffer.allocate(11);
        assertEquals(11, heapBuffer.capacity());
        assertFalse(heapBuffer.isDirect());

        ByteBuffer directBuffer = ByteBuffer.allocateDirect(11);
        assertEquals(11, directBuffer.capacity());
        assertTrue(directBuffer.isDirect());

        int[] array = IntStream.rangeClosed(1, 11).toArray();
        IntBuffer intHeapBuffer = IntBuffer.wrap(array);
        assertEquals(11, intHeapBuffer.capacity());
        assertFalse(intHeapBuffer.isDirect());
    }


}
