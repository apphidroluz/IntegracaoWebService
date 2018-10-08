package persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtil;
import entity.Facturation;
import entity.Importacao;

public class FacturationDao {

	Session session;
	Transaction transaction;
	Query query;
	Criteria criteria;

	public void gravar(List<Facturation> fac, Importacao imp) throws Exception {

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			for (Facturation f : fac) {
				session.save(f);
				imp.adicionar(f);
				session.save(imp);

			}

			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			HibernateUtil.fechar_conexao(session);

		}

	}

	public List<Facturation> findFact(String Hidro) throws HibernateException, SQLException {
		List<Facturation> lista = null;

		try {

			session = HibernateUtil.getSessionFactory().openSession();
			query = session
					.createQuery("from Facturation as f where f.num_medidor= '" + Hidro + "' Order by f.id_FT desc");
			// query.uniqueResult();
			lista = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateUtil.fechar_conexao(session);
		}

		return lista;

	}

	public List<Facturation> findFac(String facturation) throws HibernateException, SQLException {
		List<Facturation> lista = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			lista = session.createQuery("from Facturation as f where f.id_FT= " + facturation).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			HibernateUtil.fechar_conexao(session);

		}
		return lista;

	}

	public static void main(String[] args) {

		FacturationDao f = new FacturationDao();

		List<Facturation> l;
		try {
			l = f.findFact("D17B705291");

			System.out.println(l);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
