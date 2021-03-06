[![Maintainability](https://api.codeclimate.com/v1/badges/3754a79b22b9430040ba/maintainability)](https://codeclimate.com/github/chenjiangtao/spring-boot-on-kubernetes)
[![Test Coverage](https://api.codeclimate.com/v1/badges/3754a79b22b9430040ba/test_coverage)](https://codeclimate.com/github/chenjiangtao/spring-boot-on-kubernetes)
[![Travis-ci](https://www.travis-ci.com/chenjiangtao/spring-boot-on-kubernetes.svg?branch=main)](https://www.travis-ci.com/chenjiangtao/spring-boot-on-kubernetes.svg?branch=main)


# spring-boot-on-kubernetes

 使用水平Pod自动缩放器和Kubernetes上的自定义指标自动缩放Spring Boot

目录：
- <a href="#Jasypt">Jasypt tools</a>

## 组件说明
ts-sbk-app      启动入口

ts-sbk-common   公共包

ts-sbk-data     数据库相关

## 目录结构˚

```bash
├── doc
├── kubernetes
│   ├── deployment
│   ├── ingrass
│   ├── kong
│   ├── monitoring
│   │   ├── custom-metrics-api
│   │   ├── kube-operators
│   │   ├── kube-state-metrics
│   │   ├── metrics-server
│   │   ├── new
│   │   ├── nfs-storage
│   │   ├── prometheus
│   │   ├── prometheus-node-exporter-sample
│   │   └── prometheus-whole
│   ├── mysql
│   └── redis
│       └── helm
└── src
├── main
│   ├── java
│   │   └── com
│   │       └── learnk8s
│   │           └── app
│   │               ├── controller
│   │               ├── model
│   │               └── queue
│   └── resources
│       ├── static
│       └── templates
```

## Prerequisites

You should have minikube installed.

You should start minikube with at least 4GB of RAM:

```bash
minikube start \
  --memory 4096 \
  --extra-config=controller-manager.horizontal-pod-autoscaler-upscale-delay=1m \
  --extra-config=controller-manager.horizontal-pod-autoscaler-downscale-delay=2m \
  --extra-config=controller-manager.horizontal-pod-autoscaler-sync-period=10s
```

> If you're using a pre-existing minikube instance, you can resize the VM by destroying it an recreating it. Just adding the `--memory 4096` won't have any effect.

You should install `jq` — a lightweight and flexible command-line JSON processor.

You can find more [info about `jq` on the official website](https://github.com/stedolan/jq).

## Installing Custom Metrics Api

Deploy the Metrics Server in the `kube-system` namespace:

```bash
kubectl create -f monitoring/metrics-server
```

After one minute the metric-server starts reporting CPU and memory usage for nodes and pods.

View nodes metrics:

```bash
kubectl get --raw "/apis/metrics.k8s.io/v1/nodes" | jq .
```

View pods metrics:

```bash
kubectl get --raw "/apis/metrics.k8s.io/v1/pods" | jq .
```

Create the monitoring namespace:

```bash
kubectl create -f monitoring/namespaces.yaml
```

Deploy Prometheus v2 in the monitoring namespace:

```bash
kubectl create -f monitoring/prometheus
```

Deploy the Prometheus custom metrics API adapter:

```bash
kubectl create -f monitoring/custom-metrics-api
```

List the custom metrics provided by Prometheus:

```bash
kubectl get --raw "/apis/custom.metrics.k8s.io/v1" | jq .
```

## Package the application

You package the application as a container with:

```bash
eval $(minikube docker-env)
docker build -t spring-boot-hpa .
```

## Deploying the application

Deploy the application in Kubernetes with:

```bash
kubectl create -f kube/deployment
```

You can visit the application at http://minkube_ip:32000

> (Find the minikube ip address via `minikube ip`)

You can post messages to the queue by via http://minkube_ip:32000/submit?quantity=2

You should be able to see the number of pending messages from http://minkube_ip:32000/metrics and from the custom metrics endpoint:

```bash
kubectl get --raw "/apis/custom.metrics.k8s.io/v1/namespaces/default/pods/*/messages" | jq .
```

## Autoscaling workers

You can scale the application in proportion to the number of messages in the queue with the Horizontal Pod Autoscaler. You can deploy the HPA with:

```bash
kubectl create -f kube/hpa.yaml
```

You can send more traffic to the application with:

```bash
while true; do curl -d "quantity=1" -X POST http://minkube_ip:32000/submit ; sleep 4; done
```

When the application can't cope with the number of incoming messages, the autoscaler increases the number of pods in 3 minute intervals.

You may need to wait three minutes before you can see more pods joining the deployment with:

```bash
kubectl get pods
```

The autoscaler will remove pods from the deployment every 5 minutes.

You can inspect the event and triggers in the HPA with:

```bash
kubectl get hpa spring-boot-hpa
```


## <a name="Jasypt">Jasypt tools</a>

`src/main/java/com/learnk8s/app/jasypt/JasyptTest.java`

生成Jasypt密码
```java
public class JasyptTest {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("8gKZNS6GwpJ2vgq");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("test");
        String password = textEncryptor.encrypt("test");
        System.out.println("username:ENC("+username+")");
        System.out.println("password:ENC("+password+")");
    }
}
```
>密码使用参考：[Spring boot 使用Jasypt加密用户名密码
](https://blog.csdn.net/evane1890/article/details/112967867)

# travis 使用
travis 依赖`mvnwn`本地命令文件

注意`mvnw`文件使用，依赖`.mvn`文件夹中的文件；使用和`mvn`命令一样
```shell
./mvnw clean package -DskipTests
```



## spring boot监控
健康指标：http://localhost:8090/actuator/health



## Notes

The configuration for metrics and metrics server is configured to run on minikube only.

**You won't be able to run the same YAML files for metrics and custom metrics server on your cluster or EKS, GKE, AKS, etc.**

Also, there are secrets checked in the repository to deploy the Prometheus adapter.

**In production, you should generate your own secrets and (possibly) not check them into version control.**

If you wish to run metrics and custom metrics server in production, you should check out the following resources:

- [Metrics server](https://github.com/kubernetes-sigs/metrics-server)
- [How to install Prometheus and the Promtheus Adapter](https://github.com/DirectXMan12/k8s-prometheus-adapter/blob/master/docs/walkthrough.md)


# utils包里有各种小工具

# tips
- 如果idea 提交代码到github出现443，在项目根目录执行：
```shell
git config --global --unset http.proxy
```











## 欢迎加入QQ群,在线答疑

![QQ群](./doc/qq_group.jpg )
