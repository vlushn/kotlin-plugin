###Steps to release kotlin plugin:

1. Make sure `ci` workflow is green in actions tab in github repo
1. Change release version in `build.sbt`. For example, if you want to release version 1.0.0
then add `version := "1.0.0"` to `build.sbt`
1. Update `README.md` with the version to be released.
1. Run `release.sh $VERSION$` script by providing version number argument.
**Note:** `PROD=true` environment variable needs to be set before running `release.sh`
1. Make sure you have valid bintray credentials in `~/.bintray/.credentials` file
 and run `sbt publish`.
