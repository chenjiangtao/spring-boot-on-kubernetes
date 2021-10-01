IMAG_TAG?=latest
local-env:
	kubectl apply -f kube/local/namespace_dev.json
	kubectl apply -f kube/local/mysql/
	kubectl apply -f kube/local/redis/

docker-gateway-devk8s-build:
	cd sbux-datasync-gateway && chmod +x make_dev.sh && sh make_dev.sh

docker-gateway-devk8s-deploy:
	cd kube/dev/datasync-gateway && chmod +x k8s_deploy.sh && sh k8s_deploy.sh

docker-admin-devk8s-build:
	cd sbux-datasync-admin/console-service && chmod +x make_dev.sh && sh make_dev.sh
	cd sbux-datasync-admin/datasync-console && chmod +x make_dev.sh && sh make_dev.sh

docker-console-devk8s-build:
	cd sbux-datasync-admin/datasync-console && chmod +x make_dev.sh && sh make_dev.sh

docker-console-service-devk8s-build:
	cd sbux-datasync-admin/console-service && chmod +x make_dev.sh && sh make_dev.sh

docker-admin-devk8s-deploy:
	cd kube/dev/console-service && chmod +x k8s_deploy.sh && sh k8s_deploy.sh
	cd kube/dev/datasync-console && chmod +x k8s_deploy.sh && sh k8s_deploy.sh

docker-console-devk8s-deploy:
	cd kube/dev/datasync-console && chmod +x k8s_deploy.sh && sh k8s_deploy.sh

docker-console-service-devk8s-deploy:
	cd kube/dev/console-service && chmod +x k8s_deploy.sh && sh k8s_deploy.sh

docker-gateway-localk8s-build:
	cd sbux-datasync-gateway && chmod +x make_local.sh && sh make_local.sh

docker-admin-localk8s-build:
	cd sbux-datasync-admin/console-service && chmod +x make_local.sh && sh make_local.sh
	cd sbux-datasync-admin/datasync-console && chmod +x make_local.sh && sh make_local.sh

docker-gateway-localk8s-deploy:
	cd kube/local/datasync-gateway && chmod +x k8s_deploy.sh && sh k8s_deploy.sh

docker-admin-localk8s-deploy:
	cd kube/local/console-service && chmod +x k8s_deploy.sh && sh k8s_deploy.sh
	cd kube/local/datasync-console && chmod +x k8s_deploy.sh && sh k8s_deploy.sh

local-clean:
	echo 'gradle clean'
	gradle clean --no-daemon
	echo 'local docker images clean'
	docker images |grep 5000 |xargs docker rmi
	echo 'clean minikube docker images'
	minikube ssh 'docker images |grep 5000 |xargs docker rmi'

local-k8s-clean:
	cd kube/local/datasync-console && chmod +x k8s_undeploy.sh && sh k8s_undeploy.sh
	cd kube/local/console-service && chmod +x k8s_undeploy.sh && sh k8s_undeploy.sh
	cd kube/local/datasync-gateway && chmod +x k8s_undeploy.sh && sh k8s_undeploy.sh
	cd kube/local/redis && chmod +x k8s_undeploy.sh && sh k8s_undeploy.sh
	cd kube/local/mysql && chmod +x k8s_undeploy.sh && sh k8s_undeploy.sh
# 	kubectl delete -f kube/local/namespace_dev.json
	kubectl delete -f kube/local/mysql/
	kubectl delete -f kube/local/redis/

dev-clean:
	echo 'gradle clean'
	gradle clean --no-daemon
	echo 'dev docker images clean'
	docker images |grep 5000 |xargs docker rmi

push-gitee:
	echo 'pussh to gitee remote'
	git remote set-url origin git@gitee.com:chenjiangtao/spring-boot-on-kubernetes.git
	git remote -v
	git push origin
	git remote set-url origin git@github.com:chenjiangtao/spring-boot-on-kubernetes.git
	echo 'reset remote to github'
	git remote -v