package com.xyz.resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.xyz.protos.AuthResponse;
import com.xyz.util.AuthUtil;

@Path("/")
public class HomeResource {
   
    @GET
    public Response get(@HeaderParam("Authorization") String idToken) {
        AuthResponse authResponse = AuthUtil.checkAuthorization(idToken);
        if (authResponse.getResponseStatus() != Response.Status.OK) {
            return Response.status(authResponse.getResponseStatus()).entity("Authentication failed").build();
        }

        String userName = authResponse.getUserName();

        return Response.ok(userName).build();
    }
}
