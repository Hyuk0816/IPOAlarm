## 공모주 알리미 API 명세서 

## 1. 공모주 데이터 조회 

### Description
- 조회 조건에 따라 공모주 데이터를 보여준다

### Request
- URL: ```/api/ipo/data```
- Method: GET
- URL Params: 
  - ipoName: String (공모주 이름)
  - start : String (청약 시작 날짜)
  - end : String (청약 마감 날짜)
  - pageable: object (페이지 오브젝트)
    - size: int (한 페이지의 요소 개수)

### Response

```json
{
  "content": [
    {
      "ipoName": "두산 로보틱스",
      "ipoPrice": "24000 ~ 26000",
      "confirmPrice": "26000",
      "competitionRate": "750:1",
      "securities": "삼성증권,한국투자,KB증권",
      "startDate": "2024-09-04T02:30:55.084Z",
      "endDate": "2024-09-04T02:30:55.084Z"
    }
  ],
  "pageNumber": 0,
  "pageSize": 0,
  "totalElements": 0,
  "totalPages": 0
}
```

## 2. 공모주 상세 조회 

### Description 
- 하나의 공모주에 대한 상세 정보 조회 
- 공모주 상세 페이지의 댓글도 같이 조회 

### Request
- URL: ```/api/ipo_detail/data/{ipoName}```
- Method: GET
- URL Params:
  - ipoName: String (공모주 이름)

### Response 
```json
{
  "ipoName": "string",
  "industry": "string",
  "representative": "string",
  "revenue": "string",
  "netProfit": "string",
  "totalOfferedShares": "string",
  "ipoComments": [
    {
      "id": 0,
      "ipoComments": "string",
      "regDate": "string"
    }
  ]
}
```

## 3. 공모주 상세 페이지 댓글 

### Description
- 공모주 상세 페이지에 댓글 작성 기능

### Request
- URL: ```/api/ipo_comments/comment/{ipoName}```
- Method: POST
- URL Params:
  - ipoName: String (공모주 이름)
- Request Body:
  - ipoComment : String (댓글 내용)

### Response
```json
{
  "statusCode": "201",
  "statusMsg": "댓글이 작성되었습니다."
}
```

## 4. 상장 주식 조회 

### Description
- 상장 주식 데이터를 조건에 따라 조회 

### Request
- URL: ```/api/listing_shares/data```
- Method: GET
- URL Params:  
  - pageable: Object (페이징 조건)
      - size: int (한 페이지의 요소 개수)
  - Request Body:
    - requestVO: Object(조회 조건)
    ```json
    {
      "ipoName": "아이스크림미디어", //상장 주식 이름 
      "listingStartDate": "2024-09-04T04:57:46.624Z", //상장일 시작 기준일 
      "listingEndDate": "2024-09-04T04:57:46.624Z", //상장일 끝 기준일 
      "minChangeRatePrevious": -1.8, //전일 대비 가격 변동율 검색 시작 값
      "maxChangeRatePrevious": 3.8, //전일 대비 가격 변동율 검색 끝 값
      "minChangeRateOfferingPrice": 100, //공모가격 대비 가격 변동율 검색 시작 값
      "maxChangeRateOfferingPrice": 300, //공모가격 대비 가격 변동율 검색 끝 값
      "minChangeRateOpeningToOfferingPrice": 200, //공모가격 대비 상장일 당일 가격 변동율 검색 시작 값
      "maxChangeRateOpeningToOfferingPrice": 400 //공모가격 대비 상장일 당일 가격 변동율 검색 끝 값
    }
    ```

### Response:
```json
{
  "content": [
    {
      "ipoName": "string",
      "listingDate": "2024-09-04",
      "currentPrice": "string",
      "changeRatePrevious": 0,
      "offeringPrice": "string",
      "changeRateOfferingPrice": 0,
      "openingPrice": "string",
      "changeRateOpeningToOfferingPrice": 0,
      "closingPriceFirstDay": "string"
    }
  ],
  "pageNumber": 0,
  "pageSize": 0,
  "totalElements": 0,
  "totalPages": 0
}
```

## 5. 전월 공모가 대비 상장 게시날 수익률 조회 

### Description
- 전월 공모가 대비 상장 게시날 수익률 조회 
- 하나의 공모주의 청약 가격이 15,000원 상장 게시날 가격이 30,000원이라 가정하면 수익률은 100% 
- 전월에 상장한 공모주의 공모가격 대비 상장 게시날 수익률의 평균을 제공

### Request
- URL : ```/api/listing_shares/previous_month_profit```
- Method: GET
- URL Params : NONE


### Response
```json
{
  "monthlyProfit": 68.98989
}
```

## 6. 연도별 월 공모가 대비 상장 게시날 수익률 조회 

### Description
- 연도별 각 월마다 공모가 대비 상장 게시날 수익률 조회
- 파라미터로 2023년도를 전달했다면 2023년에 월별로 공모가 대비 상장 게시날 수익률 조회

### Request 
- URL: ```/api/listing_shares/monthly_profit```
- Method: GET 
- URL Params:
  - year : int (연도)

### Response
```json
//Request: http://localhost:8080/api/listing_shares/monthly_profit?year=2023
[
  58.14, //1월 
  81.6975,
  38.3975,
  10.138333333333334,
  43.73166666666666,
  57.72833333333333,
  108.86666666666666,
  59.06999999999999,
  42.995000000000005,
  79.87636363636365,
  49.61578947368421,
  158.52272727272728 //12월 
]
```

