package crud;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ReadUser {
    
    public static void main(String[] args) {
        
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).configure().buildSessionFactory();
        Session mySession = sessionFactory.openSession();

        try{

            
            mySession.beginTransaction();
            List<User> allUsers = mySession.createQuery("from User x where x.nombre='Andrei'").getResultList();
            
            allUsers.stream().forEach(user -> System.out.println(user));

            mySession.getTransaction().commit();
            System.out.println("Registro guardado");
            mySession.close();
        }finally {
            sessionFactory.close();
        }
    }
}
