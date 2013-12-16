package org.mrfogg.resources

import org.mrfogg.test.ResourceSpec

class HelloWorldResourceSpec extends ResourceSpec {

    void setUpResources() {
        jersey.addResource(new HelloWorldResource())
    }

    def 'Showing a hello world greeting'() {
        when: 'Invoking the resource endpoint'
            def greetings =
                jersey.client().resource('/hello-world/').get(String)
        then: 'The server response should have a Hello greeting in the beggining'
            greetings.startsWith('HELLO')
    }


}
