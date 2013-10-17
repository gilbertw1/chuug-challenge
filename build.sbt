
name := "Word Frequency"

version := "1.0"

organization := "com.gilbert"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq (
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.0"
)

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)