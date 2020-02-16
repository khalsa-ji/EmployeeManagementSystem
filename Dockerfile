#   Waheguru Ji!

FROM store/oracle/serverjre:8
WORKDIR /usr/src/EMS
COPY target/*.jar ems.jar
ENTRYPOINT [ "java", "-jar", "ems.jar" ]
