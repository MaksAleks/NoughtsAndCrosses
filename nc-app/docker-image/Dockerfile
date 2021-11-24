FROM openjdk:17-alpine
RUN mkdir -p /data/heap-dumps
COPY ./build/libs/*.jar app.jar
CMD ["java", \
    "-XX:+HeapDumpOnOutOfMemoryError", \
    "-XX:HeapDumpPath=/data/heap-dumps", \
    "-Djava.security.egd=file:/dev/urandom", \
    "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", \
    "-jar", \
    "/app.jar"]