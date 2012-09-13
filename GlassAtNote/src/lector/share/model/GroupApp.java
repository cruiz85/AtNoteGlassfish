package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;

@Entity
@Table(name = "group_app")
public class GroupApp implements Serializable, IsSerializable {
//TODO: Revisar esta clase, para que es necesario los bookIds
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "group_user", joinColumns = { @JoinColumn(name = "groupId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") })
    private ArrayList<UserApp> users = new ArrayList<UserApp>();
    //@Basic
   // private ArrayList<String> bookIds; //Por qué se tiene esto?.

    public GroupApp() {
       // this.bookIds = new ArrayList<String>();
    }

    public GroupApp(String name) {
        this();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public ArrayList<String> getBookIds() {
//        return bookIds;
//    }
//
//    public void setBookIds(ArrayList<String> bookIds) {
//        this.bookIds = bookIds;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public ArrayList<UserApp> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserApp> users) {
		this.users = users;
	}
    
}
