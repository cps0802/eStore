package kr.ac.hansung.cse.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Lombok이용 자동 생성
@Getter
@Setter
@ToString
@Entity //Table로 맵핑 
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) //id값이 자동적으로 생성
	@Column(name="product_id") //column name 지정
	private int id;
	
	@NotEmpty(message="The product name must not be null") // Validation
	private String name;
	
	private String category;
	
	@Min(value=0, message="The product price must not be less than zero")
	private int price;
	
	@NotEmpty(message="The product manufacturer must not be null")
	private String manufacturer;
	
	@Min(value=0, message="The product unitInStock price must not be less than zero")
	private int unitInStock;
	
	private String description;
	
	@Transient
	private MultipartFile productImage;
	
	private String imageFilename;
}
