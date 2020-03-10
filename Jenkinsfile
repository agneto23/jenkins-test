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

def exists = fileExists 'end-to-end'

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

        stage("Jira parameters") {

            agent any

            options {
              timeout(time: 30, unit: 'SECONDS')
            }

            input {
                message "Please Provide Parameters"
                ok "Ok"
                submitter "alice,bob"
                parameters {
                    string(name: 'KEY_ISSUE', description: 'Key issue', defaultValue: 'KISD-737')
                }
            }

            steps {

                script {

                    def serverInfo = jiraGetServerInfo site: 'jirakruger', failOnError: true
//                     echo serverInfo.data.toString()

                    def issueKey = "${KEY_ISSUE}"

                    def comments = jiraGetComments idOrKey: issueKey, site: 'jirakruger', failOnError: true
                    echo comments.data.toString()

                    def resultComments = new ArrayList()

                    if (comments.data.comments == null || comments.data.comments.size == 0)
                        resultComments.add("unable to fetch comments for ${env.JOB_NAME}")
                    else
                        resultComments.addAll(comments.data.comments.body)

                    echo resultComments.join('\n').toString()

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

                script {

                    env.VERSION_TAG = "${VERSION_TAG}"

                }

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

                    sh "pwd"

                    if (exists) {

                        sh "pwd"

                        echo "test version exec: ${env.VERSION_TAG}"

                        echo 'Start katalon'

                        sh 'katalonc.sh -projectPath="." -browserType="Firefox" -retry=0 -statusDelay=15 -testSuitePath="Test Suites/test" -apiKey="ae74a191-2cb0-4cf0-a61e-1b1d4ffd5774" -sendMail=caguilar@ec.krugercorp.com'

                    } else {
                        echo 'No exists directory'
                    }
                }
                
            }
        }
    }
    
    post {

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
