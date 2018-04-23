package org.libs.ci

/**
 * Simple abstraction of command line interface which wraps timestamps, timeout, 
 * envs and console color mode as well as OS it runs on: Linux or Windows
 */
def execute(String command, envs=[], time=20) {
    timestamps{
        timeout(time) {
            withEnv(envs){
                wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'xterm']) {
                    if (isUnix()) {
                        return sh(script: command)
                    } else {
                        return powershell(command)
                    }
                }
            }
        }
    }
}