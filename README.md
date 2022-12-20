# MSR

Multithreaded Service Research.

## Getting Started

### Installing dependencies

| Dependence | How to install                                                                                                                                |
|------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| make       | `sudo apt-get install build-essential`                                                                                                        |
| Java 17    | [Java installation example guide](https://techviewleo.com/install-java-openjdk-on-ubuntu-linux/)                                              |
| Maven      | [Maven installation example guide](https://www.digitalocean.com/community/tutorials/install-maven-linux-ubuntu)                               |
| PostreSQL  | [PostreSQL installation example guide](https://www.digitalocean.com/community/tutorials/how-to-install-postgresql-on-ubuntu-20-04-quickstart) |

### Running applications

#### Description

The research consists of two parts, server and client. For the first launch, we will need to deploy the database, build
and launch the server, and then the client. After the research, the database can be removed.

All the necessary commands are wrapped in a **Makefile** for convenience.

#### How to run

Deploys DB, builds server, builds client, starts server, makes a short delay (5 seconds), starts client.
Deploying a database may cause *could not change directory to "/.../MSR": Permission denied* , but do not worry, the
creation is correct.

```shell
sudo make create_db
make build start
```

**OR**

Automatic deployment and launch. `sudo make`

#### After run

To remove the DB used by this application run: `sudo make remove_db`.
Deleting a database may cause *could not change directory to "/.../MSR": Permission denied* , but do not worry, the
deletion is correct.

### Makefile description

| Command                     | Description                                                                                                                                                                                                                             |
|-----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `sudo make create_db`       | Creates a **linkermsrdb** database in Postgresql. <br/>Creates a **linkermsru** user with a password. <br/>Transfers all rights on the **linkermsrdb** database to the **linkermsru** user.                                             |
| `sudo make remove_db`       | Reassigns all rights to the **linkermsrdb** database from the **linkermsru** user to the **postgres** user.<br/>Drops everything related to the **linkermsru** user. Drops the **linkermsru** user. Drops the **linkermsrdb** database. |
| `sudo make`                 | Deploys DB, builds server, builds client, starts server, makes a short delay (5 seconds), starts client.                                                                                                                                |
| `make build`                | Builds server, builds client.                                                                                                                                                                                                           |
| `make start`                | Starts server, makes a short delay (5 seconds), starts client.                                                                                                                                                                          |
| `make start_server`         | Builds and then starts server.                                                                                                                                                                                                          |
| `make start_client`         | Builds and then starts client.                                                                                                                                                                                                          |
| `make start_client_delayed` | Builds client, makes a short delay (5 seconds), and then starts client.                                                                                                                                                                 |
| `make build_server`         | Builds server.                                                                                                                                                                                                                          |
| `make build_client`         | Builds client.                                                                                                                                                                                                                          |
| `make run_server`           | Starts server.                                                                                                                                                                                                                          |
| `make run_client`           | Starts client.                                                                                                                                                                                                                          |

## Developers

Michael Linker - *Entire work* - [MrBattary](https://github.com/MrBattary)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.