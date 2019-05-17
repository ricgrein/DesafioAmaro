package com.amaro.transformation;

import com.amaro.dto.Product;
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
}
