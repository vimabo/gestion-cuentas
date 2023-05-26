FROM openjdk:8
COPY target/banco-neoris.jar banco-neoris.jar
ENTRYPOINT [ "java", "-jar", "/banco-neoris.jar" ]