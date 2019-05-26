# logger-service
This is a logger service that works as a dependency project for microservices. This service will intercept all the logger calls made in the microservice and send it over to a kafka topic, later to be consumed by a log aggregator service that would push these logs to either a file system or a more extensive solution with Elasticsearch.
