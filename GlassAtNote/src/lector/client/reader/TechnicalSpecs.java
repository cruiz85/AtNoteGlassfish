package lector.client.reader;

import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.share.model.Language;
import lector.share.model.client.BookClient;
import lector.share.model.client.GoogleBookClient;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;

public class TechnicalSpecs extends Composite {

    private RichTextArea richTextArea = new RichTextArea();
    private VerticalPanel verticalPanel = new VerticalPanel();
    private BookClient book;

    public TechnicalSpecs(BookClient book) {
        this.book = book;
        SimplePanel decoratorPanel = new SimplePanel();
        initWidget(decoratorPanel);
        decoratorPanel.setWidget(verticalPanel);
        richTextArea.setHeight("300px");
        verticalPanel.add(richTextArea);
        richTextArea.setEnabled(false);
        richTextArea.setVisible(true);
        if (ActualUser.getLanguage()!=null)setHTMLInRichTextArea();
    }

    public void setHTMLInRichTextArea() {
    	Language Lang=ActualUser.getLanguage();
    	String pathImage;
    	if (book instanceof GoogleBookClient)
    		pathImage=((GoogleBookClient)book).getTbURL();
    	else pathImage=Constants.LOCAL_BOOK_PATH;
        richTextArea.setHTML("<table>"
                + "<tr><td><strong>" + Lang.getIDSpf() +": </strong></td><td>" + this.book.getId() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getAuthors() +": </strong></td><td>" + this.book.getAuthor() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getPages() +": </strong></td><td>" + this.book.getPagesCount() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getPublication_Year() +": </strong></td><td>" + this.book.getPublishedYear() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getTitle() +": </strong></td><td>" + this.book.getTitle() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getFront_Cover() +": </strong></td><td><img src=\"" + pathImage + "\"></img></td></tr>"
                + "</table>");
    }

    public void clear() {
        richTextArea.setHTML("");

    }

    public BookClient getBook() {
        return book;

    }

    public void setBook(BookClient book) {
        this.book = book;
        setHTMLInRichTextArea();
    }
}
