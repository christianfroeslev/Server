package view.endpoints;

import com.google.gson.Gson;
import logic.StudentController;
import security.Digester;
import shared.ReviewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Kasper on 19/10/2016.
 */

@Path("/api/student")
public class StudentEndpoint extends UserEndpoint {

    @POST
    @Consumes("application/json")
    @Path("/review")
    public Response addReview(String json) {

        Gson gson = new Gson();
        ReviewDTO review = new Gson().fromJson(json, ReviewDTO.class);

        StudentController studentCtrl = new StudentController();
        boolean isAdded = studentCtrl.addReview(review);

        if (isAdded) {
            String toJson = gson.toJson(Digester.encrypt(gson.toJson(isAdded)));

            return successResponse(200, toJson);

        } else {
            return errorResponse(404, "Failed. Couldn't get reviews.");
        }
    }

    @OPTIONS
    @Path("/review")
    public Response optionsCreateReview() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
    }

    @DELETE
    @Path("/review/{userId}/{reviewId}")
    public Response deleteReview(@PathParam("userId") int userId, @PathParam("reviewId") int reviewId) {
        Gson gson = new Gson();

        StudentController studentCtrl = new StudentController();
        boolean isDeleted = studentCtrl.softDeleteReview(userId, reviewId);

        if (isDeleted) {
            String toJson = gson.toJson(Digester.encrypt(gson.toJson(isDeleted)));

            return successResponse(200, toJson);
        } else {
            return errorResponse(404, "Failed. Couldn't delete the chosen review.");
        }
    }
}
