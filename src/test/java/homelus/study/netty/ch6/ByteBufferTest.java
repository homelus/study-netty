package homelus.study.netty.ch6;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author playjun
 * @since 2019 10 11
 */
public class ByteBufferTest {

    @Test
    void useByteBuffer() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("바이트 버퍼 초기값: " + firstBuffer);

        byte[] source = "Hello World".getBytes();
        firstBuffer.put(source);
        System.out.println("11바이트 기록 후: " + firstBuffer);
    }

    @Test
    void overByteBuffer() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("초기 상태: " + firstBuffer);

        byte[] source = "Hello world!".getBytes();

        for (byte item : source) {
            firstBuffer.put(item);
            System.out.println("현재 상태 : " + firstBuffer);
        }
    }

    @Test
    void unExpectedSelectByteBuffer() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("초기 상태: " + firstBuffer);

        firstBuffer.put((byte) 1);
        System.out.println(firstBuffer.get());
        System.out.println(firstBuffer);
    }

    @Test
    void expectedSelectByteBuffer() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("초기 상태: " + firstBuffer);

        firstBuffer.put((byte) 1);
        firstBuffer.put((byte) 2);
        assertEquals(2, firstBuffer.position());

        firstBuffer.rewind();
        assertEquals(0, firstBuffer.position());

        assertEquals(1, firstBuffer.get());
        assertEquals(1, firstBuffer.position());

        System.out.println(firstBuffer);
    }

    @Test
    void writeByteBuffer() {
        ByteBuffer firstBuffer = ByteBuffer.allocateDirect(11);
        System.out.println("초기 상태: " + firstBuffer);
        assertEquals(0, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        firstBuffer.put((byte) 1);
        firstBuffer.put((byte) 2);
        firstBuffer.put((byte) 3);
        firstBuffer.put((byte) 4);
        assertEquals(4, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        firstBuffer.flip();
        assertEquals(0, firstBuffer.position());
        assertEquals(4, firstBuffer.limit());
        System.out.println("마지막 상태: " + firstBuffer);
    }

    @Test
    void read() {
        byte[] tempArray = {1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
        ByteBuffer firstBuffer = ByteBuffer.wrap(tempArray);
        assertEquals(0, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        assertEquals(1, firstBuffer.get());
        assertEquals(2, firstBuffer.get());
        assertEquals(3, firstBuffer.get());
        assertEquals(4, firstBuffer.get());
        assertEquals(4, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        firstBuffer.flip();
        assertEquals(0, firstBuffer.position());
        assertEquals(4, firstBuffer.limit());

        System.out.println(firstBuffer.get(3));
        assertEquals(0, firstBuffer.position());
    }

}
