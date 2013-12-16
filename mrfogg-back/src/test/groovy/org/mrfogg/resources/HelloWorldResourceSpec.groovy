package org.mrfogg.resources

import org.mrfogg.domains.Greeting
import org.mrfogg.daos.GreetingDAO
import org.mrfogg.test.ResourceSpec

class HelloWorldResourceSpec extends ResourceSpec {

    void setUpResources() {
        def mockedDAO = Mock(GreetingDAO) {
                1 * persist(_) >> new Greeting(message: 'it worked')
            }
        assert mockedDAO instanceof GreetingDAO
        jersey.addResource(new HelloWorldResource(mockedDAO))
    }

    def 'Showing a hello world greeting'() {
        when: 'Invoking the resource endpoint'
            def greetings =
                jersey.client().
                    resource('/hello-world').
                    get(Greeting)
        then: 'The server response should have a Hello greeting in the beggining'
             greetings.message.startsWith('HELLO')
    }


}
