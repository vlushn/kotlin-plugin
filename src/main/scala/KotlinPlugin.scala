package kotlin

import kotlin.Keys._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbt._

/**
 * @author pfnguyen
 */
object KotlinPlugin extends AutoPlugin {
  override def trigger  = allRequirements
  override def requires = JvmPlugin

  override def projectConfigurations: Seq[Configuration] = KotlinInternal :: Nil

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      libraryDependencies ++= Seq(
        "org.jetbrains.kotlin" % "kotlin-compiler-embeddable"           % kotlinVersion.value % KotlinInternal.name,
        "org.jetbrains.kotlin" % "kotlin-scripting-compiler-embeddable" % kotlinVersion.value % KotlinInternal.name,
        "org.jetbrains.kotlin" % "kotlin-scripting-compiler-embeddable" % kotlinVersion.value
      ),
      managedClasspath in KotlinInternal := Classpaths.managedJars(KotlinInternal, classpathTypes.value, update.value),
      kotlinVersion := "1.4.10",
      kotlincOptions := Nil,
      kotlincPluginOptions := Nil,
      watchSources ++= {
        import language.postfixOps
        val kotlinSources = "*.kt" || "*.kts"
        (sourceDirectories in Compile).value.flatMap(_ ** kotlinSources get) ++
        (sourceDirectories in Test).value.flatMap(_ ** kotlinSources get)
      }
    ) ++ inConfig(Compile)(kotlinCompileSettings) ++
      inConfig(Test)(kotlinCompileSettings)

  val autoImport: Keys.type = Keys

  // public to allow kotlin compile in other configs beyond Compile and Test
  val kotlinCompileSettings = List(
    unmanagedSourceDirectories += kotlinSource.value,
    kotlincOptions := kotlincOptions.value,
    kotlincPluginOptions := kotlincPluginOptions.value,
    kotlinCompile := Def
      .task {
        KotlinCompile.compile(
          kotlincOptions.value,
          sourceDirectories.value,
          kotlincPluginOptions.value,
          dependencyClasspath.value,
          (managedClasspath in KotlinInternal).value,
          classDirectory.value,
          streams.value
        )
      }
      .dependsOn(compileInputs in (Compile, compile))
      .value,
    compile := (compile dependsOn kotlinCompile).value,
    kotlinSource := sourceDirectory.value / "kotlin"
  )
}
