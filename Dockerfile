FROM openjdk:15-jdk-alpine
COPY /out/artifacts/TimeLog_server_jar/TimeLog-server.jar TimeLog-server.jar
CMD ["java","-jar","TimeLog-server.jar","\"develop\""]