package com.amaro.controller;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;
import com.amaro.util.Similarity;
import com.amaro.util.Transform;

@Path("/service")
public class ProductService {

	@POST
	@Path("/updateProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProducts(String doc) {
		//System.out.println(doc);

		try {
			ObjectMapper mapper = new ObjectMapper();

			ProductList productList = mapper.readValue(doc, ProductList.class);

			for (Product product : productList.getProducts()) {
				product.initTagsVector();
				Transform.populateTagVector(product);
			}

			Transform.writeJsonFile(productList);

			String result = mapper.writeValueAsString(productList.getProducts());

			return result;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Erro";
	}

	@GET
	@Path("/similarproducts/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] getSimilars(@PathParam("productId") String productId) {
		try {
			System.out.println("productId: " + productId);

			// Get the tagsVector from the data base.
			Product product = Transform.getProduct(productId);

			Product[] similarList = Similarity.getSimilarProducts(product);

			return similarList;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return null;
	}


	// hashthree
	// para add os produtos "indexados" pelo id para evitar repetição.
}
