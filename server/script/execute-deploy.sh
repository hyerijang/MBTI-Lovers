#!/bin/bash
echo "> travis-deploy.sh 실행"
sudo chmod 755 /home/ubuntu/app/travis/build/script/travis-deploy.sh
/home/ubuntu/app/travis/build/script/travis-deploy.sh > /dev/null 2> /dev/null < /dev/null &
echo "> travis-deploy.sh 실행 완료"