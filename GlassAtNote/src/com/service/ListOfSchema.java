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

import lector.client.service.AnnotationSchema;

@XmlRootElement
public class ListOfSchema implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private List<AnnotationSchema> annotationSchemas;

	public ListOfSchema() {
		this.annotationSchemas = new ArrayList<AnnotationSchema>();
	}

	
	public ListOfSchema(List<AnnotationSchema> annotationSchemas) {
		this();
		this.annotationSchemas = annotationSchemas;
	}


	@XmlElement(name = "annotation_schema")
	public List<AnnotationSchema> getAnnotationSchemas() {
		return annotationSchemas;
	}



	public void setAnnotationSchemas(List<AnnotationSchema> annotationSchemas) {
		this.annotationSchemas = annotationSchemas;
	}





}
