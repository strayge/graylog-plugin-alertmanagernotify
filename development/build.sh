#/bin/sh

# prebuild graylog stuff
docker build .. -f Dockerfile -t graylog_plugin_builder

# clear output
rm ../target/*.jar

# build plugin
docker run -v `pwd`/../target:/result graylog_plugin_builder
