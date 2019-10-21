package za.co.sceoan.phonebook.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.sceoan.phonebook.dao.UserDao;
import za.co.sceoan.phonebook.dao.ValidationException;
import za.co.sceoan.phonebook.dto.User;

@Path("/user")
public class UserResource {
    
    @Inject
    private UserDao dao;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(dao.retrieveAllUsers()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response getUser(@PathParam("username") String username) {
        User u = dao.retrieveUser(username);
        if(u == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(u).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        try {
            dao.persistUser(user);
        } catch (ValidationException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getFailedValidations())
                    .build();
        }
        
        return Response.ok().build();
    }
}