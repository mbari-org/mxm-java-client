//
//  Build lib jar and javadoc jar:
//      $ mill mxm._
//
//  Run demo program:
//      $ mill mxm.run
//

import mill._, scalalib._

object mxm extends JavaModule {
    val name = "mxm-client"
    val version = "0.1.6"

    def mainClass = Some("MxmClientMain")

    override def ivyDeps = Agg(
        ivy"org.apache.httpcomponents:httpclient:4.5.13",
        ivy"com.google.code.gson:gson:2.8.9",
        ivy"org.apache.commons:commons-io:1.3.2",
    )

    override def javacOptions = Seq(
        "-source", "1.8", "-target", "1.8", "-g:lines"
    )
}
