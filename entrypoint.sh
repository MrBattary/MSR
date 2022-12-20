#!/usr/bin/env bash

set -ex

_create_db() {
	sudo -u postgres bash -c "psql -c \"CREATE DATABASE linkermsrdb;\""
	sudo -u postgres bash -c "psql -c \"CREATE USER linkermsru WITH PASSWORD 'linkermsr';\""
	sudo -u postgres bash -c "psql -c \"GRANT ALL PRIVILEGES ON DATABASE linkermsrdb TO linkermsru;\""
}

_remove_db() {
	sudo -u postgres bash -c "psql -c \"REASSIGN OWNED BY linkermsru TO postgres;\""
	sudo -u postgres bash -c "psql -c \"DROP OWNED BY linkermsru;\""
	sudo -u postgres bash -c "psql -c \"DROP DATABASE IF EXISTS linkermsrdb;\""
	sudo -u postgres bash -c "psql -c \"DROP USER IF EXISTS linkermsru;\""
}

_build_server() {
	cd server
	./mvnw clean package
	cd ..
}

_build_client() {
	cd client
	./mvnw clean package
	cd ..
}

_run_server() {
	gnome-terminal --tab -- /bin/sh -c 'cd server/target; java -jar server-1.0.jar;'
}

_run_client() {
	gnome-terminal --tab -- /bin/sh -c 'cd client/target; java -jar client-1.0-jar-with-dependencies.jar;'
}

_run_client_delayed() {
	sleep 5s
	gnome-terminal --tab -- /bin/sh -c 'cd client/target; java -jar client-1.0-jar-with-dependencies.jar;'
}

case $1 in
  create-db) _create_db;;
  remove-db) _remove_db;;
	build-server) _build_server;;
	run-server) _run_server;;
	build-client) _build_client;;
	run-client) _run_client;;
	run-client-delayed) _run_client_delayed;;
  *) exec "$@";;
esac