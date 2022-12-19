SUDO=$(grep -q $$USER || echo sudo)

.PHONY: default
default: build start

.PHONY: start
start: start_server start_client_delayed

.PHONY: build
build: build_server build_client

.PHONY: start_server
start_server: build_server run_server

.PHONY: start_client
start_client: build_client run_client

.PHONY: start_client_delayed
start_client_delayed: build_client run_client_delayed

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
	
.PHONY: build_client
build_client:
	bash entrypoint.sh build-client

.PHONY: run_client
run_client:
	bash entrypoint.sh run-client
	
.PHONY: run_client_delayed
run_client_delayed:
	bash entrypoint.sh run-client-delayed