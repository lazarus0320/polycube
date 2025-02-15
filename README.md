# 프로젝트 설명
## 고민했던 점
- 도메인 드리븐 아키텍처를 기반으로 각각 자신의 관심사에만 집중할 수 있도록 구성했습니다.
- Spring AOP 적용시, 요구사항이 명확히 user 관련 API로 한정되어 있어서 PointCut을 UsersController에만 명시적으로 적용했습니다.
- VO는 외부와의 통신 시 사용되는 객체로, DTO는 내부 계층 간 통신 시 사용되는 객체로 구분했습니다.
- mapper 클래스에서 VO와 DTO, Entity를 변환하는 로직을 담당하도록 했습니다.
- BaseResponse를 통해 요구사항에 맞는 일관된 응답 형식을 반환할 수 있도록 했습니다.
- 특수문자 필터 구현시 @Order(Ordered.HIGHEST_PRECEDENCE)를 통해 필터 체인의 가장 앞단에서 동작하여 불필요한 리소스를 방지하고자 했습니다.
- 상태 코드와 메시지는 BaseResponseStatus에 정의하였고, Enum을 통해 관리하도록 했습니다. 이를 통해 상태 코드 및 메시지를 일관된 방식으로 관리하고 확장에 용이하도록 설계했습니다.
- 테스트 코드 작성시, given, when, then 패턴을 사용하여 테스트 코드의 가독성을 높이고자 했습니다.
- 환경 변수의 경우 보안을 고려해야 했으나, 이번에는 코딩 테스트라는 점을 감안하여 따로 감추는 작업을 진행하지 않았습니다.
- 조회와 수정, 쓰기 부분에 대해 개별적인 트랜젝션을 적용했습니다.
- 수정은 더티체킹 방식으로 구현했습니다.

## 추가 라이브러리 설명
- Swagger : API 문서화 및 테스트를 위해 사용했습니다.
- Spring Batch : 로또 번호 당첨자 검수 Batch를 구현하기 위해 사용했습니다.
- h2database : 개발 시 사용할 데이터베이스로 사용했습니다.

## 어려웠던 점
- Spring Batch에 대한 공부가 부족하여 요구사항 구현에 어려움을 겪었습니다. 코드 작성은 완료했으나, 해당 부분 통합테스트 코드 검증에 실패했습니다.
- 특히 Batch 5.0.0 기준으로 환경을 설정하는데 많은 시간을 소비했습니다.
- 특수문자 필터 구현 시, 필터 체인의 동작 방식에 대한 이해가 부족하여 어려움을 겪었습니다. 관련한 공부를 다시 진행하고 정규화 로직을 구현하는데 많은 시간을 소비했습니다.
- Vo, Dto는 요구사항에 포함된 부분은 아니었으나, 계층 간 자신의 역할에 집중하도록 코드를 집중하도록 하다보니 작성하게 되었습니다. 다만 이에 대한 매핑 코드를 어디에 위치시켜야 할지 많은 고민을 했습니다. 결과적으로는 mapper 클래스가 이를 전담하도록 했습니다.
- 테스트 코드 작성 시, 테스트 코드의 가독성을 높이기 위해 given, when, then 패턴을 사용하였으나, 이를 적용하는데 많은 시간을 소비했습니다. 여러가지 상황을 고려하여 테스트 코드를 충분히 작성하지 못해 아쉬움이 남았습니다.


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

- [x] `@ControllerAdvice`, `@ExceptionHandler`를 이용하여, 잘못된 요청에 대한 응답을 처리한다. (4점)
  - [x] API를 호출할 때, 잘못된 요청이 들어오면 HTTP 400 상태의 `{"reason": 실제사유}`을 응답한다.
  - [x] API에 대한 실패 상황 통합 테스트 코드 작성
  - [x] 존재하지 않는 API 호출 시, HTTP 404 상태의 `{"reason": 실제사유}`을 응답한다.
- [x] Spring MVC 아키텍처와 Restful API를 준수하여 개발한다. (8점)
  - [x] `@RestController`, `@Service`, `@Repository`를 이용하여 개발한다.
  - [x] HTTP Method와 URI를 적절하게 사용하여 개발한다.
- [x] Clean Code를 준수하여 개발한다. (8점)
  - [x] 코드 스타일을 일관되고 명확하게 작성한다.
  - [x] 메소드와 클래스의 역할을 명확하게 작성한다.

### 2.2. 기본 문제 (50점)

- [x] user 등록 API 구현 (8점)
  - [x] `/users` API를 호출하면, `{"id": ?}`을 응답한다.
- [x] `/users` API에 대한 통합 테스트 코드 작성
- [x] user 조회 API 구현 (8점)
  - [x] `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.
  - [x] `/users/{id}` API에 대한 통합 테스트 코드 작성
- [x] user 수정 API 구현 (8점)
  - [x] `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.
  - [x] `/users/{id}` API에 대한 통합 테스트 코드 작성
- [x] 필터 구현 (12점)
  - [x] URL에 `? & = : //`를 제외한 특수문자가 포함되어 있을경우 접속을 차단하는 Filter 구현한다.
  - [x] `/users/{id}?name=test!!` API 호출에 대한 통합 테스트 코드 작성
- [x] Spring AOP를 활용한 로깅 구현 (14점)
  - [x] user 등록, 조회, 수정 API에 대해 Request시 Console에 Client Agent를 출력한다.

`user` 테이블

```csv
id,name
```

### 2.3. 구현 문제 (30점)

#### 로또 번호 발급 API 구현 (10점)
- [x] `POST /lottos` API를 호출하면, `{"numbers": [?, ?, ?, ?, ?, ?]}`을 응답한다.
- [x] `POST /lottos` API에 대한 통합 테스트 코드 작성

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
  - [x] 당첨자의 등수는 1등, 2등, 3등, 4등, 5등이 있다.
  - [x] 당첨자의 등수는 당첨 번호와 일치하는 번호의 개수로 판단한다.
  - [x] 당첨자 정보는 `winner` 테이블에 저장한다.
- [x] Batch는 매주 일요일 0시에 실행되도록 구현한다.
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
