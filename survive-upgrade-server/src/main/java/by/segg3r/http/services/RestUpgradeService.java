package by.segg3r.http.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.Application;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.JSONError;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeService;

public class RestUpgradeService {

	@Autowired
	private UpgradeService upgradeService;

	@GET
	@Path("/upgrade/{version}/{applicationPath}")
	@Produces({ "application/json" })
	public Response getUpgradeInfo(@PathParam("version") String version, @PathParam("applicationPath") String applicationPath) {
		try {
			Application application = Application.withPath(applicationPath);
			if (application == null) {
				throw new UpgradeException("Application '" + applicationPath + "' does not exist");
			}
			UpgradeInfo result = upgradeService.getUpgradeInfo(version, application);
			return Response.ok().entity(result).build();
		} catch (UpgradeException e) {
			return Response.serverError().entity(JSONError.of(e.getMessage()))
					.build();
		}
	}

	@GET
	@Path("/file/{version}/{path:.+}")
	public Response getFileContent(@PathParam("version") String version,
			@PathParam("path") String path) {
		try {
			byte[] fileContent = upgradeService.getFileContent(version, path);
			return Response.ok(fileContent).build();
		} catch (UpgradeException e) {
			return Response.serverError().type("application/json").entity(JSONError.of(e.getMessage()))
					.build();
		}
	}

}
