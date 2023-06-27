package buildTypes

import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.toId
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

data class Pipeline(
    val serviceName: String,
    val artifactName: String,
    val repositoryUrl: String
)

fun Project.addPipeline(pipeline: Pipeline) {
    val gitRepository = GitVcsRoot {
        id = RelativeId("vcsRoot_${pipeline.serviceName.toId()}")
        name = "${pipeline.serviceName} VCS Root"
        url = pipeline.repositoryUrl
        branch = "refs/heads/main"
        authMethod = uploadedKey {
            userName = "git"
            uploadedKey = "id_rsa"
        }
    }
    vcsRoot(gitRepository)

    val compile = CompileService(
        serviceName = pipeline.serviceName,
        vcsRoot = gitRepository,
        artifactName = pipeline.artifactName
    )
    buildType(compile)

    val dockerImage = BuildServiceDockerImage(
        serviceName = "Build ${pipeline.serviceName}",
        vcsRoot = gitRepository,
        artifactName = pipeline.artifactName,
        dependency = compile
    )
    buildType(dockerImage)

    val deploy = DeployService(
        serviceName = "Deploy ${pipeline.serviceName}",
        dependency = dockerImage
    )
}