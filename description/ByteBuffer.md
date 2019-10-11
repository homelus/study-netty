## ByteBuffer

### Java NIO

##### capacity : 버퍼에 저장할 수 있는 데이터의 최대 크기(설정 후 변경 불가능)
##### position : 읽기 또는 쓰기가 작업중인 위치(초깃값 0, get 혹은 put 메서드 호출 시 자동 증가), limit 와 capacity 값보다 작거타 같아야 한다. 
##### limit : 읽고 쓸 수 있는 버퍼 공간의 최대치, capacity 보다 크게 설정할 수 없음.

#### 바이트 버퍼 생성 방법

##### ByteBuffer.allocate(n) : JVM 의 힙영역에 바이트 버퍼를 생성
##### ByteBuffer.allocateDirect(n) : 운영체제의 커널 영역에 바이트 버퍼를 생성
##### ByteBuffer.wrap(byte[]) 입력된 바이트 배열을 사용해 버퍼 생성

#### 사용 메서드

##### rewind() : position 의 속성을 0으로 변경
##### flip() : position 값을 0으로 변경, limit 를 마지막에 기록한 데이터 위치로 변경  