package com.amaro.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;
import com.amaro.transformation.Transform;

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
			
			String result = mapper.writeValueAsString(productList);

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
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsJSON() {
		List<Product> listProduct = new ArrayList<>();

		Product p1 = new Product(1,"prod 1");
		Product p2 = new Product(2, "prod 2");

		listProduct.add(p1);
		listProduct.add(p2);
		return listProduct;
	}

	@POST
	@Path("/testPost")
	@Produces(MediaType.APPLICATION_JSON)
	public String testPost(String x) {
		System.out.println(x);

		return "updateProducts";
	}
}
