#!/usr/bin/env bash

## Local Docker environment streamlined
local-start:
	$(info Starting base stack in docker)
	docker-compose -f ./docker/docker-compose.yml up -d --build
local-stop:
	$(info Stopping base stack in docker)
	docker-compose -f ./docker/docker-compose.yml down