{
  val ver = System.getProperty("plugin.version")
  if (ver == null)
    throw new RuntimeException("""
      |The system property 'plugin.version' is not defined.
      |Specify this property using scriptedLaunchOpts -Dplugin.version.""".stripMargin)
  else {
    resolvers += "jitpack" at "https://jitpack.io"
    libraryDependencies += "com.github.tmtsoftware" % "kotlin-plugin" % "3.0.1"
  }
}

resolvers += Resolver.jcenterRepo
addSbtPlugin("net.aichler" % "sbt-jupiter-interface" % "0.9.1")
