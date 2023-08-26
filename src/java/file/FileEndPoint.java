/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package file;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author bishal
 */
@Path("/")
public class FileEndPoint {

    private static final String BASE_PATH = System.getenv("OZPROPERTYHUB_UPLOAD_LOCATION");

    @GET
    @Path("/{filename}")
    public Response getFile(@PathParam("filename") String filename) {
        try {
            System.out.println("here he comes");
            System.out.println(filename + "filename");
            File file = new File(BASE_PATH, filename);
            if (file.exists()) {
                return Response.ok(file, Files.probeContentType(Paths.get(file.getPath()))).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
