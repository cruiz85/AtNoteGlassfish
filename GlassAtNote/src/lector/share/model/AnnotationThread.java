package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import javax.persistence.Table;

@Entity
@Table(name = "annotation_thread")
public class AnnotationThread implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fathertId")
	private AnnotationThread father;
	@OneToMany(mappedBy = "father", cascade = CascadeType.ALL)
	private List<AnnotationThread> subThreads = new ArrayList<AnnotationThread>();
	
	private Long annotationId; 

	private String comment;
	private Long userId;    
	private String userName;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;

	public AnnotationThread() {
		super();
	}

	public AnnotationThread(AnnotationThread father,
			List<AnnotationThread> subThreads, Long annotationId,
			String comment, Long userId, String userName, Date createdDate) {
		super();
		this.father = father;
		this.subThreads = subThreads;
		this.annotationId = annotationId;
		this.comment = comment;
		this.userId = userId;
		this.userName = userName;
		this.createdDate = createdDate;
	}

	public AnnotationThread getFather() {
		return father;
	}

	public void setFather(AnnotationThread father) {
		this.father = father;
	}

	public List<AnnotationThread> getSubThreads() {
		return subThreads;
	}

	public void setSubThreads(List<AnnotationThread> subThreads) {
		this.subThreads = subThreads;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAnnotationId() {
		return annotationId;
	}

	public void setAnnotationId(Long annotationId) {
		this.annotationId = annotationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
