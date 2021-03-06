name := "jarjarjar"
version := "1.0"
scalaVersion := "2.12.2"


libraryDependencies += "com.github.mauricio" %% "postgresql-async" % "0.2.21"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime"
libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.13.1"


assemblyShadeRules in assembly ++= Seq(
  ShadeRule.rename("io.netty.**" -> "com.github.mauricio.@0")
    .inLibrary("com.github.mauricio" %% "postgresql-async" % "0.2.21")
    .inProject
    .inAll
)
