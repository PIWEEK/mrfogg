package org.mrfogg.test

import com.yammer.dropwizard.testing.ResourceTest
import spock.lang.Shared
import spock.lang.Specification

abstract class ResourceSpec extends Specification {

   abstract void setUpResources()

   @Shared ResourceTest jersey = new ResourceTest() {
      @Override
      protected void setUpResources() {}
   }

   void setupSpec(){
      setUpResources()
      jersey.setUpJersey()
   }

   void cleanupSpec() {
      jersey.tearDownJersey()
   }

}
