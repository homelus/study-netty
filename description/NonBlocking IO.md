## Non Blocking IO

### 비동기 호출과 Non Blocking IO 는 필연적으로 프로그램 복잡도가 증가한다.

#### Selector

자신에게 등록된 채널에 변경사항이 발생했는지 검사하고 변경 사항이 발생한 채널에 접근하도록 도와줌

#### ServerSocketChannel

NonBlocking 을 사용할 수 있는 Socket

configureBlocking(false) 설정 필요

ServerSocket 에 Selector 객체를 등록하면 이벤트 감지를 시작한다.
