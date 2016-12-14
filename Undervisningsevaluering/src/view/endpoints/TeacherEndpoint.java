package view.endpoints;

import com.google.gson.Gson;
import logic.StudentController;
import security.Digester;
import shared.ReviewDTO;

import javax.ws.rs.DELETE;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Kasper on 19/10/2016.
 */
@Path("/api/teacher")
public class TeacherEndpoint extends UserEndpoint {


    @OPTIONS
    @Path("/review/delete")
    public Response optionsReview() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    @DELETE
    @Path("/review/delete")
    public Response deleteReview(String data) {
        Gson gson = new Gson();
        StudentController studentCtrl = new StudentController();
        ReviewDTO review = gson.fromJson(data, ReviewDTO.class);
        boolean isDeleted = studentCtrl.softDeleteReview(review.getId());

        if (isDeleted) {
            String toJson = gson.toJson(Digester.encrypt(gson.toJson(isDeleted)));

            return successResponse(200, toJson);
        } else {
            return errorResponse(404, "Failed. Couldn't delete the chosen review.");
        }
    }
}