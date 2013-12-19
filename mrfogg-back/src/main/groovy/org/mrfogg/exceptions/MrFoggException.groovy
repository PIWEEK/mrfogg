package org.mrfogg.exceptions

import javax.ws.rs.WebApplicationException
import groovy.transform.InheritConstructors

/**
 * This exception will be the root for exceptions thrown by
 * the code of the application
 *
 j Keep in mind that WebApplicationException have constructors
 * that enable the programmer to map that exception to a
 * known HTTP error code
 */
@InheritConstructors
abstract class MrFoggException extends WebApplicationException {


}
