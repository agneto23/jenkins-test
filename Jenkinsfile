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
    parameters {
        string(name: 'USERNAME', defaultValue: 'MrJenkins', description: 'Username repository')                                        
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
