name := "Gumgum-assignment"

version := "0.0.1"

scalaVersion := "2.12.8"

val sparkVersion = "2.4.0"
resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-avro" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,

  //Needed to read from s3
  "com.amazonaws" % "aws-java-sdk" % "1.7.4",
  "org.apache.hadoop" % "hadoop-aws" % "2.7.3",


  //tests
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "org.mockito" % "mockito-core" % "1.9.5" % Test,
  "MrPowers" % "spark-fast-tests" % "0.17.1-s_2.12"

)

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}
//assemblyJarName in assembly := "test-lambda.jar"
assemblyOutputPath in assembly := file( s"src/main/resources/spark-jars/${name.value}-${version.value}.jar" )
assemblyMergeStrategy in assembly :=
  {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  }