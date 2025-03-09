import sbt._

object Dependencies {

  lazy val sparkLibs = Seq(
    "org.apache.spark" %% "spark-core" % "3.5.4"
  )

}
