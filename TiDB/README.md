# TiDB 安装与配置

## 安装tidb
```shell
curl --proto '=https' --tlsv1.2 -sSf https://tiup-mirrors.pingcap.com/install.sh | sh
source .bash_profile
```

## 安装集群
```shell
tiup cluster deploy tidb-dev v4.0.6 ./topology.yaml -i ~/.ssh/id_rsa --user root
```
或使用install.sh
```shell
sh install.sh
```

## 安装集群里使用的配置
topology.yaml 常用的配置

topology-all.yaml 完整的配置模板

里面注掉的参数都有默认值，如果想修改，考出来放到topology.yaml即可