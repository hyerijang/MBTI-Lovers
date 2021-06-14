#!/bin/bash

#/server
cd ..
REPOSITORY="$( cd "$( dirname "$0" )" && pwd -P )"
echo "> Git Pull"

git pull

echo "> 프로젝트 Build 시작"

sudo chmod 777 ./gradlew

sudo ./gradlew build 


echo "> Build 파일 복사"

cp ./build/libs/*.war $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f MBTI-Lovers-webServer)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
	    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
    else
	        echo "> kill -2 $CURRENT_PID"
		    kill -9 $CURRENT_PID
		        sleep 5
		fi

		echo "> 새 어플리케이션 배포"

		WAR_NAME=$(ls $REPOSITORY/ |grep 'MBTI-Lovers-webServer' | tail -n 1)

		echo "> WAR Name: $WAR_NAME"
		
		echo "> 백그라운드로 실행합니다."
		echo "> 종료를 원하시면 script 폴더의 exit.sh를 실행시켜주세요"
		cd $REPOSITORY
		chmod 755 $REPOSITORY/$WAR_NAME
		nohup java -jar $REPOSITORY/$WAR_NAME &
