package com.amaro.dto;

import java.util.Arrays;
import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product implements Comparable<Product>{
	private int id;
	private String name;
	private String[] tags;
	private int[] tagsVector;
	private float similarity;

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

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
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

	@Override
	public String toString() {
		return "[id=" + this.id + ", name=" + this.name + ", similarity: " + this.getSimilarity() + "]";
	}

	@Override
	public int compareTo(Product prod) {
		return (this.id - prod.id);
	}

	@Override
	public boolean equals(Object prod) {
		return compareTo((Product) prod) == 0;
	}

	/**
	 * Comparator to sort product list or array by ID.
	 */
	public static Comparator<Product> IdComparator = new Comparator<Product>() {

		@Override
		public int compare(Product e1, Product e2) {
			return (int) (e1.getId() - e2.getId());
		}
	};

	/**
	 * Comparator to sort product list or array by Similarity.
	 */
	public static Comparator<Product> SimilarityComparator = new Comparator<Product>() {

		@Override
		public int compare(Product e1, Product e2) {
			return (int) Float.compare(e1.getSimilarity(), e2.getSimilarity());
		}
	};

}