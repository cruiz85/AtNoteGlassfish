package lector.share.model.client;

public class StudentClient extends UserClient{


	public StudentClient() {
	}

	public StudentClient(Long id, String firstName, String lastName,
			String email, boolean loggedIn, String loginUrl, String logoutUrl,
			boolean isAuthenticated) {
		super(id, firstName, lastName, email, loggedIn, loginUrl, logoutUrl,
				isAuthenticated);
		// TODO Auto-generated constructor stub
	}

}
