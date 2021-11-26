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
      KotlinInternal / managedClasspath := Classpaths.managedJars(KotlinInternal, classpathTypes.value, update.value),
      kotlinVersion := "1.5.20",
      kotlincOptions := Nil,
      kotlincPluginOptions := Nil,
      kotlinSourceDir := ((Compile / sourceDirectory).value / "kotlin"),
      kotlinSources := Seq((Compile / kotlinSourceDir).value),
      watchSources ++= {
        (Compile / kotlinSources).value ++
        (Test / kotlinSources).value
      }
    ) ++ inConfig(Compile)(kotlinCompileSettings) ++
      inConfig(Test)(kotlinCompileSettings)

  val autoImport: Keys.type = Keys

  // public to allow kotlin compile in other configs beyond Compile and Test
  val kotlinCompileSettings = List(
    unmanagedSourceDirectories ++= kotlinSources.value,
    kotlincOptions := kotlincOptions.value,
    kotlincPluginOptions := kotlincPluginOptions.value,
//    compileInputs ++= kotlinSources.value,
    kotlinCompile := Def
      .task {
        KotlinCompile.compile(
          kotlincOptions.value,
          kotlinSources.value,
          kotlincPluginOptions.value,
          dependencyClasspath.value,
          (KotlinInternal / managedClasspath).value,
          classDirectory.value,
          streams.value
        )
      }
//      .dependsOn(Compile / compile / compileInputs)
      .value,
    compile := (compile dependsOn kotlinCompile).value,
  )
}
