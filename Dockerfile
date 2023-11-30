FROM --platform=linux/amd64 ubuntu:20.04
LABEL maintainer="franzzle"

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk

RUN apt-get install -y wget unzip

RUN wget https://github.com/DanielSWolf/rhubarb-lip-sync/releases/download/v1.13.0/Rhubarb-Lip-Sync-1.13.0-Linux.zip -O /tmp/rhubarb.zip && \
    unzip /tmp/rhubarb.zip -d /rhubarb

COPY build/libs/*.jar /opt/app.jar

USER root
RUN mkdir /output
RUN chmod 775 /output
RUN mkdir -p /output/lipsyncwavs

ENV JAVA_OPTS="-Xms32m -Xmx64m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app.jar" ]
