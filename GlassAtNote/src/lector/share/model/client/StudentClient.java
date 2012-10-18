package lector.share.model.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StudentClient extends UserClient implements IsSerializable{


	public StudentClient() {
	}

	public StudentClient(Long id, String firstName, String lastName,
			String email, String password, Date createdDate,boolean isConfirmed) {
		super(id, firstName, lastName, email, password,createdDate,isConfirmed);
		// TODO Auto-generated constructor stub
	}

}
