#!/bin/bash

REPOSITORY=/home/ubuntu/app/travis/build

echo "> travis 프로젝트 Build 시작"

echo "> Build 파일 복사"

cp $REPOSITORY/build/libs/*.war $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f MBTI-Lovers-web)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
	    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
    else
	        echo "> kill -2 $CURRENT_PID"
		    kill -9 $CURRENT_PID
		        sleep 5
		fi

		echo "> 새 어플리케이션 배포"

		WAR_NAME=$(ls $REPOSITORY/ |grep 'MBTI-Lovers-web' | tail -n 1)

		echo "> WAR Name: $WAR_NAME"

		echo "> nohup로 실행"
		cd $REPOSITORY
		chmod 755 $REPOSITORY/$WAR_NAME
		nohup java -jar $REPOSITORY/$WAR_NAME &
