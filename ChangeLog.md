2022-08

- simplified project setup:
    - added GitHub actions for CI; removed Travis 
    - just using maven now
        - `mvn package` 
        - `mvn compile exec:java -Dexec.mainClass="MxmClientMain"` 
- re-exercised demo program (`mill mxm.run`)
- updated gson to 2.9.0
- use dependencyOverrides to address snyk reported vulnerability in commons-codec:commons-codec@1.11
  via org.apache.httpcomponents:httpclient@4.5.13 (no more recent httpclient version in maven central)

2021-12

- update dependencies per snyk report
  gson, httpclient

2020-01

- enable automated release upon tag push
- 0.1.4: some code reorg

- 0.1.2: reflect executor-to-provider renaming,
  and use mxm.shore as endpoint for the demo

2020-01-07

- 0.1.1: various fixes, updates, and adjustments to facilitate more rapid prototyping
- building:

        mill mxm._
        MXM_VERSION=0.1.1
        cp out/mxm/jar/dest/out.jar    ./mxm-java-client-${MXM_VERSION}.jar
        cp out/mxm/docJar/dest/out.jar ./mxm-java-client-${MXM_VERSION}-javadoc.jar 

2019-09-20

- reorg project so I can also use mill for run/build:
    - `mill mxm.run`
    - `mill mxm._`
      (this one in particular also packages the javadoc in a jar)

- `> package`
- `> doc`

2019-09-19

- initial commit with basic skeleton and operations
