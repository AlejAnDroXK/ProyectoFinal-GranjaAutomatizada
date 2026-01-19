#BLOQUE 1, Descarga imagen jq (herramienta leer Jason
# se usa para leer claves Vaadin desde secretos
FROM ghcr.io/jqlang/jq:latest AS jq-stage
#BLOQUE 2, compilar la app
FROM eclipse-temurin:21-jdk AS build
#Copiar el jq desde le primer bloque
COPY --from=jq-stage /jq /usr/bin/jq
RUN jq --version

#Prepara carpeta de la app /app es el proyecto
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY . $HOME

# If you have a Vaadin Pro key, pass it as a secret with id "proKey":
#
#   $ docker build --secret id=proKey,src=$HOME/.vaadin/proKey .
#
# If you have a Vaadin Offline key, pass it as a secret with id "offlineKey":
#
#   $ docker build --secret id=offlineKey,src=$HOME/.vaadin/offlineKey .

#permite pasar licencias Vaadin Pro / Offline
#sin subir al github o dejarlas en el codigo
RUN --mount=type=cache,target=/root/.m2 \
    --mount=type=secret,id=proKey \
    --mount=type=secret,id=offlineKey \
    sh -c 'PRO_KEY=$(jq -r ".proKey // empty" /run/secrets/proKey 2>/dev/null || echo "") && \
    OFFLINE_KEY=$(cat /run/secrets/offlineKey 2>/dev/null || echo "") && \
    ./mvnw clean package -DskipTests -Dvaadin.proKey=${PRO_KEY} -Dvaadin.offlineKey=${OFFLINE_KEY}'

#BLOQUE 3, la produccion de la imagen final
FROM eclipse-temurin:21-jre-alpine
#Copia solo el JAR
COPY --from=build /app/target/*.jar app.jar
#EJECUTA LA APLICACION
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
