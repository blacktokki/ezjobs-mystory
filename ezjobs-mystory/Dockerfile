FROM openjdk:8-jre
RUN apt-get install -y libfontconfig
ENV mysql-host=192.168.25.7:3306
ENV mysql-username=root
ENV email-username=ydh051541@gmail.com
VOLUME /tmp
ADD ezjobs-mystory-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]