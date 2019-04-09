FROM ubuntu:18.04
LABEL image=Spark-base-image

ENV SPARK_VERSION=2.4.1
ENV HADOOP_VERSION=2.7

RUN apt-get update -qq && \
    apt-get install -qq -y gnupg2 wget openjdk-8-jdk scala 

RUN cd /  && \ 
    wget --no-verbose http://www.gtlib.gatech.edu/pub/apache/spark/spark-2.4.1/spark-2.4.1-bin-hadoop2.7.tgz
RUN tar -xzf /spark-2.4.1-bin-hadoop2.7.tgz && \
    mv spark-2.4.1-bin-hadoop2.7 spark && \
    echo "export PATH=$PATH:/spark/bin" >> ~/.bashrc    