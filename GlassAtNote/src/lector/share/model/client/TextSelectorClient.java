package lector.share.model.client;

import com.google.gwt.user.client.rpc.IsSerializable;


public class TextSelectorClient implements IsSerializable{

	private Long id;
	private Long x;
	private Long y;
	private Long width;
	private Long height;

	public TextSelectorClient() {
	}

	public TextSelectorClient(Long x, Long y, Long width, Long height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getX() {
		return x;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getY() {
		return y;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public String getTextCoordinates() {
		return ("(" + x + "," + y + ")");

	}
}
