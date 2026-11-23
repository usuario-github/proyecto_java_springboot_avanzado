# Pruebas

```bash
curl -i -X POST http://localhost:7070/api/v1/productos \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "nombre": "Laptop Lenovo",
    "precio": 3500.00
}'
```

```bash
curl -i -X POST http://localhost:7071/api/v1/pedidos \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "idProducto": 1,
    "cantidad": 2,
    "total": 150.00
}'
```

# Logging centralizado (ELK)

`pedido-service`, `producto-service` e `inventario-service` envían sus logs en JSON a Logstash
(`localhost:5000`) mediante `logstash-logback-encoder` (ver `src/main/resources/logback-spring.xml`
de cada servicio). Logstash los guarda en Elasticsearch con un índice por servicio y por día:
`microservicios-<servicio>-AAAA.MM.DD`.

```bash
cd monitoreo
docker compose up -d elasticsearch logstash kibana
```

En Kibana (http://localhost:5601): **Stack Management → Data Views → Create data view**
con el patrón `microservicios-*` y campo de tiempo `@timestamp`. Luego, en **Discover**,
filtrar por servicio, por ejemplo `app : "inventario-service"`, o seguir una petición entre
servicios con `traceId : "<id>"`.

Comprobar los índices desde la terminal:

```bash
curl "http://localhost:9200/_cat/indices/microservicios-*?v"
```
