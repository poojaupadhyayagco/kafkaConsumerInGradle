FROM openjdk:12-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG FUSE_ENV

ENV FUSE_ENV=${FUSE_ENV}

RUN apk update && apk add curl curl-dev bash python
RUN curl "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" -o "awscli-bundle.zip"
RUN unzip awscli-bundle.zip
RUN ./awscli-bundle/install -i /usr/local/aws -b /usr/local/bin/aws

LABEL "agco"="kafka-consumer-dashboard"

RUN apk add --no-cache openssl

COPY ${JAR_FILE} app.jar

COPY run.sh /run.sh
RUN chmod +x /run.sh

ENTRYPOINT ["bash", "/run.sh"]