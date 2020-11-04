name := "kotlin-plugin"
scalaVersion := "2.12.12"
organization := "com.github.tmtsoftware"

version := "2.1.0-M1"

homepage := scmInfo.value map (_.browseUrl)
scmInfo := Some(
  ScmInfo(url("https://github.com/tmtsoftware/kotlin-plugin"), "scm:git:git@github.com:tmtsoftware/kotlin-plugin.git")
)

scalacOptions ++= Seq("-deprecation", "-Xlint", "-feature")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.3.2"
)

sbtPlugin := true

// build info plugin

enablePlugins(BuildInfoPlugin, SbtPlugin)

buildInfoPackage := "kotlin"

// bintray
bintrayRepository := "sbt-plugins"
bintrayPackageLabels := Seq("sbt", "plugin")
bintrayVcsUrl := Some("""git@github.com:tmtsoftware/kotlin-plugin.git""")

publishMavenStyle := true

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

bintrayOrganization := None

// scripted
scriptedLaunchOpts ++= Seq(
  "-Xmx1024m",
  "-Dplugin.version=" + version.value
)
