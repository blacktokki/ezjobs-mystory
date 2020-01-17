# ezjobs-mystory
<strong>ezjobs-mystory</strong>는 취업준비생의 자기소개서 검색과 작성을도와주는 웹 서비스 입니다.

## 프로젝트 개요
ezjobs-mystory는 자기소개서의 텍스트마이닝으로 수집한 자기소개서 정보들을 데이터베이스와 검색엔진에 저장하고 자기소개서의 각 문항과 내용의 문장들의 키워드로 자기소개서를 분류하였습니다. 사용자들은 다른 사람이 작성한 것을 참고하여 자신만의 자기소개서로 내용을 재구성 할 수 있으며, 자기소개서를 어떻게 쓰는지에 대한 틀을 제공하여 작성하는데에 있어 편의를 제공합니다.

## 프로젝트 목적 및 필요성
1. 글쓰기를 어려워하는 사람들에게 자기소개서는 많은 시간과 노력이 소모된다.
2. 자기소개서 준비에 부담을 줄여주고자 대학생 및 취업준비생들이 자기소개서를 작성할 때 도움을 주는 웹서비스를 개발한다.

## 사용자 정의
* 대학생 : 취직 관련해서 자기소개서 작성 경험이 없을 수 있기 떄문에 자기소개서 항목에 관한 정보가 필수적이다.
* 취업준비생 : 자신이 작성한 다수의 자기소개서를 저장할 수 있는 요소가 중요하다.
* 이직자 : 기존에 작성한 자기소개서를 활용할 수 있어야 한다.
 
## 사용기술
* Front-End
  * HTML5
  * Webjar(CSS+Javascript)
    * webjars-locator-core
    * jquery
      * Ajax
      * jquery-ui
        * jquery-ui-touch-punch
      * jquery-validation
    * bootstrap
    * ckeditor
      * autocomplete
      * wordcount

* Back-End
  * Spring Framework
    * Spring-Boot
    * Spring-Security
      * OAuth2
      * CAPTCHA
      * SHA-256
    * Spring-Data
      * JPA
  * JSP
    * EL
    * JSTL
    * JSPF
  * Lombok
  * Repository
    * MySQL
    * ElasticSearch
      * logstash
  * Server
    * Dev
      * Windows 10 Home
      * Windows Docker Toolbox
      * JDK 1.8
      * Git for Windows
    * Prod
      * Amazon Linux AMI release 2018.03
      * Linux Docker
      * JDK 1.8
      * Git

* Dev-Ops/Tools
  * Test
    * JUnit
    * Spring Devtools
  * Build/Deploy
    * Maven
      * Webjar
    * Docker
  * Git
    * Github
  * AWS
    * AWS-EC2
    * AWS-RDS
    * VPC
  * Anayltics
    * Google Analytics
    * ElasticSearch
      * Kibana

