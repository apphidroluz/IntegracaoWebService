package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtil;
import entity.Facturation;
import entity.Importacao;

public class ImportacaoDao {

	Session session;
	Transaction transaction;
	Query query;
	Criteria criteria;

	public void gravar(Importacao imp) throws Exception {

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(imp);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			HibernateUtil.fechar_conexao(session);

		}

	}

	public List<Importacao> findImp(Integer cod_cli) throws HibernateException, SQLException {
		List<Importacao> lista = null;

		try {

			session = HibernateUtil.getSessionFactory().openSession();
			query = session.createQuery("from Importacao as i where i.cliente.codigo = " + cod_cli + "order by i.data_imp desc" );
			lista = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateUtil.fechar_conexao(session);
		}

		return lista;
	}

	public void delete(Integer id) throws Exception{
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			query = session.createQuery(
					"delete from Facturation as f where f.importacao.id_IMP=" + id);
			System.out.println(query);
			query.executeUpdate();
			transaction.commit();
			
			transaction = session.beginTransaction();
			query = session.createQuery(
					"delete from Importacao where id_IMP=" + id);
			System.out.println(query);
			query.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		ImportacaoDao impo = new ImportacaoDao();

		try {
			impo.delete(65);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
