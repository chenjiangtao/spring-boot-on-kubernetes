# kubernetes oprator
参考：https://operatorhub.io/

# 先安装oprator
./install.sh v0.17.0
- 或者手动执行
```
k create -f crds.yaml
k create -f olm.yaml  
```

# 查找operator
https://operatorhub.io/

# 两步使用operator
- 安装redis operator
kubectl create -f https://operatorhub.io/install/redis-operator.yaml
  查看
  k get all -n my-redis-operator

- 安装测试用的 redis cluster
k create -f redis-cluster.yaml
  
# redis-mater


