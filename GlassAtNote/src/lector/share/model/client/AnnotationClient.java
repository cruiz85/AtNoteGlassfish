package lector.share.model.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnotationClient {

	private Long id;
	private UserClient creator;
	private Long activity;
	private List<Long> threads = new ArrayList<Long>();
	private List<TextSelectorClient> textSelectors;
	private String comment;
	private Long bookId;

	private boolean visibility = false;
	private boolean updatability = false;
	private Integer pageNumber;

	private List<TypeClient> tags;

	private Date createdDate;

	private boolean isEditable = false;

	public AnnotationClient() {
		this.textSelectors = new ArrayList<TextSelectorClient>();
	}

	public AnnotationClient(UserClient creator, Long activity,
			List<TextSelectorClient> textSelectors, String comment,
			Long bookId, boolean visibility, boolean updatability,
			Integer pageNumber, List<TypeClient> tags, boolean isEditable) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserClient getCreator() {
		return creator;
	}

	public void setCreator(UserClient creator) {
		this.creator = creator;
	}

	public Long getActivity() {
		return activity;
	}

	public void setActivity(Long activity) {
		this.activity = activity;
	}

	public List<Long> getThreads() {
		return threads;
	}

	public void setThreads(List<Long> threads) {
		this.threads = threads;
	}

	public List<TextSelectorClient> getTextSelectors() {
		return textSelectors;
	}

	public void setTextSelectors(List<TextSelectorClient> textSelectors) {
		this.textSelectors = textSelectors;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public boolean isUpdatability() {
		return updatability;
	}

	public void setUpdatability(boolean updatability) {
		this.updatability = updatability;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<TypeClient> getTags() {
		return tags;
	}

	public void setTags(List<TypeClient> tags) {
		this.tags = tags;
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

}
