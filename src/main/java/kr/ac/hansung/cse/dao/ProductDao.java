package kr.ac.hansung.cse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Product;

@Repository // dao-context.xml�� ���Ͽ� bean���� ���
@Transactional // �޼ҵ� ���� Ʈ������ ������ ��.
public class ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	// ��ǰ ����(id�� �������� db�� �����Ͽ� ���ڵ带 ��ȸ)
	public Product getProductById(int id) {
//		Session session = sessionFactory.getCurrentSession();
//		Product product = session.get(Product.class, id);
		Product product = sessionFactory.getCurrentSession().get(Product.class, id);
		
		return product;
	}

	// DB�� �����Ͽ� ��� ���ڵ带 ��ȸ�Ͽ� ����� �Ѱ��ִ� �޼ҵ�
	@SuppressWarnings("unchecked")
	public List<Product> getProducts() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Product"); //Ŭ�����̸��� �־��־����.(Table�̸��̾ƴ�!!)
		List<Product> productList = query.list();
		
		return productList;
	}

	// ��ǰ �߰�
	public void addProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		session.flush();
	}

	// ��ǰ ����
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
