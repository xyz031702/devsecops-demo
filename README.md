# AppSec CI/CD Integration Demo

This is a demonstration project showcasing application security integration in CI/CD pipelines. The project demonstrates how to integrate Software Composition Analysis (SCA) scanning into modern DevOps workflows.

## Supported CI/CD Platforms

This demo currently supports the following CI/CD platforms:

- **GitLab CI** - Complete SCA integration template
- **Jenkins** - Pipeline configuration for SCA scanning
- **TeamCity** - Build configuration with security scanning
- **Circle CI** - Workflow integration for vulnerability detection

## Features

- 🔍 **Software Composition Analysis (SCA)** - Automated vulnerability scanning of dependencies
- 🛡️ **Security-first CI/CD** - Integrated security scanning in build pipelines
- 📊 **Comprehensive Reporting** - Detailed vulnerability reports and metrics
- 🔧 **Multi-platform Support** - Works across different CI/CD environments
- 🌐 **Enterprise Ready** - Supports both cloud and on-premises deployments

## Project Structure

```
├── .gitlab-ci.yml          # GitLab CI pipeline configuration
├── sca-bom-scan.yml        # Reusable SCA scanning template
├── package.json            # Node.js dependencies (with intentional vulnerabilities)
├── hello.ts                # Sample TypeScript application
├── tsconfig.json           # TypeScript configuration
└── README.md               # This file
```

## Quick Start

1. **Clone this repository**
2. **Configure your CI/CD platform** using the appropriate template
3. **Set required environment variables**:
   - `DEVSECOPS_TOKEN` - Your security scanning service token
   - `DEVSECOPS_IMPORT_URL` - Import endpoint for vulnerability data
   - `SCA_BOM_DETECT_DOWNLOAD_URL` - Scanner download URL

4. **Run your pipeline** and review security scan results

## Environment Variables

| Variable | Description | Required |
|----------|-------------|---------|
| `DEVSECOPS_TOKEN` | Authentication token for security service | Yes |
| `DEVSECOPS_IMPORT_URL` | Endpoint for importing scan results | Optional |
| `SCA_BOM_DETECT_DOWNLOAD_URL` | URL to download SCA scanner | Yes |

## Jenkins Setup

1. **Start Jenkins locally** using Docker:
   ```bash
   ./ci-templates/jenkins/start-jenkins.sh
   ```

2. **Access Jenkins** at http://localhost:8080

3. **Create Pipeline Job:**
   - Create a new Pipeline job
   - Select "Pipeline script from SCM" as the definition
   - Choose Git as SCM and enter repository URL
   - Set branch specifier and script path to "Jenkinsfile"

4. **Configure Credentials:**
   - Go to Manage Jenkins → Credentials
   - Add Secret Text credentials with IDs:
     - `DEVSECOPS_TOKEN`
     - `DEVSECOPS_IMPORT_URL` (if needed)
     - `SCA_BOM_DETECT_DOWNLOAD_URL`

5. **Run Build** by clicking "Build Now"

## License

MIT License - This is a demonstration project for educational purposes..
