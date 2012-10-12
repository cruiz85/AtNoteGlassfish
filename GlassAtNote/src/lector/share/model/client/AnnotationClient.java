package lector.share.model.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AnnotationClient {

	private Long id;
	private UserClient creator;
	private Long activity;
	private ArrayList<Long> threads = new ArrayList<Long>();
	private ArrayList<TextSelectorClient> textSelectors;
	private String comment;
	private Long bookId;

	private boolean visibility = false;
	private boolean updatability = false;
	private Integer pageNumber;

	private ArrayList<TypeClient> tags;

	private Date createdDate;

	private boolean isEditable = false;

	public AnnotationClient() {
		this.textSelectors = new ArrayList<TextSelectorClient>();
	}

	public AnnotationClient(UserClient creator, Long activity,
			ArrayList<TextSelectorClient> textSelectors, String comment, Long bookId,
			boolean visibility, boolean updatability, Integer pageNumber,
			ArrayList<TypeClient> tags, boolean isEditable) {
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

	public List<Long> getThreads() {
		return threads;
	}

	public void setThreads(ArrayList<Long> threads) {
		this.threads = threads;
	}

	public Long getActivity() {
		return activity;
	}

	public void setActivity(Long activity) {
		this.activity = activity;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public ArrayList<TextSelectorClient> getTextSelectors() {
		return textSelectors;
	}

	public void setTextSelectors(ArrayList<TextSelectorClient> textSelectors) {
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

	public boolean getVisibility() {
		return visibility;
	}

	public boolean getUpdatability() {
		return updatability;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<TypeClient> getTags() {
		return tags;
	}

	public void setTags(ArrayList<TypeClient> tags) {
		this.tags = tags;
	}

	public UserClient getCreator() {
		return creator;
	}

	public void setCreator(UserClient creator) {
		this.creator = creator;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public void setUpdatability(boolean updatability) {
		this.updatability = updatability;
	}

}
