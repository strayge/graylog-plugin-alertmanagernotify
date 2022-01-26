# Prepare

```
git clone https://github.com/strayge/graylog-plugin-alertmanagernotify.git
cd graylog-plugin-alertmanagernotify

# Get Graylog version from pom.xml (this requires "libxml2-utils")
export GRAYLOG_VERSION=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="parent"]/*[local-name()="version"]/text()' pom.xml)

# Checkout desired Graylog version (format: "4.0.0")
git clone --depth 1 --branch "${GRAYLOG_VERSION}" https://github.com/Graylog2/graylog2-server.git ../graylog2-server

# Build Graylog web interface
cd ../graylog2-server
mvn generate-resources -pl graylog2-server -B -V
```

# Build plugin

```
mvn -B package --file pom.xml
```

Builded plugin stored at `target/graylog-plugin-alertmanagernotify-<VERSION>.jar`

# Run Graylog with plugin

```
docker-compose -f development/docker-compose.yml up -d
```

Open Graylog [http://127.0.0.1:9000](http://127.0.0.1:9000) and login with `admin:admin`

AlertManager can be accessed at [http://127.0.0.1:9093/](http://127.0.0.1:9093/)

# Update Graylog Project Parent
Set the `version` of `parent` in `pom.xml`.

Then, update (clone or checkout) your local graylog2-server as shown in "Prepare"

# Upgrade yarn dependencies

```
yarn upgrade
```

# Troubleshooting

## class file has wrong version

Ensure that Maven uses the correct Java version by setting the `JAVA_HOME` environment variable, eg. via export `JAVA_HOME=/usr/lib/jvm/java-8-openjdk`.

Graylog only compatible with Java 8 (and testing 11 support)
