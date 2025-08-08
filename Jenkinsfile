pipeline {
    agent any
    
    environment {
        // Define environment variables similar to GitLab CI
        SCA_BOM_DETECT_DOWNLOAD_URL = 'https://download.scantist.io/sca-bom-detect.jar'
        DEVSECOPS_TOKEN = credentials('DEVSECOPS_TOKEN')
        DEVSECOPS_IMPORT_URL = credentials('DEVSECOPS_IMPORT_URL')
    }
    
    stages {
        stage('Setup') {
            steps {
                // Use nvm to install Node.js locally for the Jenkins user
                sh '''
                    # Install nvm if not available
                    export NVM_DIR="$HOME/.nvm"
                    if [ ! -d "$NVM_DIR" ]; then
                        echo "Installing nvm..."
                        curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
                    fi
                    
                    # Load nvm
                    [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
                    
                    # Install Node.js if not already installed
                    if ! command -v node &> /dev/null || [ "$(node --version | cut -d. -f1 | tr -d 'v')" -lt 18 ]; then
                        echo "Installing Node.js 18..."
                        nvm install 18
                        nvm use 18
                    fi
                    
                    # Install yarn
                    if ! command -v yarn &> /dev/null; then
                        echo "Installing Yarn..."
                        npm install -g yarn
                    fi
                    
                    # Display versions
                    echo "Node.js version: $(node --version)"
                    echo "Yarn version: $(yarn --version)"
                '''
            }
        }
        
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
