/**
 * 
 */
package com.amaro.util;

import java.io.IOException;
import java.util.Arrays;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;

/**
 * @author jgrein
 *
 */
public class Similarity {

	/**
	 * S = 1/(1 + D), where 
	 * D = is the distance between the vectors  v1 and v2.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static float calculateSimilarity(int[] v1, int [] v2) {
		try {
			float distance = calculateDistance(v1, v2);
			float similarity = 1/(1 + distance);

			return similarity;
		}catch (Exception e) {
			// TODO Log and return 0.
			System.out.println(e.getMessage());
			return new Float(0);
		}
	}

	/**
	 * Calculate the distance between the vectors  v1 and v2, 
	 * the distance is calculated with the formula:
	 * Distance = Math.sqrt((v1[0] - v2[0])^2 + (v1[1] - v2[1])^2 + .. + (v1[N-1] - v2[N-1])^2)
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	private static float calculateDistance(int[] v1, int[] v2) throws Exception {
		float subResult = 0;

		for (int i = 0; i < v1.length; i++) {
			subResult += (float) Math.pow(v1[i] - v2[i], 2);
		}

		double distance = Math.sqrt(subResult);

		return (float) distance;
	}

	public static Product[] getSimilarProducts(Product product) throws JsonParseException, JsonMappingException, IOException {
		ProductList productList = Transform.getAllProduct();

		Product[] products = new Product[productList.getProducts().size() - 1];
		int iterator = 0;
		for (Product prod : productList.getProducts()) {
			if (prod.getId() != product.getId()) {
				float similarity = calculateSimilarity(product.getTagsVector(), prod.getTagsVector());
				prod.setSimilarity(similarity);

				products[iterator] = prod;
				iterator++;
			} else {
				System.out.println("Mesmo Id:" + product.getId());
			}
		}

		Arrays.sort(products, Product.SimilarityComparator.reversed());

		return Arrays.copyOf(products,3);

	}

	/**
	 * Example values for test.
	 * "id": 8371, "tagsVector": [1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0]
	 * "id": 8367, "tagsVector": [0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0]
	 * "id": 8293, "tagsVector": [0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,1,1,0]
	 * "id": 8291, "tagsVector": [0,0,0,1,0,0,1,0,1,0,0,1,0,0,1,0,0,1,1,0]
	 * "id": 75162,"tagsVector": [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
