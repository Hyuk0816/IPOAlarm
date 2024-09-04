# IPOAlarm
공모주 알리미 서비스 

## 1. 개요
- 공모주 알리미는 공모주 청약일 및 공모 가격, 확정 가격, 경쟁률, 주간사 등의 정보를 제공하며 카카오톡 캘린더 API와 연동하여 공모주 청약일에 알려주는 서비스이다.
- 공모주 알리미는 더 나아가 상장 예정인 공모주에 대한 상장일, 현재 가격, 전일비, 공모가, 공모가 대비 등락률, 시초가, 공모가 대비 시초가 등락률, 첫날 종가 등의 정보를 제공한다

## 2. 데이터 수집 및 저장 방식
- 데이터를 가져오는 웹 크롤러를 AWS ECR 이미지로 빌드하여 AWS Lambda 함수와 EventBridge로 매일 새벽 S3에 csv 파일을 저장한다. S3에 파일이 저장되면 구독해 놓은 SNS 엔드포인트로 메일이 발송된다.
  - (Repo: https://github.com/Hyuk0816/IPO-Crawling-Lambda)

- S3에 저장된 각 csv 파일을 Apache commons-csv 를 이용하여 RDB에 저장한다(@Schedule 적용). 이 때 중복을 고려하여 변경된 데이터는 Update되고 새로운 데이터는 insert 되게 한다 (hashCode 이용)
- DB insert는 따로 분리하여 Docker Container로 실행 중(Repo: https://github.com/Hyuk0816/IPO_DB_Insert_Module)

# DOCS

### API 명세 문서

### Swager 


