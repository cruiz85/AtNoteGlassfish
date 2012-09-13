package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
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
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "annotation")
public class Annotation implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bookId;
	private Long userId; // no debería ser necesario guardar el objeto, existe
							// una transacción que juegue con las dos
							// referencias

	private String userName;
	private int visibility = 0;
	private int updatability = 0;
	private Integer pageNumber;

	@OneToMany(cascade = CascadeType.ALL)
	private ArrayList<TextSelector> textSelectors;

	private String comment;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ann_file", joinColumns = { @JoinColumn(name = "annotationId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "fileId", referencedColumnName = "id") })
	private List<FileDB> files;
	@Transient
	private boolean isPersisted;
	private Long readingActivity; // NO ES NECESARIO TENER EL OBJETO
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;

	@Transient
	private boolean isEditable = false;

	public Annotation() {
		isPersisted = false;

		this.textSelectors = new ArrayList<TextSelector>();
	}

	public Annotation(String bookId, Integer pageNumber,
			ArrayList<TextSelector> textSelectors, String comment) {
		this();
		this.bookId = bookId;
		this.pageNumber = pageNumber;
		this.textSelectors = textSelectors;
		this.comment = comment;
	}

	public Annotation(String bookId, Integer pageNumber,
			ArrayList<TextSelector> textSelectors, String comment,
			ArrayList<FileDB> files) {
		this.bookId = bookId;
		this.pageNumber = pageNumber;
		this.textSelectors = textSelectors;
		this.comment = comment;
		this.files = files;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public ArrayList<TextSelector> getTextSelectors() {
		return textSelectors;
	}

	public void setTextSelectors(ArrayList<TextSelector> textSelectors) {
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

	public void setIsPersisted(boolean isPersisted) {
		this.isPersisted = isPersisted;
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

	public boolean isPersisted() {
		return isPersisted;
	}

	public void setPersisted(boolean isPersisted) {
		this.isPersisted = isPersisted;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getUpdatability() {
		return updatability;
	}

	public void setUpdatability(int updatability) {
		this.updatability = updatability;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<FileDB> getFiles() {
		return files;
	}

	public void setFiles(List<FileDB> files) {
		this.files = files;
	}

	public Long getReadingActivity() {
		return readingActivity;
	}

	public void setReadingActivity(Long readingActivity) {
		this.readingActivity = readingActivity;
	}

}
