import groovy.json.JsonSlurper
def getVersionTags() {
    final API_KEY = "FOOBARAPIKEY"
    final REPO_NAME = "service-docker"
    final APP_NAME = "myapp"

    def cmd = [ 'bash', '-c', "curl https://api.bitbucket.org/2.0/repositories/caaguilarn/jenkins-test/refs/tags".toString()]
    def result = cmd.execute().text

    def slurper = new JsonSlurper()
    def json = slurper.parseText(result)
    def tags = new ArrayList()
    if (json.values == null || json.values.size == 0)
      tags.add("unable to fetch tags for ${APP_NAME}")
    else
      tags.addAll(json.values.name)
    return tags.join('\n')
}

pipeline {
    agent any
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')

        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')

        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
                                        
    }
    stages {
        stage("Deployment Parameters") {
            timeout(time: 30, unit: 'SECONDS') {
                
                input {
                message "Should we continue?"
                ok "Yes, we should."
                submitter "alice,bob"
                parameters {
                    choice(name: 'VERSION_TAG', choices: getVersionTags(), description: 'Available versions')
                }
            }
                
            }
            steps {
            
                echo "Hello, ${VERSION_TAG}, nice to meet you."

            }
        }
        stage('Example') {
            steps {
                
                script {
                
                    echo "Hello ${params.PERSON}"

                    echo "Biography: ${params.BIOGRAPHY}"

                    echo "Choice: ${params.CHOICE}"

                    echo "Version tag: ${VERSION_TAG}"
                }
            }
        }
    }
}
