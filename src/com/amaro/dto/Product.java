package com.amaro.dto;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
	private int id;
	private String name;
	private String[] tags;
	private int[] tagsVector;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public int[] getTagsVector() {
		return tagsVector;
	}

	public void setTagsVector(int[] tagsVector) {
		this.tagsVector = tagsVector;
	}

	public Product(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public void initTagsVector() {
		this.tagsVector = new int[20];
		Arrays.fill(this.getTagsVector(),new Integer(0));
	}

	public Product() {
		super();
	}
}