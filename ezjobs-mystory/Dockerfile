FROM openjdk:8-jdk-alpine
ENV devtools=false
ENV mysql-host=192.168.25.7:3306
ENV mysql-username=root
VOLUME /tmp
ADD ezjobs-mystory-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]