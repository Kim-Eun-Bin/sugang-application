# sugang application
Android에 기반을 둔 프로젝트로써, 사용자에게 개인별 수강 현황에 대한 정보를 입력받아 여러 가지로 활용합니다.
사용자가 기준에 미달인 성적을 받은 과목은 수강 후보 과목으로 분류합니다. 선 이수 과목이 있는 과목인지 아닌지를 구분하여 수강신청목록에 반영합니다.
또한, 사용자들의 졸업학점을 기준으로 어떤 종류의 과목이 몇 학점 부족한지 계산하여 전반적인 학점관리에 도움을 줍니다.

### 프로젝트 주제
사용자들에게 수강한 과목을 바탕으로 만들어진 시간표와 계산된 졸업 학점을 통해 남은 학점 관리를 쉽게 할 수 있도록 하는 안드로이드 어플리케이션입니다.

### 프로젝트 기간
2019.09 ~ 2019.12

### 개발 목표
- 강좌 검색 및 추천
- 수강 과목 목록 제공 및 학점 관리
- 학교 myiweb 시간표 연동

## 사용한 기술
- Java
- Android Studio
- Google firebase

## 기능
- 수강 신청 제안
- 사용자 정보 입력
- 게시판
- 캘린더
- 과제, 출석 알림
- 학점 관리
- 시간표 연동

## DB
![image](https://user-images.githubusercontent.com/56482682/156554317-d3c9e597-b3fe-457a-a76d-e09d082a4e21.png)

## UI / UX
### 로그인
로그인 화면입니다. 명지대학교 eclass 아이디, 비밀번호를 이용해 인증을 받아 로그인할 수 있습니다. <br /><br />
<img width="200" src="https://user-images.githubusercontent.com/56482682/156554456-cae7af64-7a45-4d1e-8ee8-cd8c08cfd017.png">

### Home (시간표)
로그인한 사용자의 수강중인 과목 시간표를 자동으로 띄워줍니다. <br /><br />
<img width="200" src="https://user-images.githubusercontent.com/56482682/156554516-9302b88d-fd45-4591-8c8a-abc47fa7b9ba.png">

### 수강 과목 정보
전공, 교양 등의 과목들에 대한 수강 이수 여부를 확인할 수 있습니다.
수강과목에 대한 정보를 입력하면 전체 평점을 확인할 수 있고, 재입력을 원할 시 초기화 버튼을 통해 수강과목 목록을 초기화할 수 있습니다. <br /><br />
<img width="200" src="https://user-images.githubusercontent.com/56482682/156554586-0f0aea99-18f7-473f-99dc-30dc00dbeac9.png">

### 게시판
사용자는 게시글 및 댓글을 작성할 수 있습니다. <br /><br />
<img width="500" src="https://user-images.githubusercontent.com/56482682/156554672-00051d10-d94c-4a87-96c8-45eace34d785.png">

### 마이페이지
현재까지 수강한 총 학점과 졸업까지 남은 공학인증 학점을 계산하여 보여줍니다. <br /><br />
<img width="200" src="https://user-images.githubusercontent.com/56482682/156554792-f74dae29-73ba-4ce5-8786-c0fa9f23d710.png">

