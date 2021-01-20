先安装kong

# 设置PROXY_IP
```
export PROXY_IP=$(minikube service -n kong kong-proxy --url | head -1)
```
# 创建echo server
```
kubectl create -f echo-server.yaml
```
# 再创建基础demo
```
kubectl create -f kong-demo.yaml
```
测试：
curl -i $PROXY_IP/foo
>HTTP/1.1 200 OK
 Content-Type: text/plain; charset=UTF-8
 Transfer-Encoding: chunked
 Connection: keep-alive
 Date: Wed, 09 Dec 2020 07:40:05 GMT
 Server: echoserver
 X-Kong-Upstream-Latency: 0
 X-Kong-Proxy-Latency: 1
 Via: kong/2.2.1

# 使用 kong plugin
## correlation-id plugin
```
kubectl create -f kong-plugin-correlation-id.yaml
kubectl create -f kong-plugin-correlation-id-test.yaml
```
测试
```
curl -i -H "Host: example.com" $PROXY_IP/bar/sample
```
## rate-limiting plugin
```
kubectl create -f kong-plugin-rate-limiting.yaml
kubectl create -f kong-plugin-rate-limiting-test.txt
```
测试
```
curl -i -H "Host: example.com" $PROXY_IP/bar/sample
curl -I $PROXY_IP/foo
```
>HTTP/1.1 200 OK
Content-Type: text/plain; charset=UTF-8
Connection: keep-alive
Date: Fri, 21 Jun 2019 18:25:49 GMT
Server: echoserver
X-RateLimit-Limit-minute: 5
X-RateLimit-Remaining-minute: 2
X-Kong-Upstream-Latency: 0
X-Kong-Proxy-Latency: 4
Via: kong/1.1.2

里面多了RateLimit参数

# 详细执行步参考：
https://docs.konghq.com/kubernetes-ingress-controller/1.0.x/guides/getting-started/