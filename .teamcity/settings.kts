import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.
*/

version = "2021.2"

project {
    // Project details
    description = "DevSecOps Demo Project"

    // Build configuration for the main build job
    buildType {
        id("Build")
        name = "Build"
        description = "Builds the project using Node.js"

        // Use the default VCS root from the TeamCity project
        vcs {
            root(DslContext.settingsRoot)
        }

        // Define build steps
        steps {
            // Install dependencies
            script {
                name = "Install dependencies"
                scriptContent = "yarn install --frozen-lockfile"
                dockerImage = "node:18"
            }
            
            // Build project
            script {
                name = "Build"
                scriptContent = "yarn build"
                dockerImage = "node:18"
            }
        }

        // Artifacts to publish
        artifacts {
            // Save the compiled JS
            artifact {
                artifactRules = "dist/** => dist.zip"
            }
        }

        // VCS Trigger to run build on each commit
        triggers {
            vcs {
                branchFilter = "+:*"
            }
        }
    }
}
