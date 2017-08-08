assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "maven", "io.netty", xs @ _*) => MergeStrategy.last
  case PathList("META-INF", "MANIFEST.MF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
