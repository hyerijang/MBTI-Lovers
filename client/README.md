# MBTI-Lovers
홍익대학교 2021 1학기 졸업전시회 출품작 - MBTI연애해 입니다.  
> 본 프로젝트는 2020년 2학기부터 2021년 1학기동안 진행된 모바일 앱 제작 프로젝트입니다.

> 본 프로젝트는 **안드로이드 웹앱**으로 구현되었으며 현재 리포지토리에는 **안드로이드** 관련 코드만을 포함하고 있습니다. 
> 웹서버 파트는  [[github]MBTI-Lovers-webServer](https://github.com/hyerijang/MBTI-Lovers-webServer) 에서 확인 가능합니다.


# Description
- 코로나로 인해 외출이 어려워지고 자연스러운 만남의 기회가 줄어들면서 소셜 데이팅 앱이 인기를 끌고 있습니다.
  저희는 기존 소셜 데이팅 앱 시스템에 최근 유행하고 있는 MBTI 성격유형을 결합하여 MBTI 채팅 앱을 개발하였습니다.

- 회원 가입 후 매칭을 통해 내 주변 유저들의 프로필을 조회하고 친구 신청을 할 수 있습니다. 친구가 된 유저는 친구목록에 추가되고 채팅방을 생성하여 서로 채팅할 수 있습니다.


# 안드로이드
[[github]MBTI-Lovers](https://github.com/hyerijang/MBTI-Lovers)
## Environment
Tool : Android Studio, BlueStacks, SourceTree  
OS : window 10

## Dependencies
### Language
- Backend: - **java** openjdk 11.0.11

### Build
- **Gradle** 4.1.0

### Database
- firebase

# 웹서버 [![Build Status](https://travis-ci.com/hyerijang/MBTI-Lovers-webServer.svg?branch=master)](https://travis-ci.com/hyerijang/MBTI-Lovers-webServer)
 [[github]MBTI-Lovers-webServer](https://github.com/hyerijang/MBTI-Lovers-webServer) 
## Environment
Tool : intelliJ , SourceTree  
OS : window 10 / ubuntu 18.04.5

## Dependencies
### Language
- Frontend : html, css , javascript,  thymeleaf, jquery
- Backend: - **java** openjdk 11.0.11

### Build
- **Gradle** 6.8.2


### Spring Boot
- Spring MVC
- Spring Data JPA
- Spring AOP


### Database
- MySQL 8.0.21
- firebase

### AWS
- EC2
- RDS
- S3
- CodeDeploy

### Deploy
- travisCI
- AWS CodeDeploy

# Installation
    $ git clone https://github.com/hyerijang/MBTI-Lovers-webServer.git
    $ cd MBTI-Lovers-webServer
    $ ./deploy.sh








