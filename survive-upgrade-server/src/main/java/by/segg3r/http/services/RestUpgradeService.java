package by.segg3r.http.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.JSONError;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeService;

@Path("/upgrade")
public class RestUpgradeService {

	@Autowired
	private UpgradeService upgradeService;

	@GET
	@Path("/{version}")
	@Produces({ "application/json" })
	public Response getUpgradeInfo(@PathParam("version") String version) {
		try {
			UpgradeInfo result = upgradeService.getUpgradeInfo(version);
			return Response.ok().entity(result).build();
		} catch (UpgradeException e) {
			return Response.serverError().entity(JSONError.of(e.getMessage()))
					.build();
		}
	}

}
