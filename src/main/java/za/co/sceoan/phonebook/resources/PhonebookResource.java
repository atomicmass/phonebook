package za.co.sceoan.phonebook.resources;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import za.co.sceoan.phonebook.PhonebookManager;
import za.co.sceoan.phonebook.ValidationException;
import za.co.sceoan.phonebook.dto.Contact;
import za.co.sceoan.phonebook.dto.Message;
import za.co.sceoan.phonebook.dto.User;

@Path("/v1/phonebook")
public class PhonebookResource {
    @Inject 
    private PhonebookManager manager;
    
    @Context
    private SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/initials")
    public Response getInitials(@Context SecurityContext sc) {
        String email = sc.getUserPrincipal().getName();
        List<String> initials = manager.listInitials(email);

        return Response.ok(initials).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response postContact(Contact contact) {
        String email = securityContext.getUserPrincipal().getName();
        try {
            manager.persistContact(email, contact);
        } catch (ValidationException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getFailedValidations())
                    .build();
        }

        return Response.ok(new Message("Success")).build();
    }
}
