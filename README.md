# Tiny URL

Servicio para acortar URL's

## Prerequisitos

- Tener instalado docker.

- Hacer pull de redis con el siguiente comando, `$ docker pull redis`

- Correr el contenedor con el siguiente comando, `$ docker run -d -p 6379:6379 --name my-redis redis`

### Uso del servicio

- Para correr el servicio se debe ejecutar el siguiente comando,  `$ ./gradlew bootRun`

- La documentación del servicio se puede ver en la siguiente URL `http://localhost:8080/swagger-ui/`

- Para ver información del servicio se dispone la librería de actuator de spring boot, `http://localhost:8080/actuator`

- Para ver las metricas del servicio se dispone el siguiente path de actuator, `http://localhost:8080/actuator/metrics`