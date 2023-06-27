package buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.VcsRoot
import jetbrains.buildServer.configs.kotlin.toId

class BuildServiceDockerImage(
    private val serviceName: String,
    private val artifactName: String,
    private val vcsRoot: VcsRoot,
    private val dependency: BuildType
) : BuildType({
    name = "Build $serviceName Docker Image"
    id = RelativeId(name.toId())

    vcs {
        root(vcsRoot)
    }

    steps {

    }

    dependencies {
        dependency(dependency) {
            snapshot{
            }

            artifacts {
                cleanDestination = true
                artifactRules = artifactName
            }
        }
    }
})