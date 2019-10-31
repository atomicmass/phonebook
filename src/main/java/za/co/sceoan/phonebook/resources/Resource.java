package za.co.sceoan.phonebook.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Base class for REST resources. Proviides utility methods
 */
public abstract class Resource {

    @Context
    private SecurityContext securityContext;

    /**
     * Gets the email address of the requesting user Uses the basic auth token
     * if present
     *
     * @return
     */
    protected String email() {
        if (securityContext != null && securityContext.getUserPrincipal() != null) {
            return securityContext.getUserPrincipal().getName();
        }
        return null;
    }

}
