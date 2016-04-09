MeterBeat power consumption data collector
==========================================

The intention of this Java tool is to collect measurement data from smart devices (such as smart outlets)
and provide that data to either Logstash or directly to an ElasticSearch service.

Supported Devices
-----------------

* D-Link DSP-W215

Installation
------------

### Requirements

For running
- Oracle Java JRE 8
- at least one of the supported devices
- probably an iOS or Android mobile to setup the device
- an ElasticSearch and/or Logstash service to send data to
- Kibana is recommended for visualising data

In addition for development
- Oracle Java JDK 8
- Gradle 2.12
- An IDE, e.g. Eclipse (run `gradle eclipse` to generate required project settings and classpath adjustments)

### Setup

1. Download the latest JAR from the releases area or clone the source code
2. Download the sample `meterbeat.yml` configuration file from the sources
3. Place the file in a proper location and adjust the content to your system and requirements
4. Start via `java -jar meterbeat.jar -c <LOCATION OF meterbeat.yml>`
  - replace `<LOCATION OF meterbeat.yml>` with the actual path to the configuration file


