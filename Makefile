SUDO=$(grep -q $$USER || echo sudo)

.PHONY: default
default: build start

.PHONY: start_server
start_server: build_server run_server

.PHONY: start
start: start_server

.PHONY: build
build: build_server

.PHONY: create_db
create_db: 
	bash $(SUDO) entrypoint.sh create-db
	
.PHONY: remove_db
remove_db: 
	bash $(SUDO) entrypoint.sh remove-db
	
.PHONY: build_server
build_server:
	bash entrypoint.sh build-server

.PHONY: run_server
run_server:
	bash entrypoint.sh run-server