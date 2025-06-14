FROM bellsoft/liberica-openjdk-alpine:21

WORKDIR /app

COPY external-services.jar external-services.jar

CMD java -jar external-services.jar