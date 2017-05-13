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

//Lombok�̿� �ڵ� ����
@Getter
@Setter
@ToString
@Entity //Table�� ���� 
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) //id���� �ڵ������� ����
	@Column(name="product_id") //column name ����
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
