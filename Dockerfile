FROM ubuntu:20.04
MAINTAINER FRANZZLE

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

# RUN addgroup -S franzzle
# RUN adduser --uid 999 -S franzzle -G franzzle
# USER franzzle
#
# RUN chown -R franzzle:franzzle /output/lipsyncwavs

ENV JAVA_OPTS="-Xms256m -Xmx1024m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app.jar" ]
