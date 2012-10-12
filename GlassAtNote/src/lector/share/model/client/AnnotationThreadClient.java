package lector.share.model.client;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnotationThreadClient{

	private Long id;

	private AnnotationThreadClient father;

	private List<AnnotationThreadClient> subThreads = new ArrayList<AnnotationThreadClient>();
	
	private AnnotationClient annotation;
	private String comment;
	private Long userId;    
	private String userName;
	private Date createdDate;

	public AnnotationThreadClient() {
		super();
	}

	public AnnotationThreadClient(AnnotationThreadClient father,
			List<AnnotationThreadClient> subThreads, AnnotationClient annotation,
			String comment, Long userId, String userName, Date createdDate) {
		super();
		this.father = father;
		this.subThreads = subThreads;
		this.annotation = annotation;
		this.comment = comment;
		this.userId = userId;
		this.userName = userName;
		this.createdDate = createdDate;
	}

	public AnnotationThreadClient getFather() {
		return father;
	}

	public void setFather(AnnotationThreadClient father) {
		this.father = father;
	}

	public List<AnnotationThreadClient> getSubThreads() {
		return subThreads;
	}

	public void setSubThreads(List<AnnotationThreadClient> subThreads) {
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
	
	public AnnotationClient getAnnotation() {
		return annotation;
	}

	public void setAnnotation(AnnotationClient annotation) {
		this.annotation = annotation;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
