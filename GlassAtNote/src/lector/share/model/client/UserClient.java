package lector.share.model.client;

public class UserClient {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;

	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private boolean isAuthenticated = true;

	public UserClient() {
	}

	public UserClient(Long id, String firstName, String lastName, String email,
			boolean loggedIn, String loginUrl, String logoutUrl,
			boolean isAuthenticated) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.loggedIn = loggedIn;
		this.loginUrl = loginUrl;
		this.logoutUrl = logoutUrl;
		this.isAuthenticated = isAuthenticated;
	}

	public UserClient(String email) {
		super();
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}


	@Override
	public String toString() {
		return "name:" + firstName + " lastName:" + lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
