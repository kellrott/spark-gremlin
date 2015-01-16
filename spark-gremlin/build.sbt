import AssemblyKeys._ // put this at the top of the file

organization := "org.apache"

name := "spark-gremlin"

version := "0.1"

scalaVersion := "2.10.4"

// conflictManager := ConflictManager.strict

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % "1.1.0",
	"org.apache.spark" %% "spark-graphx" % "1.1.0",
	"com.tinkerpop" % "gremlin-core" % "3.0.0.M5",
	"com.tinkerpop" % "tinkergraph-gremlin" % "3.0.0.M5",
	"com.tinkerpop" % "gremlin-test" % "3.0.0.M5" % "test",
	"com.twitter" % "parquet-avro" % "1.5.0",
	"org.apache.avro" % "avro" % "1.7.7",
	"org.scalatest" %% "scalatest" % "2.1.5" % "test",
	"com.novocode" % "junit-interface" % "0.10" % "test"
)

dependencyOverrides ++= Set(
	"com.fasterxml.jackson.core" % "jackson-core" % "2.3.0",
	"com.fasterxml.jackson.core" % "jackson-databind" % "2.3.0",
	"commons-codec" % "commons-codec" % "1.5",
	"org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13",
	"org.codehaus.jackson" % "jackson-core-asl" % "1.9.13",
	"com.thoughtworks.paranamer" % "paranamer" % "2.6",
	"org.slf4j" % "slf4j-api" % "1.7.5",
	"junit" % "junit" % "4.11"
)

resolvers ++= Seq(
    "Akka Repository" at "http://repo.akka.io/releases/",
    "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

assemblySettings

assembleArtifact in packageScala := false

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.first
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
    case PathList("org", "w3c", xs @ _*) => MergeStrategy.first
    case "about.html"     => MergeStrategy.discard
    case "reference.conf" => MergeStrategy.concat
    case "log4j.properties"     => MergeStrategy.concat
    //case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  }
}


test in assembly := {}
