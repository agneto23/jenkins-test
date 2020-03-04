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
        string(name: 'USERNAME', defaultValue: 'caaguilarn', description: 'Username repository')
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
        
        stage("Deployment parameters") {

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

        stage('Docker katalon test') {
            agent {
                kubernetes {
                      label 'continuous-delivery-builder'
                      yaml """
                apiVersion: v1
                kind: Pod
                metadata:
                  labels:
                    jenkins-agent: katalon-jnlp-slave
                    jenkins/katalon-slave: true
                spec:
                  containers:
                  - name: katalon
                    image: katalonstudio/katalon
                    imagePullPolicy: IfNotPresent
                    args:
                    - cat
                    tty: true
                """
                }
            }
            steps {
                container('katalon') {
                echo 'Start katalon'
                      sh 'katalonc.sh -projectPath="/home/jenkins/agent/workspace/jenkins-test_master/logintest" -browserType="Chrome" -retry=0 -statusDelay=15 -testSuitePath="Test Suites/test" -apiKey="ae74a191-2cb0-4cf0-a61e-1b1d4ffd5774"'
                }
            }
        }
    }
    
    post {

        always {
//             archiveArtifacts artifacts: 'reports/**/*.*', fingerprint: true
//             junit 'reports/**/JUnit_Report.xml'
//
            node('master') {
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '/home/jenkins/agent/workspace/jenkins-test_master/logintest/Reports', reportFiles: 'index.html', reportName: 'Katalon Report', reportTitles: ''])
            }
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
