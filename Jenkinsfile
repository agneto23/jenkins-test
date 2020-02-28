import groovy.json.JsonSlurper
def getDockerImages() {
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
      tags.addAll(json.tags)
    return tags.join('\n')
}

pipeline {
    agent any
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')

        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')

        booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')

        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
        
        choices: ['dev','qa'].join('\n'), description: 'Please select the Environment'),
                                        
        choice(name: 'IMAGE_TAG', choices: getDockerImages(), description: 'Available Docker Images'),

        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
    }
    stages {
        stage('Example') {
            steps {
                echo "Hello ${params.PERSON}"

                echo "Biography: ${params.BIOGRAPHY}"

                echo "Toggle: ${params.TOGGLE}"

                echo "Choice: ${params.CHOICE}"

                echo "Password: ${params.PASSWORD}"
            }
        }
    }
}
