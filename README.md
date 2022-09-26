# HelloWorld Back-end

> 맘껏 낙서하세요

### 기술

- IntelliJ
- Spring, SpringBoot, MVC, Java, JPA
- MySQL, RDBMS



---

## 개발 환경

### Spring

사이드 프로젝트라 일단은 최신 버전으로 생성하였습니다. (현업에서는 구버전을 쓰기도 합니다.)

샘플코드를 push해 두었습니다.

### DB

DB 접속정보를 입력해야 합니다.

자세한 내용은 `Notion 코드공유`를 참고 부탁드립니다.



## 9/26 회의 보고용

> 이후 지우겠습니다.

#### 진행사항

- Swagger
  - 참고: https://kim-jong-hyun.tistory.com/49
  - 가장 많이 사용되는 API 문서화 툴입니다. 
  - 기능 개발 및 정상 작동 확인했습니다. 서버 실행 후 아래 주소로 사용 가능합니다.
    - http://localhost:8001/swagger-ui/index.html#/
- 에러 API
  - 참고: https://developers.naver.com/docs/common/openapiguide/errorcode.md#%EC%98%A4%EB%A5%98-%EB%A9%94%EC%8B%9C%EC%A7%80
  - 실패 요청을 반환해야 하는 상황에서 공통된 JSON 형식의 메세지를 반환하는 기능입니다.
    - 실패 요청은 400, 500번대 에러가 대표적입니다.
    - Front는 HTTP 상태코드를 가지고 예외처리 하시면 됩니다.
  - 반환 메세지는 Naver API 양식으로 채택했습니다.

#### 예정

- 클라우드 배포
  - 비용 문제로 인해 AWS가 아닌 Oracle cloud를 먼저 시도할 계획입니다.
  - Oracle cloud는 과금 위험이 없다는 장점이 있습니다. 
  - 문서가 적어 시행착오가 있을 것 같습니다. 9/29 까지 완료할 계획이나 더 늦어질 가능성이 있습니다.