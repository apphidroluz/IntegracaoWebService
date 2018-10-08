package persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtil;
import entity.Hidrometro;

public class HidrometroDao {

	Session session;
	Transaction transaction;
	Query query;
	Criteria criteria;

	public List<Hidrometro> findHidro(String Hidro) throws HibernateException, SQLException {
		List<Hidrometro> lista = null;

		try {

			session = HibernateUtil.getSessionFactory().openSession();
			query = session.createQuery("from Hidrometro as h where h.num_hidro= '" + Hidro + "'");
			lista = query.list();
			query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			HibernateUtil.fechar_conexao(session);
		}

		return lista;
	}
	
	
	public static void main(String[] args) {
		HidrometroDao hd = new HidrometroDao();
		try {
			List<Hidrometro> teste = hd.findHidro("123456789123465");
			
			System.out.println(teste);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
