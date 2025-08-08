pipeline {
    agent {
        docker {
            image 'node:18'
            // Run inside a Docker container using the node:18 image
        }
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
                // Similar to the build job in .gitlab-ci.yml
                sh 'yarn install --frozen-lockfile'   // install deps
                sh 'yarn build'                       // compile to dist/
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
