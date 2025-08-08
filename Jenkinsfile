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

    stage('SCA Scan') {
      steps {
        script {
          evaluate(new URL('https://github.com/scantist/devsecops-templates/blob/main/ci-templates/jenkins/bom-sca-scan.jenkinsfile').text)
          scaScan() // Runs with all secrets loaded from Jenkins credentials 
        }
      }
    }
  }
}
