## 开启prometheus
```shell
docker run -d -p 9090:9090 -v ~/spring-boot-on-kubernetes/docker/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```

## 开启grafana
macOS
```shell
brew install grafana
```
使用docker
```shell
docker run -d -p 3000:3000 grafana/grafana
```