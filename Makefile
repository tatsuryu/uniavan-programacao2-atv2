MAKEFLAGS += silent

SHELL = /bin/bash

.DEFAULT_GOAL := build

src/br/edu/uniavan/Atv2.class:
	@cd src; \
	javac br/edu/uniavan/Atv2.java
	

.PHONY: build
build: src/br/edu/uniavan/Atv2.class

run: build
	@cd src; \
	java -cp .:../lib/mysql-connector-j-8.2.0.jar br.edu.uniavan.Atv2;

clean:
	@git clean -Xdf

db:
	docker compose --project-directory docker up -d

stop_db:
	docker compose --project-directory docker down