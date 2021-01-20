# metrics-server/目录下的不再使用

metrics-server-3.6.yaml 可直接运行
metrics-server-eipwork-v3.7.yaml  可直接运行
metrics-server-v0.4.0.yaml 未测试

kube-dashboard-v2.0.yaml 可运行
kube-dashboard-v2.0-self-certs.yaml 使用创建证书的方式运行
运行时注意：

使用手动生成自定义证书
1.手动创建证书（目的是加载自定义证书）
kubectl create namespace kubernetes-dashboard
kubectl create secret generic kubernetes-dashboard-certs --from-file=/root/certs/new-for-dashboard -n kubernetes-dashboard

2. 改deployment文件
把recommended.yaml下载下来（加入下面两项）
- --tls-cert-file=/root/certs/kubecfg.crt
- --tls-key-file=/root/certs/kubecfg.key
tls-cert-file作用：
apiserver本身是一个http服务器，需要tls证书

如图：

3.证书生成两种方式 
x509证书crt文件的创建办法（截取k8s的证书）
mkdir certs & certs/
 grep 'client-certificate-data' ~/.kube/config | head -n 1 | awk '{print $2}' | base64 -d >> kubecfg.crt
 grep 'client-key-data' ~/.kube/config | head -n 1 | awk '{print $2}' | base64 -d >> kubecfg.key
查看证书内容
 openssl x509 -in /root/certs/kubecfg.crt -text -noout
* 也可以生成新证书
openssl genrsa -des3 -passout pass:over4chars -out dashboard.pass.key 2048
openssl rsa -passin pass:over4chars -in dashboard.pass.key -out dashboard.key
openssl req -new -key dashboard.key -out dashboard.csr
openssl x509 -req -sha256 -days 365 -in dashboard.csr -signkey dashboard.key -out dashboard.crt

