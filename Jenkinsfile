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
    agent any
    
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
            steps {
                
                script {
                
                    echo "Hello ${params.USERNAME}"
                    
                }
            }
        }
        
        stage("Deployment Parameters") {
        
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
             
                IMAGE_TAG = VERSION_TAG
            
                echo "Version exec: ${IMAGE_TAG}"

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
