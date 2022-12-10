/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

import scala.sys.process._

name         := "val-ld"
scalaVersion := "2.12.15"

// The build mode
val branch       = ("git branch".lineStream_!).find(_.head == '*').map(_.drop(2)).getOrElse("")
val main         = branch == "main"
val develop      = branch == "develop"
val release      = branch.startsWith("release")
val hotfix       = branch.startsWith("hotfix")

// Setting for resolvers
resolvers ++= Seq(
  "Sonatype Release"   at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshot"  at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Typesafe Releases"  at "https://repo.typesafe.com/typesafe/releases/",
  "IxiaS Snapshots"    at "https://s3-ap-northeast-1.amazonaws.com/maven.ixias.net/snapshots",
  "IxiaS Releases"     at "https://s3-ap-northeast-1.amazonaws.com/maven.ixias.net/releases"
)

// Required libraries
libraryDependencies ++= Seq(

  // --[ Private ]------------------------------------------
  "net.ixias" %% "ixias"      % "1.1.40",
  "net.ixias" %% "ixias-play" % "1.1.40",
  "net.ixias" %% "ixias-aws"  % "1.1.40",

  // --[ OSS ]----------------------------------------------
  "mysql"                % "mysql-connector-java"     % "8.0.28",
  "net.logstash.logback" % "logstash-logback-encoder" % "7.0.1",

  // --[ UnitTest ]-----------------------------------------
  "org.specs2" %% "specs2-core"          % "4.14.0" % Test,
  "org.specs2" %% "specs2-matcher-extra" % "4.14.0" % Test,
  ws, guice
)

// Setting for project
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(SbtWeb)

// Setting for Play routes
// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
Compile / unmanagedResourceDirectories += (baseDirectory { _ / "app" / "views" }).value

// Template Engine.
TwirlKeys.templateImports := Seq.empty
Compile / TwirlKeys.compileTemplates / sourceDirectories := Seq(baseDirectory.value / "app")

// Play routes setting.
import play.sbt.routes.RoutesKeys
RoutesKeys.routesImport := Seq()

// Scala compile options
scalacOptions ++= Seq(
  "-deprecation",            // Emit warning and location for usages of deprecated APIs.
  "-feature",                // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",              // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings",        // Fail the compilation if there are any warnings.
  "-Xlint",                  // Enable recommended additional warnings.
  "-Ywarn-adapted-args",     // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code",        // Warn when dead code is identified.
  "-Ywarn-unused:imports",   // Warn if an import selector is not referenced.
  "-Ywarn-inaccessible",     // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen",    // Warn when numerics are widened.
  "-Ypartial-unification",
  "-Wconf:any&src=target/scala-2.12/routes/.*:s"
)

javaOptions ++= Seq(
  "-Dconfig.file=conf/env.dev/application.conf",
  "-Dlogger.file=conf/env.dev/logback.xml"
)
Universal / javaOptions ++= Seq(
  "-Dpidfile.path=/dev/null"
)
Compile / run / fork := true

// Setting for prompt
import com.scalapenos.sbt.prompt._
val defaultTheme = PromptTheme(List(
  text("[SBT] ", fg(green)),
  text(state => { Project.extract(state).get(organization) + "@" }, fg(magenta)),
  text(state => { Project.extract(state).get(name) },               fg(magenta)),
  text(":", NoStyle),
  gitBranch(clean = fg(green), dirty = fg(yellow)).padLeft("[").padRight("]"),
  text(" > ", NoStyle)
))
promptTheme := defaultTheme
shellPrompt := (implicit state => promptTheme.value.render(state))
