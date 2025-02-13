# 폴리큐브 백엔드 개발자 코딩 테스트

## 1. 시작하기

### 1.1. 개발 환경

- OpenJDK 17
- Spring Boot 3.2.1

### 1.2. 라이브러리

- Spring Web
- Lombok
- H2 Database ( ID : pc, PW : 2024 )
- 그 외 필요한 라이브러리는 `build.gradle`에 추가하시면 됩니다.

**라이브러리 추가 시, 어떠한 이유로 추가했는지 프로젝트 설명에 간단히 적어주시면 됩니다.**

### 1.3. 실행 방법


```shell
./gradlew bootRun
```

## 2. 개발 요구사항

공통, 기본, 구현 문제로 구성되어 있으며, 각 문제에 대한 요구사항을 모두 만족해야 합니다.

### 2.1. 공통 (20점)

- [ ] `@ControllerAdvice`, `@ExceptionHandler`를 이용하여, 잘못된 요청에 대한 응답을 처리한다. (4점)
  - [ ] API를 호출할 때, 잘못된 요청이 들어오면 HTTP 400 상태의 `{"reason": 실제사유}`을 응답한다.
  - [ ] API에 대한 실패 상황 통합 테스트 코드 작성
  - [ ] 존재하지 않는 API 호출 시, HTTP 404 상태의 `{"reason": 실제사유}`을 응답한다.
- [ ] Spring MVC 아키텍처와 Restful API를 준수하여 개발한다. (8점)
  - [ ] `@RestController`, `@Service`, `@Repository`를 이용하여 개발한다.
  - [ ] HTTP Method와 URI를 적절하게 사용하여 개발한다.
- [ ] Clean Code를 준수하여 개발한다. (8점)
  - [ ] 코드 스타일을 일관되고 명확하게 작성한다.
  - [ ] 메소드와 클래스의 역할을 명확하게 작성한다.

### 2.2. 기본 문제 (50점)

- [ ] user 등록 API 구현 (8점)
  - [ ] `/users` API를 호출하면, `{"id": ?}`을 응답한다.
  - [ ] `/users` API에 대한 통합 테스트 코드 작성
- [ ] user 조회 API 구현 (8점)
  - [ ] `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.
  - [ ] `/users/{id}` API에 대한 통합 테스트 코드 작성
- [ ] user 수정 API 구현 (8점)
  - [ ] `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.
  - [ ] `/users/{id}` API에 대한 통합 테스트 코드 작성
- [ ] 필터 구현 (12점)
  - [ ] URL에 `? & = : //`를 제외한 특수문자가 포함되어 있을경우 접속을 차단하는 Filter 구현한다.
  - [ ] `/users/{id}?name=test!!` API 호출에 대한 통합 테스트 코드 작성
- [ ] Spring AOP를 활용한 로깅 구현 (14점)
  - [ ] user 등록, 조회, 수정 API에 대해 Request시 Console에 Client Agent를 출력한다.

`user` 테이블

```csv
id,name
```

### 2.3. 구현 문제 (30점)

#### 로또 번호 발급 API 구현 (10점)
- [ ] `POST /lottos` API를 호출하면, `{"numbers": [?, ?, ?, ?, ?, ?]}`을 응답한다.
- [ ] `POST /lottos` API에 대한 통합 테스트 코드 작성

##### Request

```shell
curl -X POST -H "Content-Type: application/json" http://localhost:8080/lottos
```

##### Response

```json
{
  "numbers": [?, ?, ?, ?, ?, ?]
}
```

#### 로또 번호 당첨자 검수 Batch 구현 (20점)
그냥 랜덤? secure random?
- [ ] 랜덤하게 로또 번호를 발급하여, 당첨 번호와 비교하여 당첨자를 검수하는 Batch를 구현한다.
  - [ ] 당첨자의 등수는 1등, 2등, 3등, 4등, 5등이 있다.
  - [ ] 당첨자의 등수는 당첨 번호와 일치하는 번호의 개수로 판단한다.
  - [ ] 당첨자 정보는 `winner` 테이블에 저장한다.
- [ ] Batch는 매주 일요일 0시에 실행되도록 구현한다.
- [ ] Batch에 대한 통합 테스트 코드 작성

##### Input Data

`lotto` 테이블

```csv
id,number_1,number_2,number_3,number_4,number_5,number_6
1,7,28,33,2,45,19
2,26,14,41,3,22,35
3,15,29,38,6,44,21
4,31,16,42,9,23,36
5,17,30,39,10,45,24
```

##### Output Data

`winner` 테이블

```csv
id,lotto_id,rank
```

- `id`: generated value
- `lottos_id`: 당첨 번호의 `id`
- `rank`: 당첨 등수 (1등, 2등, 3등, 4등, 5등)

#### 추가 설명

- `?`는 임의의 값으로, 실제로 응답할 때는 해당 값이 들어가야 합니다.
- `id`는 `Long` 타입입니다.
- `@ExceptionHandler`는 `@RestControllerAdvice`를 이용하여 구현합니다.

## 제출 방법

- 개발이 완료되면, 본인의 github 리포지토리에 올리고 해당 주소를 보내주시면 됩니다.
- 응시자가 개발하면서 고민했던 점, 혹은 어려웠던 점을 프로젝트 설명에 간단히 적어주시면 됩니다. (선택사항)
