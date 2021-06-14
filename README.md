# MBTI Lovers 
[![Build Status](https://travis-ci.com/hyerijang/MBTI-Lovers.svg?branch=master)](https://travis-ci.com/hyerijang/MBTI-Lovers)

홍익대학교 2021 1학기 졸업전시회 출품작 - MBTI연애해 입니다.  
> 본 프로젝트는 2020년 2학기부터 2021년 1학기동안 진행된 **안드로이드 웹앱**  프로젝트입니다.


# Description
- 코로나로 인해 외출이 어려워지고 자연스러운 만남의 기회가 줄어들면서 소셜 데이팅 앱이 인기를 끌고 있습니다.
  저희는 기존 소셜 데이팅 앱 시스템에 최근 유행하고 있는 MBTI 성격유형을 결합하여 MBTI 채팅 앱을 개발하였습니다.

- 회원 가입 후 매칭을 통해 내 주변 유저들의 프로필을 조회하고 친구 신청을 할 수 있습니다. 친구가 된 유저는 친구목록에 추가되고 채팅방을 생성하여 서로 채팅할 수 있습니다.

# 실행 화면
<div>
<img src = "https://user-images.githubusercontent.com/46921979/121854586-551b3180-cd2d-11eb-843a-c2219d9e2312.png" width ="24%">
<img src = "https://user-images.githubusercontent.com/46921979/121854594-56e4f500-cd2d-11eb-8bd3-16a51caaab64.png" width ="24%">
<img src = "https://user-images.githubusercontent.com/46921979/121857950-138c8580-cd31-11eb-9c33-229a758f289a.png" width ="24%">
<img src = "https://user-images.githubusercontent.com/46921979/121854611-5b111280-cd2d-11eb-943d-9ba4114d0dd3.png" width ="24%">
</div>
# client (android)
## Environment
Tool : Android Studio
OS : window 10

## Dependencies
### Language
- Backend: - **java** openjdk 11.0.11

### Build
- **Gradle** 4.1.0

### Database
- firebase

# server

## Environment
Tool : intelliJ  
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


# Architecture
## client - server
<img src = "https://user-images.githubusercontent.com/46921979/121853662-25b7f500-cd2c-11eb-9fe5-918292bcc6fc.png" width="70%">

## server : chat
STOMP를 활용한 토픽 구독 방식으로 설계 
![MBTI 채팅 구조](https://user-images.githubusercontent.com/46921979/121853189-914d9280-cd2b-11eb-92b3-3712423c5aa8.png)


# Installation
배포 파일은 백그라운드로 실행됩니다.

    $ git clone https://github.com/hyerijang/MBTI-Lovers.git
    $ cd MBTI-Lovers/server
    $ ./deploy.sh


# Exit
배포를 종료하고 싶으신 경우 script폴더의 exit.sh를 실행해주세요.

    $ cd MBTI-Lovers/server/script
    $ ./exit.sh





