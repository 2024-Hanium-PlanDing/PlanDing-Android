# PlanDing-Android
일정관리와 그룹 스터디 플래너를 손쉽게 계획하고 공유하는 플랫폼, PlanDing입니다.

![로고](https://github.com/user-attachments/assets/97214830-506d-4b29-9f44-e80ef4acac3c)

| 기능 | 설명 | 기능 | 설명 |
|:----:|:----:|:----:|:----:|
| `로그인` | 소셜 로그인(카카오) | `개인 스케줄 목록` | 선택된 날짜의 개인 스케줄 조회 (list) |
| `개인 스케줄 생성` | 선택된 날짜에 개인 스케줄 생성 | `그룹 스케줄 목록` | 선택된 날짜의 그룹 스케줄 조회 (list) |
| `전체 스케줄 목록` | 선택된 날짜가 포함되는 주의 모든 스케줄 조회 (Bar) | `그룹 목록` | 사용자가 속한 그룹 조회 (list) |
| `그룹 생성` | 사용자가 그룹을 생성 | `그룹 상세 조회` | 그룹의 상세 페이지를 조회 |
| `그룹 스케줄 조회` | 선택된 날짜의 그룹 스케줄 조회 (list, Bar) | `그룹 스케줄 상세조회` | 그룹 스케줄의 상세 목록을 확인 |
| `그룹 초대` | 원하는 사용자를 그룹에 초대 | `채팅` | 그룹원들과 실시간 채팅 가능 |
| `할 일 추가` | 그룹 스케줄에 할 일을 추가하여 관리 | `그룹 즐겨찾기` | 그룹을 즐겨찾기에 등록 |
| `즐겨찾기 목록` | 즐겨찾기한 그룹을 조회 (list) | `그룹초대 요청 목록` | 받은 그룹 초대 목록 조회 (list) |
| `초대 요청 수락` | 그룹 초대 요청을 수락 | `초대 요청 거절` | 그룹 초대 요청을 거절 |


<div align="center"><h1>🎨 UI</h1></div>

<div align="center">
  <div>
    <img src="https://github.com/user-attachments/assets/b0b1b483-b126-4c69-aa18-d0fcd1b38874" width="600px" height="360px">
  </div>
  <br>
  <div>
    <img src="https://github.com/user-attachments/assets/afd4d7d7-f1d8-4410-8731-7d7e40148c57" width="600px" height="360px">
  </div>
</div>

<br>
<br>


|기술 항목|사용 기술|
|:----:|:----:|
|`UI`|Jetpack Compose|
|`디자인 패턴`|MVVM + MVI|
|`비동기 처리`|Flow, Coroutines|
|`구조`|Clean Architecture, Multi-Module|
|`의존성 주입`|Hilt|
|`실시간 통신`|WebSocket(krossbow)|
