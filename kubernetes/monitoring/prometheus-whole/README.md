https://github.com/peppiii/prometheus-k8s

# Description
Monitoring Prometheus in Kubernetes

# How to User
- please install kubectl in laptop or server
- clone repo : git clone git@github.com:peppiii/prometheus-k8s.git
- use to folder cd prometheus-k8s
- please read Noted in Readme before execute Prometheus

# Step Apply
```
kubectl apply -f monitoring.yml
kubectl apply -f prometheus-rules-config.yaml
kubectl apply -f prometheus-rules-alertmanager-configmap.yml
kubectl apply -f prometheus-alertmanager-configmap.yml
kubectl apply -f prometheus-storage.yml
kubectl apply -f prometheus-pvc.yml
kubectl apply -f prometheus-node-exporter-daemonset.yml
kubectl apply -f prometheus-kubestate-metrics.yml ( Optional )
kubectl apply -f prometheus-roles-rbac.yml
kubectl apply -f prometheus-alertmanager-deployment.yml
kubectl apply -f prometheus-alertmanager-service.yml
kubectl apply -f prometheus-deployment.yaml
kubectl apply -f prometheus-service.yaml
```
# Configuration Prometheus
| Configuration | Justification | file | How | 
| ------ | ------ | ------ | ------ |
| allowVolumeExpansion | extend Volumet | [prometheus-storage](https://github.com/peppiii/prometheus-k8s//blob/master/prometheus-storage.yml) | true
| type Storage | pd-standard or pd-ssd | [prometheus-storage](https://github.com/peppiii/prometheus-k8s//blob/master/prometheus-storage.yml) | pd-standard or pd-ssd
| size storage | default 20Gi | [storage](https://github.com/peppiii/prometheus-k8s//blob/master/prometheus-pvc.yml) | 20Gi
| namespaces | monitoring | [namespaces](https://github.com/peppiii/prometheus-k8s/blob/master/monitoring.yml) | monitoring
| prometheus-scrapes | prometheus-scrapes | [rules-prometheus](https://github.com/peppiii/prometheus-k8s/blob/master/prometheus-rules-config.yaml) | true
| alertmanager | alertmanager | [alert-manager](https://github.com/peppiii/prometheus-k8s/blob/master/prometheus-rules-alertmanager-configmap.yml) | true
| retention storage | retention storage | [retention](https://github.com/peppiii/prometheus-k8s/blob/master/prometheus-deployment.yaml) | true
# Error Prometheus
| Error | Justification | How |
| ------ | ------ | ------ |
| Opening storage failed invalid block sequence: block time ranges overlap after | extend volume | please check line  13 in file [prometheus-pvc.yml](https://github.com/peppiii/prometheus-k8s/blob/master/prometheus-pvc.yml)|
| Permission denied writing to mount using volumeClaimTemplate | securityContext |  The SecurityContext allows you so specify the user and group you want your Pod to run as, which will allow you to predict the permissions your volume will need  [permission](https://github.com/prometheus-operator/prometheus-operator/issues/966)|
| persistent volumes permission denied | securityContext |  The SecurityContext allows you so specify the user and group you want your Pod to run as, which will allow you to predict the permissions your volume will need [permission](https://github.com/prometheus-operator/prometheus-operator/issues/966)
| Container Creating long 10 menit | check describe pods in namespaces monitoring | kubectl describe pods pods-id -n monitoring |
| Error on statfs() system call permission denied" source="filesystem_linux.go:57" | run as root in daemonset | change security context in [daemon-set](https://github.com/peppiii/prometheus-k8s/blob/master/prometheus-node-exporter-daemonset.yml) to runAsUser: 0 |
| diskstats collector failed after 0.000629s: invalid line for /host/proc/diskstats | disable diskstats | --no-collector.diskstats, refer to [diskstats](https://stackoverflow.com/questions/53379593/starting-node-exporter-while-disable-ling-a-specific-collector-collector-disks)
| Error parsing commandline arguments: unexpected enable prometheus: error: unexpected enable | remove command args in prometheus deployments | remove - '--web.enable-admin-api=enable' to - '--web.enable-admin-api' refer to [prometheus](https://github.com/peppiii/prometheus-k8s/blob/master/prometheus-deployment.yaml)

# Clear Data
Delete Time Series Metrics, Use the following syntax to delete all time series metrics that match some label:
`````
$ curl -X POST -g 'http://localhost:9090/api/v1/admin/tsdb/delete_series?match[]={foo="bar"}'
`````
*  To delete time series metrics that match some job or instance, run:

``````
$ curl -X POST -g 'http://localhost:9090/api/v1/admin/tsdb/delete_series?match[]={job="node_exporter"}'
$ curl -X POST -g 'http://localhost:9090/api/v1/admin/tsdb/delete_series?match[]={instance="192.168.0.1:9100"}'
``````

*  To delete all data from Prometheus, run:

`````
$ curl -X POST -g 'http://localhost:9090/api/v1/admin/tsdb/delete_series?match[]={__name__=~".+"}'
`````

Note that the above API calls don’t delete data immediately.
The actual data still exists on disk and will be cleaned up in future compaction.

To determine when to remove old data, use --storage.tsdb.retention option e.g. --storage.tsdb.retention='365d' (by default, Prometheus keeps data for 15 days).

To completely remove the data deleted by delete_series send clean_tombstones API call:

`````
$ curl -X POST -g 'http://localhost:9090/api/v1/admin/tsdb/clean_tombstones'
`````

# License
Commercial use