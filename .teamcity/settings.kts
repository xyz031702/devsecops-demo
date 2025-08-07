import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.
*/

version = "2023.05"

project {
    // Project details
    description = "DevSecOps Demo Project"

    // Build configuration for the main build job
    buildType {
        // Define a proper build type ID with project name prefix to avoid default naming
        id("DevSecOpsDemo_Build")
        name = "Build"
        description = "Builds the project using Node.js"
        
        // Make this a top-level build configuration that gets discovered by TeamCity
        buildTypesOrder = listOf(this)

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
                dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
                dockerPull = true
            }
            
            // Build project
            script {
                name = "Build"
                scriptContent = "yarn build"
                dockerImage = "node:18"
                dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
                dockerPull = true
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
