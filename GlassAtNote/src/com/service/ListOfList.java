package com.service;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListOfList implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private List<ListOfSchema> listOfList;

	public ListOfList() {
		this.listOfList = new ArrayList<ListOfSchema>();
	}

	public ListOfList(List<ListOfSchema> listOfList) {
		this();
		this.listOfList = listOfList;
	}

	@XmlElement(name = "list_of_schemas")
	public List<ListOfSchema> getListOfList() {
		return listOfList;
	}

	public void setListOfList(List<ListOfSchema> listOfList) {
		this.listOfList = listOfList;
	}

}
