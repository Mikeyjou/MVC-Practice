FROM jetty
ADD build/libs/WebApi-0.0.2.war $JETTY_BASE/webapps/root.war
MAINTAINER Docker Newbee sianwu2011@gmail.com
# WORKDIR $JETTY_BASE
# RUN java -jar $JETTY_HOME/start.jar  && chown -R jetty:jetty $JETTY_BASE
EXPOSE 8080
