## For development environment

### Requirement
- Docker: https://docs.docker.com/get-docker/
- Amazon Corretto Version 8.x: https://aws.amazon.com/jp/corretto/
- Sbt: https://www.scala-sbt.org/download.html
- nodejs: https://heynode.com/tutorial/install-nodejs-locally-nvm/

### Check out repository

```
$ git git@github.com:glaciermelt00/vald00.git
$ cd vald00
```

### Launch database (MySQL)
- listening on port 13306

```
 $ docker-compose up -d
 $ mysql -uroot -p -h127.0.0.1 -P13306
```

The first time it is started, it will take some time as it loads the data;
The progress status can be determined by checking the log.

```
$ docker-compose logs -f
....
mysql_1  | 2022-12-10T12:59:02.383241Z 0 [Note] Event Scheduler: Loaded 0 events
mysql_1  | 2022-12-10T12:59:02.390104Z 0 [Note] mysqld: ready for connections.
mysql_1  | Version: '5.7.35'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)
```

- How to check the status of docker.

```
$ docker-compose ps
     Name                  Command             State                 Ports
-----------------------------------------------------------------------------------------
vald00_mysql_1   docker-entrypoint.sh mysqld   Up      0.0.0.0:13306->3306/tcp, 33060/tcp
```

### Start up application server
- listening on port 9000

```
$ sbt
[SBT] val-ld@val-ld:[feature/2022-12-10-start-project] > run
--- (Running the application, auto-reloading is enabled) ---
[info] p.c.s.AkkaHttpServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000
(Server started, use Enter to stop and go back to the console...)
```

## Note: Dump working database snapshot

```
$ cd conf/database/mysql_data
$ mysqldump -uroot -p -h127.0.0.1 -P13306 -B --skip-extended-insert edu_shop > edu_shop.sql
```
