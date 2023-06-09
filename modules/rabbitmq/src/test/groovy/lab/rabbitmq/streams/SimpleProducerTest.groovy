/*
 * This Spock specification was generated by the Gradle 'init' task.
 */
package lab.rabbitmq.streams

import lab.rabbitmq.streams.simple.SimpleProducer
import spock.lang.Specification

class SimpleProducerTest extends Specification {
    def "application has a greeting"() {
        setup:
        def app = new SimpleProducer()

        when:
        def result = app.greeting

        then:
        result != null
    }
}