## 주요기능
 모든 페이지에 대한 상세한 설명은 [페이지 설명.md](https://github.com/blacktokki/ezjobs-mystory/blob/master/페이지%20설명.md)에 있습니다.

### 자기소개서 관리(/resume)
> __#AJAX #CKEditor #JQuery-UI #Tagging #Autocomplete__

<img src="https://user-images.githubusercontent.com/39031723/72608474-1893ac80-3966-11ea-9623-656abf2a45b7.png" width="66%" height="66%"/>

 자기소개서의 작성 목록을 보여주며, 새 자기소개서 버튼을 누르면 새로 작성이 가능하고 목록에서 제목을 누르면 수정을 할 수 있습니다. 작성 창에서는 문장의 시작단어(Ex. 저는,또한)를 입력만 해도 그와 관련된 문장들이 나오며 클릭하면 내용이 옮겨지게 됩니다. 

 자동완성으로 완성된 자기소개서는 기존의 자소서와의 유사도가 걸리게 됩니다. 저희는 검토하기-유사도검사를 통해 그 문장이 얼마나 어느부분에 부분 유사, 완전 유사를 판별하고 퍼센트로 띄워주는 유사도 검사를 만들고, 주황부분은 부분유사, 빨간부분은 완전 유사를 표시하고 몇 퍼센트로 유사한지를 보실 수가 있습니다.

 이렇게 높은 유사도가 나와서 유사도를 벗어나기 위해 직접 단어를 입력하여 교정할 수 있지만, 검토하기-단어교체 기능을 통해 유사한 의미를 가진 말을 목록에서 고를 수 있고, 문맥에 맞게 자신이 원하는 말을 추가할 수 있습니다. 또 말의 흐름도 손쉽게 드래그 앤 드랍 방식으로 수정할 수 있습니다. 수정 후  다시 유사도 검사를 하면 바뀐 유사도를 확인 할 수 있습니다.

### 회원페이지(/user/**)
> __#Spring-Security  #OAuth2 #CAPTCHA #SHA-256 #E-mail #Validation__

<img src="https://user-images.githubusercontent.com/39031723/72607524-3b24c600-3964-11ea-841e-eaccf5ad1ef6.png" width="66%" height="66%"/>

 회원가입과 로그인을 할 수 있으며,  로그인 후 그 회원의 입력 정보를 스스로 확인 할 수 있습니다. Spring-Security를 기반으로 OAuth를 통한 소셜 로그인과 CAPTCHA를 포함한 일반로그인이 구현되어 있습니다. 회원가입 및 회원 수정에는 JQuery-Validation으로 폼 입력값을 검증합니다. 회원정보에 입력했던 E-mail을 통해서 비밀번호 찾기가 가능하며 임시 비밀번호가 등록된 이메일로 발송됩니다.

### 관리자페이지(/admin/**)
> __#CRUD #Paging #Search__

<img src="https://user-images.githubusercontent.com/39031723/72607526-3b24c600-3964-11ea-95b9-d0abe6f17f6f.png" width="66%" height="66%"/>

 관리자 페이지에서는 회원관리, 게시글 관리, 태그관리 등을 할 수 있습니다.

### 게시판(/board/**)
> __#CRUD #Paging__

<img src="https://user-images.githubusercontent.com/39031723/72607769-9951a900-3964-11ea-97c9-d351db9016fc.png" width="66%" height="66%"/>

 커뮤니티 게시판에서는 회원과 비 회원 둘 다 이용가능한 게시판으로 서로의 정보를 교환하는 공간입니다.

## 구성원 및 구성원별 수행 업무

### blacktokki(Dong Hyeok)
* 담당 페이지 : 자기소개서 페이지
* 담당 업무 : 프로젝트 PM,데이터베이스 설계, 배포,CKEditor-custom,spring boot config, spring-security config, maven dependency 관리, AWS 관리,자기소개서 UI 설계 및 구현,데이터 수집(자기소개서)
* 구현 기능 : 게시판CRUD, 자기소개서 CRUD, 자기소개서 관리 기능, 자기소개서 작성 기능, Oauth2 소셜로그인 기능, 임시 비밀번호 발급 기능, CAPTCHA 연동

### minho1004
* 담당 페이지 : 회원 페이지
* 담당 업무 : 회원 페이지 UI 설계 및 구현,대외문서 관리(캡스톤 디자인, 졸업작품), 발표자료 준비, 데이터 수집(동의어 사전)
* 구현 기능 : 일반 로그인 기능, 회원가입 기능, 회원정보 수정 기능, SHA-256암호화

### MusongChoi
* 담당 페이지 : 게시판 페이지,관리자 페이지
* 담당 업무 : 게시판 페이지 UI 설계 및 구현,관리자 페이지 UI설계 및 구현
* 구현 기능 : 커뮤니티 게시판(목록,읽기,작성), 사이트 관리기능(사용자, 자기소개서, 게시글,태그)

### seoljinseong
* 담당 페이지 : 메인페이지,서비스 안내 페이지
* 담당 업무 : 메인페이지 UI 및 설계 구현,서비스 안내 페이지 UI설계 및 구현, 유스케이스 설계, 발표자료 디자인
* 구현 기능 : 메인페이지(광고 배너, 검색, 인기 게시글, 홈페이지 소개) 서비스 안내(공지사항, QnA,FaQ)

### sjh1675(Ji Hun)
* 담당 페이지 : 검색 페이지
* 담당 업무 : 검색 페이지 UI 설계 및 구현, 회의록 관리, 수집 데이터 가공, 데이터 시각화
* 구현 기능 : 검색 기능(검색창, 태그 검색, 키워드 검색, 페이징) 대시보드 기능(jqcloud, d3.js)
