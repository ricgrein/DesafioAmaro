package com.amaro.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;
import com.amaro.enumeration.Tag;

public class Transform {

	/**
	 * Populate the tagsVector based on tags.
	 * If the value exists on the Enum Tag the position of the value is used to update the position on the vector with the value '1'.
	 * If the value doesn't exist, a Log is send to inform that a value different from the tags mapped exists.
	 * @param product
	 * @return
	 */
	public static Product populateTagVector(Product product) {

		for(String tag : product.getTags()) {
			try {
				product.getTagsVector()[Tag.valueOf(tag).ordinal()] = 1;
			} catch (IllegalArgumentException e) {
				// If something happens wrong, log, because it doesn't should happen.
				System.out.println(e.getMessage());
			}
		}

		return product;
	}

	public static Product getProduct(String productId) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<Long, Product> treeMap = new TreeMap<Long, Product>();

		ProductList productList = mapper.readValue(new File("/home/jgrein/db/Amaro/products.json"), ProductList.class);

		for (Product product : productList.getProducts()) {
			treeMap.put((long) product.getId(), product);
		}
		Product prodSelected = treeMap.get(new Long(productId));

		return prodSelected;
	}

	public static ProductList getAllProduct() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		ProductList productList = mapper.readValue(new File("/home/jgrein/db/Amaro/products.json"), ProductList.class);

		return productList;
	}

	/**
	 * Save the product list on a Json File on the directory '{$user.home}/db/Amaro/products.json'.
	 * 
	 * @param productList
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void writeJsonFile (ProductList productList) throws JsonGenerationException, JsonMappingException, IOException {

		String directory = System.getProperty("user.home") +"/db/Amaro/";
		new File(directory).mkdirs();

		//System.getProperty("user.home");
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		writer.writeValue(new File(directory +"/products.json"), productList);
	}
}
