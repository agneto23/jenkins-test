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
		always {
			echo "Pipeline finalizado del repositorio"
		}
		success {
			echo 'La linea de construccion finalizo exitosamente'
			slackSend(
				channel: "testchannel",
				color: "good",
				message: ":simple_smile::computer: *ÉXITO EN CONSTRUCCIÓN* en construcción"
			)
		}        
		failure {
			echo "La linea de construccion finalizo con errores "
			slackSend (
				channel: "testchannel", 
				color: "danger", 
				message: ":disappointed_relieved::sos: *ERROR EN CONSTRUCCIÓN* en construcción"
			)
		}
		unstable {
			echo 'La linea de construccion finalizo de forma inestable' 
			slackSend (
				channel: "testchannel", 
				color: "warning", 
				message: ":worried::warning: *ALERTA CONSTRUCCIÓN INESTABLE* en construcción"
			)
		}
	}
}
