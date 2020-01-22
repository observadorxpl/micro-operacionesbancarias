FROM openjdk:8
VOLUME /tmp
ADD ./target/OperacionesBancarioMS-0.0.1-SNAPSHOT.jar micro-operacionesbancario.jar
ENTRYPOINT ["java","-jar","/micro-operacionesbancario.jar"]