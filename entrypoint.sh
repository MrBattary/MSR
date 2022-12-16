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
	sudo -u postgres bash -c "psql -c \"DROP USER IF EXISTS linkermsru;\""
	sudo -u postgres bash -c "psql -c \"DROP DATABASE IF EXISTS linkermsrdb;\""
}

_build_server() {
	cd server
	./mvnw clean package
	cd ..
}

_run_server() {
	gnome-terminal --tab -- /bin/sh -c 'cd server/target; java -jar server-0.0.1-SNAPSHOT.jar;'
}

case $1 in
    create-db) _create_db;;
    remove-db) _remove_db;;
	build-server) _build_server;;
	run-server) _run_server;;
    *) exec "$@";;
esac