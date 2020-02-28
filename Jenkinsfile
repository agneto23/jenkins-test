import groovy.json.JsonSlurper
def getVersionTags() {

    def cmd = [ 'bash', '-c', "curl https://api.bitbucket.org/2.0/repositories/caaguilarn/${env.JOB_NAME}/refs/tags".toString()]
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
                message "Should we continue?"
                ok "Yes, we should."
                submitter "alice,bob"
                parameters {
                    choice(name: 'VERSION_TAG', choices: getVersionTags(), description: 'Available versions')
                }                
            }
            steps {
            
                echo "Hello, ${VERSION_TAG}, nice to meet you."

            }
        }
    }
}
