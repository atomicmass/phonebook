package za.co.sceoan.phonebook.resources;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import za.co.sceoan.phonebook.PhonebookManager;
import za.co.sceoan.phonebook.ValidationException;
import za.co.sceoan.phonebook.dto.Contact;
import za.co.sceoan.phonebook.dto.Message;

@Path("/v1/phonebook")
public class PhonebookResource extends Resource {
    @Inject 
    PhonebookManager manager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/initial")
    public Response getInitials() {
        List<String> initials = manager.listInitials(email());
        return Response.ok(initials).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/initial/{initial}")
    public Response getByInitial(@PathParam("initial") String initial) {
        List<Contact> c = manager.listByInitial(email(), initial);
        return Response.ok(c).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response getAllContacts() {
        List<Contact> c = manager.listAll(email());
        return Response.ok(c).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response postContact(Contact contact) {
        try {
            manager.persistContact(email(), contact);
        } catch (ValidationException ex) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity(ex.getFailedValidations())
                    .build();
        }
        return Response.ok(new Message("Success")).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Contact c = manager.find(email(), id);
        if(c == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(c).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search")
    public Response getById(@QueryParam("s") String regex) {
        List<Contact> c = manager.search(email(), regex);
        return Response.ok(c).build();
    }
    
    @DELETE
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        manager.delete(email(), id);
        return Response.ok().build();
    }
    
    @DELETE
    @RolesAllowed({"ADMIN"})
    @Path("/all")
    public Response delete() {
        manager.deleteAll();
        return Response.ok().build();
    }
}
