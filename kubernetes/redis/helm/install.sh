#!/usr/bin/env bash
helm install redis-dev stable/redis-ha --namespace staging -f config.yml