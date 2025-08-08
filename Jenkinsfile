// NOTE: This script requires Jenkins Script Security approval in the Jenkins settings
// External scripts loaded with 'load' need to be approved in Jenkins Script Security configuration
//
// How to approve scripts in Jenkins:
// 1. Go to Jenkins Dashboard
// 2. Navigate to 'Manage Jenkins' > 'In-process Script Approval'
// 3. Look for pending scripts or signatures
// 4. Click 'Approve' for the scaScan.groovy script and any other related signatures
// 5. Re-run the pipeline after approving
//
// Alternative solution:
// You can download the template locally and include it in your repository to avoid script security issues:
// 1. Download the script from: https://raw.githubusercontent.com/scantist/devsecops-templates/main/ci-templates/jenkins/scaScan.groovy
// 2. Save it to your repository as 'scaScan.groovy'
// 3. Update the pipeline to use the local version instead of downloading it

pipeline {
  agent any

  environment {
    SCA_BOM_DETECT_DOWNLOAD_URL = credentials('SCA_BOM_DETECT_DOWNLOAD_URL')
    DEVSECOPS_TOKEN             = credentials('DEVSECOPS_TOKEN')
    DEVSECOPS_IMPORT_URL        = credentials('DEVSECOPS_IMPORT_URL')
  }

  stages {
    stage('Setup') {
      steps {
        sh '''
          set -e
          export NVM_DIR="$HOME/.nvm"

          # Install nvm if missing
          if [ ! -d "$NVM_DIR" ]; then
            echo "Installing nvm..."
            curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
          fi

          # Load nvm
          [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"

          # Ensure Node 18
          if ! command -v node >/dev/null 2>&1 || [ "$(node -v | cut -d. -f1 | tr -d v)" -lt 18 ]; then
            nvm install 18
          fi
          nvm use 18

          # Ensure yarn
          if ! command -v yarn >/dev/null 2>&1; then
            npm install -g yarn
          fi

          node -v
          yarn -v
        '''
      }
    }

    stage('Build') {
      steps {
        sh '''
          set -e
          # Load nvm again in this new shell and use Node 18
          export NVM_DIR="$HOME/.nvm"
          [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"
          nvm use 18

          yarn install --frozen-lockfile
          yarn build
        '''
      }
      post {
        success {
          archiveArtifacts artifacts: 'dist/**', fingerprint: true
        }
      }
    }

    // SCA Scan
    stage('SCA Scan') {
      steps {
        script {
          sh '''
            curl -fsSL https://raw.githubusercontent.com/scantist/devsecops-templates/main/ci-templates/jenkins/scaScan.groovy -o scaScan.groovy
          '''
          def sca = load 'scaScan.groovy'
          sca.scaScan()
        }
      }
    }
  }
}
