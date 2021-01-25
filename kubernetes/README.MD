# kubernetes
## mysql 
## redis
## ingress
## hpa

## LimitRange
cpu-defaults.yaml cpu限制,分limit和request

memory-defaults.yaml 内存限制，分limit和request

指定空间,在哪个namespace里，就限制哪个，如：dev

k create -f cpu-defaults.yaml -ndev

目前看，如果不加limitRange，会自动分配，并不受限制（k8s里设定了的除外）



