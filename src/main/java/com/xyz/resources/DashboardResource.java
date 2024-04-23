package com.xyz.resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.xyz.protos.AuthResponse;
import com.xyz.util.AuthUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.xyz.protos.User;
import java.util.Map;
import java.util.TreeMap;

@Path("/dashboard")
public class DashboardResource {

    @GET
    public Response get(@HeaderParam("Authorization") String idToken, @HeaderParam("ReferralCode") String referralCode) {
        AuthResponse authResponse = AuthUtil.checkAuthorization(idToken);
        if (authResponse.getResponseStatus() != Response.Status.OK) {
            return Response.status(authResponse.getResponseStatus()).entity("Authentication failed").build();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        // Check if user is already present or not if not enter the data if present return the rank
        String uidToCheck = authResponse.getUid();

        // Add ValueEventListener to check if data is present
        usersRef.child(uidToCheck).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data exists at the specified location
                    System.out.println("Data is present for UID: " + uidToCheck);
                } else {
                    // Data does not exist at the specified location
                    System.out.println("No data found for UID: " + uidToCheck + " so putting data in table");
                    User user = new User.Builder(authResponse.getUid(), authResponse.getEmail())
                                            .setName(authResponse.getUserName())
                                            .setReferredBy(referralCode)
                                            .setRefCount(0).build();
                                            // Add some logic for generation of referral code here and useFunction setReferralCode
                    usersRef.child(authResponse.getUid()).setValue(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved: " + databaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                System.out.println("Error occurred while checking data presence: " + databaseError.getMessage());
            }
        });


        //usersRef.orderByChild("refCount").addListenerForSingleValueEvent(new ValueEventListener() {
        //    @Override
        //    public void onDataChange(DataSnapshot dataSnapshot) {
        //        Map<String, Integer> sortedUserCounts = new TreeMap<>();
        //        int rank = 1;

        //        // Sort users by refCount in descending order
        //        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
        //            User user = childSnapshot.getValue(User.class);
        //            sortedUserCounts.put(childSnapshot.getKey(), user.getRefCount());
        //        }

        //        // Find the rank of the user
        //        for (Map.Entry<String, Integer> entry : sortedUserCounts.entrySet()) {
        //            if (entry.getKey().equals(uidToCheck)) {
        //                break;
        //            }
        //            rank++;
        //        }

        //        // Return the rank and referral code
        //        Response.ResponseBuilder responseBuilder = Response.ok("ReferralCode: " + referralCode + ", Rank: " + rank);
        //    }

        //    @Override
        //    public void onCancelled(DatabaseError databaseError) {
        //        // Handle errors
        //        System.out.println("Error occurred while getting user rank: " + databaseError.getMessage());
        //    }
        //});
        return Response.ok("UID: " + authResponse.getUid()).build();
    }
}