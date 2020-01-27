# MXM Java Client Library

This project's goal is to build a client library to allow tools and
applications on the JVM to interact with an MXM service.

**Status**:

- A preliminary version of the library is already available
  for initial testing (only informational queries at the moment).

- The MXM service itself, including its API, is also in a prototypical stage
  so the API in general is not stable.

## Using the library

The library as well as the javadoc (in a separate JAR) are made available at
https://github.com/mbari-org/mxm-java-client/releases/.

Besides the `mxm-java-client-x.y.z.jar` library, you will also need the
[Apache httpclient](https://search.maven.org/artifact/org.apache.httpcomponents/httpclient/4.5.10/jar)
and the
[Google Gson](https://search.maven.org/artifact/com.google.code.gson/gson/2.8.6/jar)
libraries in your CLASSPATH.

As indicated in the included [demo program](mxm/src/main/java/MxmClientMain.java),
you construct an instance of the `MxmClient` class providing the URL of the MXM endpoint,
and then make queries and requests as needed:

```java
MxmClient mxm = new MxmClient("http://mxm.shore.mbari.org/mxm-graphql");

// general info about providers:
List<Provider> providers = mxm.getProviders();
...
// more detailed info about a particular provider:
Optional<Provider> ox = mxm.getProvider("foo-provider");
...
// detailed info about a mission template:
Optional<MissionTemplate> omt = mxm.getProviderMissionTemplate("foo-provider", "bar-mtempl");
...
// detailed info about a mission:
Optional<Mission> omt = mxm.getMission("foo-provider", "bar-mtempl", "some-mission");
...
```

Part of the demo program's output is enclosed below.

## General information

The GUI of the current MXM service prototype is at http://mxm.shore.mbari.org/mxm/.

The corresponding MXM API endpoint is `http://mxm.shore.mbari.org/mxm-graphql`.

A [GraphiQL](https://github.com/graphql/graphiql) interface is available
at http://mxm.shore.mbari.org/mxm-graphiql.

## Demo program

The following is part of the output generated by the included demo program
[`MxmClientMain`](mxm/src/main/java/MxmClientMain.java) when run
against the API endpoint indicated above:

```
mxm-client library version: 0.1.1

==== all providers response ====
    [{
      "providerId": "TethysDash",
      "description": "TethysDash/LRAUV System",
      "httpEndpoint": "http://tethyssim.shore.mbari.org:8080/TethysDash/api/mxm",
      "apiType": "REST0",
      "canValidate": false,
      "usesSched": true,
      "usesUnits": true
    }]

==== provider providerId='TethysDash' ====
    {
      "providerId": "TethysDash",
      "description": "TethysDash/LRAUV System",
      "httpEndpoint": "http://tethyssim.shore.mbari.org:8080/TethysDash/api/mxm",
      "apiType": "REST0",
      "canValidate": false,
      "usesSched": true,
      "usesUnits": true,
      "missionTemplates": [
        {
          "missionTplId": "Science/75m_isobath",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/chlpeak_sampling_InAboveBelow",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/circle_acoustic_contact",
          "description": "This mission runs a circle around a specified acoustic contact. The\ntracking behavior periodically queries the DAT for range and direction\nto the contact, and the DAT acoustically interrogates the contact. The\ntracking component then calculates the position of the contact to update\nthe center of the circle. When the vehicle surfaces and updates its own\nposition with GPS, it queries the contact again, then sends the contact\nposition to shore.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
```

## deployment

### javadoc

```
MXM_VERSION=0.1.2
JAR="mxm-java-client-${MXM_VERSION}-javadoc.jar"
scp ${JAR} mxmadmin@mxm.shore.mbari.org:/var/www/html/java-client-doc/javadoc.jar && \
ssh mxmadmin@mxm.shore.mbari.org <<'EOF'
  cd /var/www/html/java-client-doc/
  unzip -o ./javadoc.jar
EOF
```
