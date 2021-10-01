# 修改
可修改的master名：
masterSet: mymaster

副本：
replicaCount: 3

开启
sentinel:
    enabled: true

设密码
redis:
password:

# 安装
```shell
helm install -ndev redis-sentinel bitnami/redis --values values.yaml
```


# 查看

看主从关系
```shell
kubectl logs redis-sentinel-node-0 sentinel -ndev
```
看redis主机信息 port is 6379
```shell
kubectl exec -i -t redis-sentinel-node-0 -ndev -c redis -- redis-cli -h 10.100.176.247 -p 6379 -a Redis@123 info replication
```

查看sentinel ,port is 26379
```shell
kubectl exec -i -t redis-sentinel-node-0 -ndev -c redis -- redis-cli -h 10.100.176.247 -p 26379 -a Redis@123 info
#see the sentinel master info(master name etc. )
kubectl exec -i -t redis-sentinel-node-0 -ndev -c redis -- redis-cli -h 10.100.176.247 -p 26379 -a Redis@123 sentinel masters 
```

