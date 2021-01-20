#!/usr/bin/env bash
helm install nfs-server-provisioner stable/nfs-server-provisioner -f nfs-config.yaml
kubectl create -f nfs-server-pv.yaml

#在所有节点上执行,安装nfs
#yum -y install nfs-utils
#systemctl restart rpcbind && systemctl enable rpcbind
#systemctl restart nfs && systemctl enable nfs

