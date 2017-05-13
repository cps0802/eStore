package kr.ac.hansung.cse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Product;

@Repository // dao-context.xml에 의하여 bean으로 등록
@Transactional // 메소드 전부 트렌젝션 적용이 됨.
public class ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	// 상품 수정(id를 바탕으로 db에 접근하여 레코드를 조회)
	public Product getProductById(int id) {
//		Session session = sessionFactory.getCurrentSession();
//		Product product = session.get(Product.class, id);
		Product product = sessionFactory.getCurrentSession().get(Product.class, id);
		
		return product;
	}

	// DB에 접근하여 모든 레코드를 조회하여 결과를 넘겨주는 메소드
	@SuppressWarnings("unchecked")
	public List<Product> getProducts() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Product"); //클래스이름을 넣어주어야함.(Table이름이아님!!)
		List<Product> productList = query.list();
		
		return productList;
	}

	// 상품 추가
	public void addProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		session.flush();
	}

	// 상품 제거
	public void deleteProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(product);
		session.flush();
	}

	public void editProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		session.flush();
	}
}
