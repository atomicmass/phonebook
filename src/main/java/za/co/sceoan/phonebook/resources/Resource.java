package za.co.sceoan.phonebook.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Base class for REST resources. Proviides utility methods
 */
public abstract class Resource {
    @Context
    private SecurityContext securityContext;
    
    protected String email() {
        return securityContext.getUserPrincipal().getName();
    }
    
}
