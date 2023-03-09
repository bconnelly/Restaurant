FROM eclipse-temurin:17.0.6_10-jdk
SHELL ["/bin/bash", "-c"]

RUN wget https://downloads.apache.org/tomcat/tomcat-10/v10.1.5/bin/apache-tomcat-10.1.5.tar.gz && \
    tar -xf apache-tomcat-10.1.5.tar.gz -C /opt/tomcat && \
    rm apache-tomcat-10.1.5.tar.gz && \
    chown -R tomcat: /opt/tomcat

ADD TablesService.war /opt/tomcat/apache-tomcat-10.1.5/webapps
COPY tomcat-users.xml /opt/tomcat/apache-tomcat-10.1.5/conf
COPY context.xml /opt/tomcat/apache-tomcat-10.1.5/webapps/manager/META-INF
COPY server.xml /opt/tomcat/apache-tomcat-10.1.5/conf

RUN echo "0" > healthy

CMD ["/opt/tomcat/apache-tomcat-10.1.5/bin/catalina.sh", "run"]