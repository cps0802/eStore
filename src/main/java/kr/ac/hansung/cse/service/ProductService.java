package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.ProductDao;
import kr.ac.hansung.cse.model.Product;

@Service
public class ProductService {

	@Autowired // Controller > Service > Dao 과정 Flow
	private ProductDao productDao; // 만들어진 productDao bean을 주입

	// Dao의 getProducts()를 호출해서 List로 받아와 return 해줌
	public List<Product> getProducts() {
		return productDao.getProducts();
	}

	public void addProduct(Product product) {
		productDao.addProduct(product);
	}

	public void deleteProduct(Product product) {
		productDao.deleteProduct(product);
	}

	public Product getProductById(int id) {
		return productDao.getProductById(id);
	}

	public void editProduct(Product product) {
		productDao.editProduct(product);
	}
}
