2020-01-07

- 0.1.1: various fixes, updates, and adjustments to facilitate more rapid prototyping
- building:

        mill mxm._
        cp out/mxm/jar/dest/out.jar    ./mxm-java-client-0.1.1.jar
        cp out/mxm/docJar/dest/out.jar ./mxm-java-client-0.1.1-javadoc.jar 

2019-09-20

- reorg project so I can also use mill for run/build:
    - `mill mxm.run`
    - `mill mxm._`
      (this one in particular also packages the javadoc in a jar)

- `> package`
- `> doc`

2019-09-19

- initial commit with basic skeleton and operations
