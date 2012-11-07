package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "annotation")
public class Annotation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private UserApp creator;
	@ManyToOne
	private ReadingActivity activity;
	@OneToMany(mappedBy = "annotation", cascade = CascadeType.ALL)
	private List<AnnotationThread> threads = new ArrayList<AnnotationThread>();
	@OneToMany(cascade = CascadeType.ALL)
	private List<TextSelector> textSelectors;
	private String comment;
	private Long bookId;

	private short visibility = 0;
	private short updatability = 0;
	private Integer pageNumber;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ann_file", joinColumns = { @JoinColumn(name = "annotationId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "fileId", referencedColumnName = "id") })
	private List<Tag> tags;

	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;

	@Transient
	private boolean isEditable = false;

	public Annotation() {
		this.textSelectors = new ArrayList<TextSelector>();
	}

	public Annotation(UserApp creator, ReadingActivity activity,
			List<TextSelector> textSelectors, String comment, Long bookId,
			short visibility, short updatability, Integer pageNumber,
			List<Tag> tags, boolean isEditable) {
		super();
		this.creator = creator;
		this.activity = activity;
		this.textSelectors = textSelectors;
		this.comment = comment;
		this.bookId = bookId;
		this.visibility = visibility;
		this.updatability = updatability;
		this.pageNumber = pageNumber;
		this.tags = tags;
		this.isEditable = isEditable;
	}

	public List<AnnotationThread> getThreads() {
		return threads;
	}

	public void setThreads(List<AnnotationThread> threads) {
		this.threads = threads;
	}

	public ReadingActivity getActivity() {
		return activity;
	}

	public void setActivity(ReadingActivity activity) {
		this.activity = activity;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<TextSelector> getTextSelectors() {
		return textSelectors;
	}

	public void setTextSelectors(List<TextSelector> textSelectors) {
		this.textSelectors = textSelectors;
	}

	public boolean isIsEditable() {
		return isEditable;
	}

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public int getVisibility() {
		return visibility;
	}

	public int getUpdatability() {
		return updatability;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public UserApp getCreator() {
		return creator;
	}

	public void setCreator(UserApp creator) {
		this.creator = creator;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setVisibility(short visibility) {
		this.visibility = visibility;
	}

	public void setUpdatability(short updatability) {
		this.updatability = updatability;
	}

}
