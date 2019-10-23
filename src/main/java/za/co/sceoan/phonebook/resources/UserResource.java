package za.co.sceoan.phonebook.resources;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import za.co.sceoan.phonebook.UserManager;
import za.co.sceoan.phonebook.ValidationException;
import za.co.sceoan.phonebook.dto.Message;
import za.co.sceoan.phonebook.dto.User;

@Path("/v1/user")
public class UserResource extends Resource {
    @Inject
    UserManager manager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getUsers(@Context SecurityContext securityContext) {
        return Response.ok(manager.retrieveAllUsers()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    @RolesAllowed("ADMIN")
    public Response getUser(@PathParam("username") String username) {
        User u = manager.retrieveUser(username);
        if (u == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(u).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response addUser(User user) {
        try {
            manager.persistUser(user);
        } catch (ValidationException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getFailedValidations())
                    .build();
        }

        return Response.ok(new Message("Success")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    @PermitAll
    public Response login(User user) {
        if (manager.authenticateUser(user)) {
            return Response.ok(new Message("Success")).build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new Message("Login failed"))
                    .build();
        }
    }
}
