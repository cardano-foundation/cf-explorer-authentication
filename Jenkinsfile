def COLOR_MAP = [
    'SUCCESS': 'good',
    'FAILURE': 'danger',
]

def secretFolder = '~/configs/cardano-authentication'

pipeline {
    agent any

    stages {
        stage('Build') {
            steps { 
                sh "cp ${secretFolder}/settings.xml ./.m2/settings.xml"
                sh "docker build -t cardano-authentication ."
                echo 'Build successfully!!!'
            }
        }
        stage('Deploy') {
            when {
                branch 'develop'
            }
            steps {
                echo 'Deploying....'
                script {
                    if (env.BRANCH_NAME == 'develop') {
                        envFileDeploy = secretFolder + '/develop.env'
                    }
                    if (env.BRANCH_NAME == 'test') {
                        envFileDeploy =  secretFolder + '/test.env'
                    }
                    if (env.BRANCH_NAME == 'prod') {
                        envFileDeploy =  secretFolder + '/prod.env'
                    }
                }
                sh "docker compose --env-file ${envFileDeploy} -p ${env.BRANCH_NAME} up -d"
                sh "docker images -f 'dangling=true' -q --no-trunc | xargs docker rmi &> /dev/null" 
                echo 'Deployment Done!!!'
            }
        }
    }
    post {
        always {
            script {
                Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
            }
            slackSend channel: "#build-group-test",
                    color: COLOR_MAP[currentBuild.currentResult],
                    message: "*${currentBuild.currentResult}:* ${env.JOB_NAME} build ${env.BUILD_NUMBER} by commit author: ${Author_ID} \n More information at: ${env.BUILD_URL}"
        }
    }
}
