package buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.toId

class DeployService(
    private val serviceName: String,
    dependency: BuildType
) : BuildType({
    name = "Deploy $serviceName"
    id = RelativeId(name.toId())

    steps {

    }

    dependencies {
        dependency(dependency) {
            snapshot{
            }
        }
    }
})