## 7. 공모주 청약일자 알람 신청

### Description
- 공모주 청약일정 알람을 신청한다
- 현재는 카카오톡 캘린더 API와 연동이 되어있지 않아 DB에만 저장된다.

### Request 
- URL: ```/api/alarm/data```
- Method: POST 
- URL Params:
  - ipoName: String (공모주 이름)

### Response
```json
//Request: http://localhost:8080/api/alarm/data?ipoName=쓰리빌리언
{
  "statusCode": "201",
  "statusMsg": "알림이 등록되었습니다"
}
```

### Error

#### 1. 이미 등록된 알람인 경우
```json
//Request : http://localhost:8080/api/alarm/data?ipoName=아이언 디바이스
{
"apiPath": "uri=/api/alarm/data",
"errorCode": "INTERNAL_SERVER_ERROR",
"errorMessage": "이미 등록된 알람 입니다. 아이언디바이스",
"errorTime": "2024-09-04T14:30:21.2970998"
}
```

#### 2. 마감일이 지난 공모주를 알람 신청 했을 경우
```json
{
  "apiPath": "uri=/api/alarm/data",
  "errorCode": "BAD_REQUEST",
  "errorMessage": "마감일이 지난 공모주는 알림 등록 할 수 없습니다.",
  "errorTime": "2024-09-04T14:31:38.9874439"
}
```

#### 3. 청약 진행 중인 공모주를 알람 신청 했을 경우 
```json
{
  "apiPath": "uri=/api/alarm/data",
  "errorCode": "BAD_REQUEST",
  "errorMessage": "현재 청약 진행 중인 건은 알람 등록 할 수 없습니다. 청약 진행하시면 됩니다!",
  "errorTime": "2024-09-04T14:35:39.8163732"
}
```

## 8. 상장일 알람 신청

### Description 
- 공모주 상장 일정 알람 신청
- 현재는 카카오톡 캘린더 API와 연동하지 않아 DB에만 저장

### Request
- URL: ```/api/listing_share_alarm/alarm/{listingShares}```
- Method: POST
- URL Params:
  - listingShares: String (청약예정 공모주 이름)

### Response
```json
//Request: http://localhost:8080/api/listing_share_alarm/alarm/미래에셋비전스팩7호
{
  "statusCode": "201",
  "statusMsg": "알람이 등록 되었습니다."
}
```

### Error

#### 1. 이미 상장한 공모주를 알람 신청 했을경우
```json
{
  "apiPath": "uri=/api/listing_share_alarm/alarm/%EC%9D%B4%EC%97%94%EC%85%80",
  "errorCode": "BAD_REQUEST",
  "errorMessage": "이미 상장 된 공모주 입니다.",
  "errorTime": "2024-09-04T14:43:54.8895579"
}
```

## 9.유저 정보 조회 

### Description 
- 유저의 이메일 및 프로필 사진 조회

### Request
- URL: ```/api/mypage/data```
- Method: GET

### Response
```json
{
  "email": "test@test.com",
  "image": "imgage"
}
```

## 10. 등록한 공모주 알림 조회 

### Description
- 유저가 등록한 공모주 알림 조회 

### Request
- URL: ```/api/mypage/myalarm```
- Method: GET 

### Response
```json
[
  {
    "ipoName": "쓰리빌리언",
    "ipoPrice": "4,500~6,500",
    "confirmPrice": "-",
    "securities": "한국투자증권",
    "startDate": "2024-10-11T10:00:00.000+09:00"
  },
  {
    "ipoName": "아이언디바이스",
    "ipoPrice": "4,900~5,700",
    "confirmPrice": "-",
    "securities": "대신증권",
    "startDate": "2024-09-09T10:00:00.000+09:00"
  },
  {
    "ipoName": "미래에셋비전스팩7호",
    "ipoPrice": "2,000~2,000",
    "confirmPrice": "2,000",
    "securities": "미래에셋증권",
    "startDate": "2024-09-02T10:00:00.000+09:00"
  },
  {
    "ipoName": "이엔셀",
    "ipoPrice": "13,600~15,300",
    "confirmPrice": "15,300",
    "securities": "NH투자증권",
    "startDate": "2024-08-12T10:00:00.000+09:00"
  },
  {
    "ipoName": "엠83",
    "ipoPrice": "11,000~13,000",
    "confirmPrice": "16,000",
    "securities": "신영증권,유진투자증권",
    "startDate": "2024-08-12T10:00:00.000+09:00"
  },
  {
    "ipoName": "대신밸런스스팩18호",
    "ipoPrice": "2,000~2,000",
    "confirmPrice": "2,000",
    "securities": "대신증권",
    "startDate": "2024-08-09T10:00:00.000+09:00"
  },
  {
    "ipoName": "유라클",
    "ipoPrice": "18,000~21,000",
    "confirmPrice": "21,000",
    "securities": "키움증권",
    "startDate": "2024-08-06T10:00:00.000+09:00"
  }
]
```

## 11. 등록한 상장일 알림 조회 

### Description
- 유저가 등록한 상장일 알림 조회 

### Request
- URL: ```/api/mypage/myListingAlarm```
- Method: GET 

### Response
```json
  {
    "listingShares": "미래에셋비전스팩7호",
    "listingDate": "2024-09-11",
    "offeringPrice": "2,000"
  }
```

