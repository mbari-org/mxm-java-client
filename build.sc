//
//  Build lib jar and javadoc jar:
//      $ mill mxm._
//
//  Run demo program:
//      $ mill mxm.run
//

import mill._, scalalib._
import $ivy.`com.github.carueda::jbuildinfo:0.1.1`
import com.github.carueda.mill.JBuildInfo

object mxm extends JavaModule with JBuildInfo {
    val name = "mxm-client"
    val version = "0.1.0"

    def mainClass = Some("MxmClientMain")

    def buildInfoPackageName: Option[String] = Some("org.mbari.mxm.client")

    def buildInfoMembers = Map(
        "name" → name,
        "version" → version
    )

    override def ivyDeps = Agg(
        ivy"org.apache.httpcomponents:httpclient:4.5.10",
        ivy"com.google.code.gson:gson:2.8.5",
        ivy"org.apache.commons:commons-io:1.3.2",
    )

    override def javacOptions = Seq(
        "-source", "1.8", "-target", "1.8", "-g:lines"
    )
}
