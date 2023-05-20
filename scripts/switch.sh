#!/usr/bin/env bash

# switch.sh
# nginx 연결 설정 스위치

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    # nginx와 연결한 주소 생성
    # | sudo tee ~ : 앞에서 넘긴 문장을 service-url.inc에 덮어씀
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> 엔진엑스 Reload"
    # nignx reload. restart와는 다르게 설정 값만 불러옴
    sudo service nginx reload
}

# function switch_proxy() {
#     IDLE_PORT=$(find_idle_port)
#     CURRENT_PORT=$(sudo nginx -T | grep -oP "(?<=listen\s)[0-9]+")
#     echo "> 현재 Port: $CURRENT_PORT"

#     if [ "$IDLE_PORT" == "$CURRENT_PORT" ]; then
#         echo "> Port 가 변경되지 않아 nginx reload 건너뜁니다."
#         return
#     fi

#     echo "> Port 전환"
#     # nginx와 연결한 주소 생성
#     # | sudo tee ~ : 앞에서 넘긴 문장을 service-url.inc에 덮어씀
#     echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

#     echo "> 엔진엑스 Reload"
#     # nignx reload. restart와는 다르게 설정 값만 불러옴
#     sudo service nginx reload
# }

#switch_proxy
