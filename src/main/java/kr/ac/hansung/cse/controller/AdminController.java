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

	// 지정을 안했으므로 admin이라는 url이 넘어오면 호출되서 admin이라는 view name을 return
	@RequestMapping
	public String adminPage() {
		return "admin";
	}

	// Product 리스트를 보여주는 메소드
	@RequestMapping("/productInventory")
	public String getProducts(Model model) { // DB를 조회해서 Products 리스트를 가져와서
												// Model에 넣어 view로 전달.

		List<Product> products = productService.getProducts();

		model.addAttribute("products", products); // model에 넘겨줌.
		return "productInventory";
	}

	/*
	 * Product를 추가하는 메소드
	 * 1. 해당 URL로 Request를 보냄. 2. 해당 메소드가 수행이 됨. 3. 메소드에서 Product 객체를 생성하여
	 * Model에 넣어 view로 넘겨줌.
	 */
	@RequestMapping("/productInventory/addProduct")
	public String addProduct(Model model) {
		Product product = new Product(); // 반드시 객체를 만들어서 넘겨줘야한다. Spring Form Tag
											// 사용 시 필요.

		product.setName("노트북");
		product.setCategory("컴퓨터"); // Category 부분을 컴퓨터 초기값으로 지정.

		model.addAttribute("product", product); // Product 객체를 생성하여 model의 넣어주는
												// 부분.

		return "addProduct";
	}

	/*
	 * 사용자가 입력한 form 데이터가 product에 Mapping되서 product 객체에 Data Vinding이 자동적으로
	 * 이루어짐. Validation을 통한 검증 BindingResult 객체 product와 result는 Model의 자동적으로
	 * 삽입되어 Dispatcher Servlet에 의하여 View로 전달됨 (그래서 사용자가 다시 처음부터 입력할 필요가 없음<기존
	 * 입력내용 보존>).
	 */
	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.POST) // Post이므로 명시해줌.
	public String addProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {

		// productService.addProduct(product); service객체를 통한 add 후 service객체는
		// dao를 통해 DB에 저장 후 return하여 jsp파일 오픈

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // errorList를 돌면서
																// error메시지를 출력
			}

			return "addProduct"; // error가 발생하면 다시 addProduct view로 이동.
		}

		// Image를 resource에 저장하기 위한 코드------------------------
		MultipartFile productImage = product.getProductImage(); // image첨부를 위한
																// 선언
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

		return "redirect:/admin/productInventory"; // redirect를 시켜서 호출하여 DB에 접근
													// 후 view에서 볼 수 있음.

		/*
		 * return "/admin/productInventory"; 이런식으로 넘기면
		 * productInventory.jsp(view)에서 새로운 data 보여지지 않음 즉, getProducts()를
		 * 호출해야함.
		 */
	}

	// 상품 삭제 호출
	@RequestMapping("/productInventory/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) { // id값이 위 url에 들어가게됨.

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

	// 상품 수정 호출
	@RequestMapping("/productInventory/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model model) {

		Product product = productService.getProductById(id); // id를 이용하여 db를 조회

		model.addAttribute("product", product); // 조회한 레코드를 product 객체에 삽입.

		return "editProduct";
	}

	// 상품 수정 호출
	@RequestMapping(value = "/productInventory/editProduct", method = RequestMethod.POST)
	public String editProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) { 
		// product가 dataVinding에 의해서 넘어옴

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // errorList를 돌면서
																// error메시지를 출력
			}

			return "editProduct"; // error가 발생하면 다시 addProduct view로 이동.
		}

		// Image를 resource에 저장하기 위한 코드------------------------
		MultipartFile productImage = product.getProductImage(); // image첨부를 위한
																// 선언
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
