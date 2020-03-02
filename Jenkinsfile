import groovy.json.JsonSlurper
def getVersionTags(username) {

    final APP_NAME = "jenkins-test"
    
    final USER_NAME = username

    def cmd = [ 'bash', '-c', "curl https://api.bitbucket.org/2.0/repositories/${USER_NAME}/${APP_NAME}/refs/tags".toString()]
    def result = cmd.execute().text

    def slurper = new JsonSlurper()
    def json = slurper.parseText(result)
    def tags = new ArrayList()
    if (json.values == null || json.values.size == 0)
      tags.add("unable to fetch tags for ${env.JOB_NAME}")
    else
      tags.addAll(json.values.name)
    return tags.join('\n')
    
}

pipeline {
    agent none
    
    environment {
        IMAGE_TAG = 'latest'
    }
    
    parameters {
        string(name: 'USERNAME', defaultValue: 'mrjenkins', description: 'Username repository')                                        
    }
    
    triggers {
        bitbucketPush()
    }
    
    stages {
        stage('Example') {

            agent any

            steps {
                
                script {
                
                    echo "Hello ${params.USERNAME}"
                    
                }
            }
        }
        
        stage("Deployment Parameters") {

            agent any
        
            options {
              timeout(time: 30, unit: 'SECONDS') 
            }
            
            input {
                message "Please Provide Parameters"
                ok "Ok"
                submitter "alice,bob"
                parameters {
                    choice(name: 'VERSION_TAG', choices: getVersionTags("${params.USERNAME}"), description: 'Available versions')
                }                
            }
            
            steps {
                         
                echo "Version exec: ${VERSION_TAG}"

            }
        }

        stage('Example Test') {
            tools {
                docker 'latest'
            }
            agent {
                docker {
                    image 'katalonstudio/katalon'
                    args "-u root"
                }
            }
            steps {
                sh 'katalon-execute.sh -browserType="Chrome" -retry=0 -statusDelay=15 -testSuitePath="test/TS_RegressionTest"'
            }
        }
    }
    
    post {

        always {
            archiveArtifacts artifacts: 'report/**/*.*', fingerprint: true
            junit 'report/**/JUnit_Report.xml'
        }

		success {
			echo 'Success job'
			slackSend(
				channel: "testchannel",
				color: "good",
				message: ":Success"
			)
		}        
		failure {
			echo "Error job"
			slackSend (
				channel: "testchannel", 
				color: "danger", 
				message: "Error"
			)
		}
	}
}
