#!/bin/bash
#
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/metrics-server-amd64:v0.3.6
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/addon-resizer:1.8.11

docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/metrics-server-amd64:v0.3.6 k8s.gcr.io/metrics-server-amd64:v0.3.6
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/addon-resizer:1.8.11 k8s.gcr.io/addon-resizer:1.8.11

docker rmi registry.cn-hangzhou.aliyuncs.com/google_containers/metrics-server-amd64:v0.3.6
docker rmi registry.cn-hangzhou.aliyuncs.com/google_containers/addon-resizer:1.8.11