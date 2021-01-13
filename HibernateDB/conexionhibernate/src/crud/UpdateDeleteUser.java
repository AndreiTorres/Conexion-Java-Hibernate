package crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UpdateDeleteUser {
    
    public static void main(String[] args) {
        
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).configure().buildSessionFactory();
        Session mySession = sessionFactory.openSession();

        try{

            int idUser = 1;    
            mySession.beginTransaction();
            User user = mySession.get(User.class, idUser);

            mySession.delete(user);
            
        
            mySession.getTransaction().commit();
            System.out.println("Registro actualizado");
            mySession.close();
        }finally {
            sessionFactory.close();
        }
    }
}
