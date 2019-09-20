organization := "org.mbari"

name := "mxm-java-client"

version := "0.1.0"

libraryDependencies ++= Seq(
    "org.apache.httpcomponents" % "httpclient" % "4.5.10",
    "com.google.code.gson" % "gson" % "2.8.5",
    "org.apache.commons" % "commons-io" % "1.3.2",
)

javacOptions in (Compile, compile) ++= Seq(
    "-source", "1.8", "-target", "1.8", "-g:lines"
)
javacOptions in (Compile,doc) ++= Seq("-notimestamp", "-linksource")

crossPaths := false // drop off Scala suffix from artifact names
autoScalaLibrary := false // exclude scala-library from dependencies
