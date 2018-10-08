package config;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/*
 *Classe responsável pela conexão com o banco de dados. 
 */
public class HibernateUtil {
	
	
    private static final SessionFactory sessionFactory;

    static {
        try {
            
            sessionFactory = new AnnotationConfiguration().configure("config/persistence.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void fechar_conexao(Session session){
    	
    	if (session != null && session.isOpen()){
    		try {
				
    		System.out.println("Passsou");
    		sessionFactory.close();
    		session.clear();
    		session.close();
    		
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}

		System.out.println("Não Passsou");
    }
    
    public static void main(String[] args) {
   	 Configuration cfg = new
   	 AnnotationConfiguration().configure("config/persistence.cfg.xml");
   	
   	 new SchemaExport(cfg).create(true, true);
   	 }
    
}