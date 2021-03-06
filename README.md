# lagom-java-chirper-example
Fork of lagom-java-chirper-example, to add docker-compose support.

## Build & test

```
sbt -DbuildTarget=compose clean docker:publishLocal
cd deploy/compose
docker-compose up -d cassandra web locator
#wait 30 seconds or so..
docker-compose up -d chirpservice friendservice activityservice proxy
#wait a bit for those services to launch..
#visit http://localhost:9000/ in a browser
#if you see a 503 error, the services haven't finished booting yet, 
#check with `docker-compose ps`, or `docker-compose logs <service-name>`
```

If you need nodejs then you can acquire it using
```
wget -qO- https://raw.githubusercontent.com/creationix/nvm/v0.33.2/install.sh | bash
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
nvm install --lts
```

## Status

Current status: *FUNCTIONAL*

All looking good =)

Could even look into using docker-compose depends on directives to have the services wait for cassandra/consul =)

## Differences from original project

- 2h timeout set for websocket based services
- maven build removed (sbt worked for me, and didn't want to maintain 2 build systems)
- `proxy` folder has an haproxy docker container that will act as the front door instead of ingress
- `lagom-service-locator-consul-java` folder is the impl of ServiceLocator that uses consul (still returns null, but that path is never called at the mo)
- `<servicename>-impl/src/main/compose-resource` folder contains `platform.conf` for running on compose
- `deploy\compose` folder contains the docker-compose.yml that can be used to start the whole lot. 
- added a `/health` endpoint to each service for consul/haproxy to use to know if the service is up
- added logic to each services Module class to register the service with Consul if the env has configuration for the consul hostname

