###Steps to release kotlin plugin:

1. Update kotlin-version in kotlin.KotlinPlugin
1. Update sbt version and scalafmt version in .scalafmt.conf
1. Update plugins in plugins.sbt
1. Make sure jitpack.yml is updated with the version of jdk and sbt
1. Make sure `ci` workflow is green in actions tab in github repo
1. Change release version in `build.sbt`. For example, if you want to release version 1.0.0
then add `version := "1.0.0"` to `build.sbt`
1. Update `README.md` with the version to be released and commit changes.
1. Run `release.sh $VERSION$` script by providing version number argument.
**Note:** `PROD=true` environment variable needs to be set before running `release.sh`
