


```shell
docker run -d -p 9090:9090 -v ~/spring-boot-on-kubernetes/docker/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```
