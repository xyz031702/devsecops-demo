pipeline {
    agent any
    
    // You'll need to ensure Node.js is installed on the Jenkins agent
    tools {
        nodejs 'NodeJS' // This assumes you have NodeJS configured in Jenkins global tool configuration
    }
    
    environment {
        // Define environment variables similar to GitLab CI
        SCA_BOM_DETECT_DOWNLOAD_URL = 'https://download.scantist.io/sca-bom-detect.jar'
        DEVSECOPS_TOKEN = credentials('DEVSECOPS_TOKEN')
        DEVSECOPS_IMPORT_URL = credentials('DEVSECOPS_IMPORT_URL')
    }
    
    stages {
        stage('Build') {
            steps {
                // Set up the correct Node.js environment
                nodejs('NodeJS') {
                    // Similar to the build job in .gitlab-ci.yml
                    sh 'yarn install --frozen-lockfile'   // install deps
                    sh 'yarn build'                       // compile to dist/
                }
            }
            
            post {
                success {
                    // Archive the build artifacts (similar to GitLab artifacts)
                    archiveArtifacts artifacts: 'dist/**', fingerprint: true
                }
            }
        }
    }
}
