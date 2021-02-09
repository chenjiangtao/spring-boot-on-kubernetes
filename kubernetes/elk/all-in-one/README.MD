# Deploy Elasticsearch 
##创建 elasticsearch
see [Deploy an Elasticsearch cluster
](https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-deploy-elasticsearch.html)
```shell
cat <<EOF | kubectl apply -f -
apiVersion: elasticsearch.k8s.elastic.co/v1
kind: Elasticsearch
metadata:
  name: quickstart
spec:
  version: 7.10.2
  nodeSets:
    - name: default
      count: 1
      config:
        node.store.allow_mmap: false
EOF        
```

访问es
```shell
kubectl port-forward service/quickstart-es-http 9200
```

使用url，用户：`elastic`

`https://localhost:9200`

获取密码
```shell
PASSWORD=$(kubectl get secret quickstart-es-elastic-user -o go-template='{{.data.elastic | base64decode}}')
```

测试
```shell
curl -u "elastic:$PASSWORD" -k "https://localhost:9200"
```

## 部署Kibana 
see [Deploy a Kibana instance](https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-deploy-kibana.html)

创建kibana
```shell
cat <<EOF | kubectl apply -f -
apiVersion: kibana.k8s.elastic.co/v1
kind: Kibana
metadata:
  name: quickstart
spec:
  version: 7.10.2
  count: 1
  elasticsearchRef:
    name: quickstart
EOF
```

访问kibana

开放端口
```shell
kubectl port-forward service/quickstart-kb-http 5601
```

使用url访问，用户：`elastic`

`https://localhost:5601`

获取密码
```shell
kubectl get secret quickstart-es-elastic-user -o=jsonpath='{.data.elastic}' | base64 --decode; echo
```

## 升级节点
[Upgrade your deployment](https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-upgrade-deployment.html#k8s-upgrade-deployment)

For example, grow the cluster to three es nodes:
```shell
cat <<EOF | kubectl apply -f -
apiVersion: elasticsearch.k8s.elastic.co/v1
kind: Elasticsearch
metadata:
  name: quickstart
spec:
  version: 7.10.2
  nodeSets:
  - name: default
    count: 3
    config:
      node.store.allow_mmap: false
EOF
```

## filebeat example
```shell
k create -f filebeat.yaml
```

## dashboard look

## busybox测试
```shell
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: counter
spec:
  containers:
  - name: count
    image: busybox
    args: [/bin/sh, -c,
            'i=0; while true; do echo "hello world: $i: $(date)"; i=$((i+1)); sleep 3; done']
EOF        
```

