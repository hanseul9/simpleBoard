#!/usr/bin/env bash

# start.sh
# 서버 구동을 위한 스크립트

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/deploy
PROJECT_NAME=simpleBoard/simpleBoard

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/simpleBoard/build/libs/*.jar $REPOSITORY/"
cp $REPOSITORY/zip/simpleBoard/build/libs/*.jar $REPOSITORY/

# echo "> Build 파일 복사"
# echo "> cp $REPOSITORY/zip/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/deploy/"

# if ls $REPOSITORY/zip/$PROJECT_NAME/build/libs/*.jar 1> /dev/null 2>&1; then
#   cp $REPOSITORY/zip/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/deploy/
# else
#   echo "> Build 파일이 존재하지 않습니다."
#   exit 1
# fi


echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar \
    -Dspring.config.location=classpath:/application.yml,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-real.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
