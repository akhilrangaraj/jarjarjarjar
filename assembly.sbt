assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.last
}
