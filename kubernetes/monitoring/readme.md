# monitoring

## kube-dashboard
*使用new文件夹里的`kube-dashboard...`文件*

## metrices-server 
使用`new`文件夹里的yaml文件创建

# 弃用的
- metrices-server 


# 未完成的
- springboot监控metrice监控


## prometheus-operator
使用helm安装，里面自带了grafana的模板，非常完整。
```shell
helm install prometheus-operator apphub/prometheus-operator -ndev
```

*其它prometheus弃用*