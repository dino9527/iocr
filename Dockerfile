FROM openjdk:8-jdk
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/"$TZ" /etc/localtime && echo "$TZ" > /etc/timezone
ENV LANG=C.UTF-8
VOLUME /var
VOLUME /var/tmp
VOLUME /var/tmp/images

# 设置工作目录
WORKDIR /app

COPY ./target/iocr-0.0.1-SNAPSHOT.jar /app/iocr-0.0.1-SNAPSHOT.jar
EXPOSE 6010
ENV JAVA_OPTS "-Xms4096m -Xmx8192m"
CMD java $JAVA_OPTS -jar /app/iocr-0.0.1-SNAPSHOT.jar
