package org.mrfogg.resources

import org.mrfogg.domains.User
import org.mrfogg.test.ResourceSpec

class HelloWorldResourceSpec extends ResourceSpec {

    void setUpResources() {
        jersey.addResource(new HelloWorldResource())
    }

    def 'Showing a hello world greeting'() {
        when: 'Invoking the resource endpoint'
            def user =
                jersey.client().
                    resource('/hello-world?name=john').
                    get(User)
        then: 'The server response should have a Hello greeting in the beggining'
             user.email.startsWith('john')
    }


}
