FROM maven:3.8-jdk-8

RUN mkdir /result

WORKDIR /app/plugin

RUN apt-get update && \
    apt-get -y install libxml2-utils && \
    apt-get clean

# dependency for graylog2-server fetching
COPY pom.xml .

# fetch graylog2-server and generate some staff in it
RUN export GRAYLOG_VERSION=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="parent"]/*[local-name()="version"]/text()' pom.xml) && \
    export GRAYLOG_SERVER_PATH=$(realpath ../graylog2-server) && \
    git clone --depth 1 --branch "${GRAYLOG_VERSION}" https://github.com/Graylog2/graylog2-server.git "${GRAYLOG_SERVER_PATH}" && \
    cd ${GRAYLOG_SERVER_PATH} && \
    mvn generate-resources -pl graylog2-server -B -V

# copy actual plugin
COPY src /app/plugin/src
COPY *.js *.json *.xml *.lock /app/plugin/

# build plugin
RUN mvn -B package --file pom.xml

# extract result
CMD  cp target/*.jar /result/
