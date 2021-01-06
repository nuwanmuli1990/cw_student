FROM java:8
WORKDIR /
RUN echo "Asia/Colombo" | tee /etc/timezone
RUN dpkg-reconfigure --frontend noninteractive tzdata
ADD student-0.0.1-SNAPSHOT.jar student-0.0.1-SNAPSHOT.jar
EXPOSE 5070
ENTRYPOINT ["java","-jar","student-0.0.1-SNAPSHOT.jar","--server.port=5070"]
