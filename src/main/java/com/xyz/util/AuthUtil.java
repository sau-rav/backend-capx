package com.xyz.util;

import javax.ws.rs.core.Response;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.xyz.protos.AuthResponse;

public class AuthUtil {

    public static AuthResponse checkAuthorization(String idToken) {
        if (idToken == null || idToken.isEmpty()) {
            return new AuthResponse.Builder()
                    .responseStatus(Response.Status.UNAUTHORIZED)
                    .build();
        }

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            String userName = decodedToken.getName(); // Assuming FirebaseToken has a method to get the user's name
            String email = decodedToken.getEmail();

            return new AuthResponse.Builder()
                    .responseStatus(Response.Status.OK)
                    .uid(uid)
                    .userName(userName)
                    .email(email)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResponse.Builder()
                    .responseStatus(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }
}