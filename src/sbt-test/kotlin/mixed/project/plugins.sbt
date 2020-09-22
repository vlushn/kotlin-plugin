{
  val ver = System.getProperty("plugin.version")
  if (ver == null)
    throw new RuntimeException("""
      |The system property 'plugin.version' is not defined.
      |Specify this property using scriptedLaunchOpts -Dplugin.version.""".stripMargin)
  else addSbtPlugin("com.github.tmtsoftware" % "kotlin-plugin" % ver)
}

resolvers += Resolver.jcenterRepo
addSbtPlugin("net.aichler" % "sbt-jupiter-interface" % "0.8.3")
