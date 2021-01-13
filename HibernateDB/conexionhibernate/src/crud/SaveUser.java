package crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SaveUser {
    
    public static void main(String[] args) {
        
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).configure().buildSessionFactory();
        Session mySession = sessionFactory.openSession();

        try{

            User user = new User("Jose", "Landero" , "Montejo");
            mySession.beginTransaction();
            mySession.save(user);

            mySession.getTransaction().commit();
            System.out.println("Registro guardado");
            mySession.close();
        }finally {
            sessionFactory.close();
        }
    }
}
