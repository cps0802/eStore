package kr.ac.hansung.cse.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue
	private int userId;
	
	@NotEmpty(message="The username must not be null")
	private String username;
	
	@NotEmpty(message="The username must not be null")
	private String password;
	
	@NotEmpty(message="The username must not be null")
	private String email;
	
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
	private ShippingAddress shippingAddress;
	
	private boolean enabled = false;
	
	private String authority; //mydoctor에서는 구분 지어서 권한을.
	
}
