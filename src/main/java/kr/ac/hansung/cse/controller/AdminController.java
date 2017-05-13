package kr.ac.hansung.cse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin") // URL Mapping
public class AdminController {

	@Autowired
	private ProductService productService;

	// ������ �������Ƿ� admin�̶�� url�� �Ѿ���� ȣ��Ǽ� admin�̶�� view name�� return
	@RequestMapping
	public String adminPage() {
		return "admin";
	}

	// Product ����Ʈ�� �����ִ� �޼ҵ�
	@RequestMapping("/productInventory")
	public String getProducts(Model model) { // DB�� ��ȸ�ؼ� Products ����Ʈ�� �����ͼ�
												// Model�� �־� view�� ����.

		List<Product> products = productService.getProducts();

		model.addAttribute("products", products); // model�� �Ѱ���.
		return "productInventory";
	}

	/*
	 * Product�� �߰��ϴ� �޼ҵ�
	 * 1. �ش� URL�� Request�� ����. 2. �ش� �޼ҵ尡 ������ ��. 3. �޼ҵ忡�� Product ��ü�� �����Ͽ�
	 * Model�� �־� view�� �Ѱ���.
	 */
	@RequestMapping("/productInventory/addProduct")
	public String addProduct(Model model) {
		Product product = new Product(); // �ݵ�� ��ü�� ���� �Ѱ�����Ѵ�. Spring Form Tag
											// ��� �� �ʿ�.

		product.setName("��Ʈ��");
		product.setCategory("��ǻ��"); // Category �κ��� ��ǻ�� �ʱⰪ���� ����.

		model.addAttribute("product", product); // Product ��ü�� �����Ͽ� model�� �־��ִ�
												// �κ�.

		return "addProduct";
	}

	/*
	 * ����ڰ� �Է��� form �����Ͱ� product�� Mapping�Ǽ� product ��ü�� Data Vinding�� �ڵ�������
	 * �̷����. Validation�� ���� ���� BindingResult ��ü product�� result�� Model�� �ڵ�������
	 * ���ԵǾ� Dispatcher Servlet�� ���Ͽ� View�� ���޵� (�׷��� ����ڰ� �ٽ� ó������ �Է��� �ʿ䰡 ����<����
	 * �Է³��� ����>).
	 */
	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.POST) // Post�̹Ƿ� �������.
	public String addProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {

		// productService.addProduct(product); service��ü�� ���� add �� service��ü��
		// dao�� ���� DB�� ���� �� return�Ͽ� jsp���� ����

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // errorList�� ���鼭
																// error�޽����� ���
			}

			return "addProduct"; // error�� �߻��ϸ� �ٽ� addProduct view�� �̵�.
		}

		// Image�� resource�� �����ϱ� ���� �ڵ�------------------------
		MultipartFile productImage = product.getProductImage(); // image÷�θ� ����
																// ����
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename());

		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		product.setImageFilename(productImage.getOriginalFilename());

		// ------------------------------------------------------------------------

		productService.addProduct(product);

		return "redirect:/admin/productInventory"; // redirect�� ���Ѽ� ȣ���Ͽ� DB�� ����
													// �� view���� �� �� ����.

		/*
		 * return "/admin/productInventory"; �̷������� �ѱ��
		 * productInventory.jsp(view)���� ���ο� data �������� ���� ��, getProducts()��
		 * ȣ���ؾ���.
		 */
	}

	// ��ǰ ���� ȣ��
	@RequestMapping("/productInventory/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) { // id���� �� url�� ���Ե�.

		Product product = productService.getProductById(id);
		
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path path = Paths.get(rootDirectory + "\\resources\\images\\" + product.getImageFilename());
		
		
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		productService.deleteProduct(product);

		return "redirect:/admin/productInventory";
	}

	// ��ǰ ���� ȣ��
	@RequestMapping("/productInventory/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model model) {

		Product product = productService.getProductById(id); // id�� �̿��Ͽ� db�� ��ȸ

		model.addAttribute("product", product); // ��ȸ�� ���ڵ带 product ��ü�� ����.

		return "editProduct";
	}

	// ��ǰ ���� ȣ��
	@RequestMapping(value = "/productInventory/editProduct", method = RequestMethod.POST)
	public String editProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) { 
		// product�� dataVinding�� ���ؼ� �Ѿ��

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // errorList�� ���鼭
																// error�޽����� ���
			}

			return "editProduct"; // error�� �߻��ϸ� �ٽ� addProduct view�� �̵�.
		}

		// Image�� resource�� �����ϱ� ���� �ڵ�------------------------
		MultipartFile productImage = product.getProductImage(); // image÷�θ� ����
																// ����
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename());

		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		product.setImageFilename(productImage.getOriginalFilename());

		productService.editProduct(product);

		System.out.println(product);

		return "redirect:/admin/productInventory";
	}
}
