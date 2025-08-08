import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

version = "2023.05"

project {

    description = "DevSecOps Demo Project"

    buildType {
        name = "Build"            // TeamCity will auto-create a safe ID
        vcs { root(DslContext.settingsRoot) }

        steps {
            // Install deps ➜ build, in a single Dockerised step
            script {
                scriptContent = """
                    yarn install --frozen-lockfile
                    yarn build
                """.trimIndent()
                dockerImage = "node:18"
                dockerPull  = true          // always grab freshest image
            }
        }

        artifacts { artifactRules = "dist/** => dist.zip" }

        triggers { vcs {} }        // run on every push
    }
}
