FROM ubuntu:18.04

RUN \
apt-get update -y && \
apt-get install default-jre -y

ADD ./target/student-api-1.0.0.jar student-api-1.0.0.jar

EXPOSE 9080

CMD java -jar student-api-1.0.0.jar