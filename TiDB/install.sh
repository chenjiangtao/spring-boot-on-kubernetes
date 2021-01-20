# 做好所有服务器互信，自信(.ssh/authorized_keys添加各机器的id_rsa.pub)
tiup cluster deploy tidb-test v4.0.6 ./topology.yaml -i ~/.ssh/id_rsa --user root

