package persistence;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import config.HibernateUtil;
import entity.Clientes_Concentrador;

public class Clientes_ConcentradorDao {

	Session session;
	Transaction transaction;
	Query query;
	Criteria criteria;

	public Clientes_Concentrador logar(String login, String senha) throws Exception {

		Clientes_Concentrador cli = null;

		try {

			session = HibernateUtil.getSessionFactory().openSession();
			criteria = session.createCriteria(Clientes_Concentrador.class);
			criteria.add(Restrictions.eq("login", login));
			criteria.add(Restrictions.eq("senha", senha));
			
			///OI////

			cli = (Clientes_Concentrador) criteria.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.fechar_conexao(session);
		}

		return cli;
	}
	
	
	public static void main(String[] args) {
		try {
			
			
			Clientes_Concentrador cli = new Clientes_ConcentradorDao().logar("hidroluz", "h1dr0l@mzr-srvc");
			
			System.out.println(cli);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
