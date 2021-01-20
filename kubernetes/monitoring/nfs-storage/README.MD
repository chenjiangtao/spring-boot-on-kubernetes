# k8s 安装 nfs-storage

nfs-storage 的作用：使用helm安装redis时会使用动态storage,所以必须配置

### 在所有节点上执行,安装nfs

```shell
yum -y install nfs-utils
systemctl restart rpcbind && systemctl enable rpcbind
systemctl restart nfs && systemctl enable nfs
```

# 执行shell
sh install.sh
