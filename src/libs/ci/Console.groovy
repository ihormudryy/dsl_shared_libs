!groovy

package org.libs.ci

/**
 *
 * Simple abstraction of command line execution
 * in DSL which wraps around timeout, timestamps, envs and console color mode
 * as well as which system it runs on: Linux or Windows depends on slave
 *
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