package by.segg3r.http.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/upgrade")
public class UpgradeRestService {

	private static final class Person {
		private String name;

		public Person(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@Produces({ "application/json" })
	@GET
	public Person test() {
		return new Person("Hello, world!");
	}

}
