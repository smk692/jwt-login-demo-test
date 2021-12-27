# Login-demo-son

## 해당 프로젝트는 Jwt token을 이용한 로그인 데모 서비스입니다.

### 구성
- Spring boot 2.6.2, gradle-6.8.3
- Spring JPA
- Spring Security
- coolSMS : https://coolsms.co.kr/ (핸드폰 인증)
- Jwt token
- Mysql database
- Log4j jdbc
- Swagger
- ...

```
만약 gradle 버전을 올려야되면 gradle-wrapper.properties 수정 하면된다.

Library 추가 및 수정은 build.gradle 파일을 확인한다. 
```

### 코드 진행
```
클라이언트(Front) -> Fillter -> Intercepter -> Controller -> service -> model
-> service -> controller -> Intercepter -> Fillter -> 클라이언트(Front)   
```

### 실행방법색

- gradle compileQuerydsl 실행한다.
- 상단 메뉴에서 Run > Edit Configuration > Active profiles > local 입력 후 실행

### 테스트 방법

Swagger Link - http://localhost:8080/swagger-ui/index.html#/
- curl 명령어로 정리를 하면 복잡해지기때문에 swagger 사용
- 아무것도 안나올 경우 explore 버튼 옆에 /api-docs 입력 후 검
```
테스트 진행 하고 싶을 시 메뉴 바 안에 try it 클릭 후 진행 # X-API-VERSION = 1 필수

Step 1. 로컬을 킨 후 스웨거링크로 접속한다.
Step 2. 회원가입을 한다. (/member - post) 
Step 3. 로그인을 한다. (/login - post)
Step 4. 로그인 API 호출 후 accessToken 값을 긁어서 복사한다.
Step 5. 우측 상단에 Authorizations accessToken 값을 넣어준다.
Step 6. 인증 완료 된 후 테스트를 한다.

참고 - 문자 무료 전송은 하루에 20건이라 양해 부탁드려요 ㅎㅎ.. 
```


