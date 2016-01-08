package by.segg3r.http.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.JSONError;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeService;

public class RestUpgradeService {

	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired
	private UpgradeService upgradeService;

	@GET
	@Path("/upgrade/{version}/{applicationPath}")
	@Produces({ APPLICATION_JSON })
	public Response getUpgradeInfo(@PathParam("version") String version,
			@PathParam("applicationPath") String applicationPath) {
		try {
			Application application = Application.withPath(applicationPath);
			verifyApplication(applicationPath, application);
			ApplicationVersion applicationVersion = new ApplicationVersion(
					application, version);

			UpgradeInfo result = upgradeService
					.getUpgradeInfo(applicationVersion);
			return Response.ok().entity(result).build();
		} catch (UpgradeException e) {
			return Response.serverError().entity(JSONError.of(e.getMessage()))
					.build();
		}
	}

	@GET
	@Path("/file/{version}/{applicationPath}/{path:.+}")
	public Response getFileContent(@PathParam("version") String version,
			@PathParam("applicationPath") String applicationPath,
			@PathParam("path") String path) {
		try {
			Application application = Application.withPath(applicationPath);
			verifyApplication(applicationPath, application);
			ApplicationVersion applicationVersion = new ApplicationVersion(
					application, version);

			if (!upgradeService.fileExists(applicationVersion, path)) {
				return Response.status(Status.FORBIDDEN)
						.type(APPLICATION_JSON)
						.entity(JSONError.of("This file does not exist"))
						.build();
			}

			byte[] fileContent = upgradeService.getFileContent(
					applicationVersion, path);
			return Response.ok(fileContent).build();
		} catch (UpgradeException e) {
			return Response.serverError().type(APPLICATION_JSON)
					.entity(JSONError.of(e.getMessage())).build();
		}
	}

	private void verifyApplication(String applicationPath,
			Application application) throws UpgradeException {
		if (application == null) {
			throw new UpgradeException("Application '" + applicationPath
					+ "' does not exist");
		}
	}

}
