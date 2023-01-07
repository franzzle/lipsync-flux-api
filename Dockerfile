FROM ubuntu:20.04

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk

MAINTAINER FRANZZLE
COPY build/libs/*.jar /opt/app.jar

ADD target/rhubarb.gz /rhubarb

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
