package buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.VcsRoot
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.toId

class CompileService(
    private val serviceName: String,
    private val artifactName: String,
    private val vcsRoot: VcsRoot
) : BuildType({
    name = "Compile $serviceName"
    id = RelativeId(name.toId())
    artifactRules = "build/libs/${artifactName}-1.0-SNAPSHOT.jar"

    vcs {
        root(vcsRoot)
    }

    steps {
        gradle {
            tasks = "clean build"
            gradleWrapperPath = ""
        }
    }
})