# Hibernate

Hibernate es una herramienta de mapeo objeto-relacional (ORM) que facilita
el mapeo de atributos en una base de datos tradicional, y el modelo de objetos de
una aplicación mediante archivos declarativos *pom.xml* o anotaciones en los beans de las entidades
que permiten establecer relaciones.

### ¿Porque usar Hibernate?

Hibernate busca solucionar el problema de la diferencia entre los 2 modelos de datos
en una aplicación, el orientado a objetos y el usado en las bases de datos, *modelo relacional*.
Para lograr esto, **HIbernate** permite al desarrollador detallar como es su modelo de datos,
que relaciones existen y que forma tienen. Con esta información Hibernate le permite a la aplicación
manipular los datos que hay en la base de datos operando sobre objetos, con todas las caracteristicas
de la Programación Orientada a Objetos.

Por lo tanto, Hibernate nos ayuda a no tener que usar el lenguaje SQL y hacer todas las consultas utilizando
métodos.

# Conexion-Java-Hibernate

Descargar la carpeta donde se encuentran la libreria de hibernate necesaria, 
ahi tambien se encuentran los conectores para mysql y postgresql.

Tener xampp, y phpmyadmin para poder acceder a la base de datos. La practica se hara en mySQL.
 Link de descarga xampp https://www.apachefriends.org/es/index.html
 


### Link del video de la presentacion
https://www.youtube.com/watch?v=zBibPLjiP1M

## Ejemplo básico: CRUD en Java utilizando Hibernate

Una vez que tenemos el entorno configurado correctamente, *librerias y drivers necesarios*, empezamos a crear el CRUD. En este caso haremos un CRUD  de usuarios con nombre, apellidos y direccion.

Primero creamos la clase *DBConnection.java*, en esta clase haremos
la conexión a nuestra base.

~~~
import java.sql.DriverManager;
import java.sql.Connection;


public class DBConnection {
    
    public static void main(String[] args) {
        
        String url = "jdbc:mysql://localhost:3306/NOMBREDELABASE";
        String user = "NOMBREDEUSUARIO";
        String password = "PASSWORD";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

~~~

El siguiente paso es configurar nuestro archivo *pom.xml*, pasandole el url a la base de datos, como en la clase DBConnection, el nombre del usuario y la contraseña, en el caso de usar postgresql se tiene que cambiar el dialecto. Este archivo se puede conseguir muy facilmente en la web, buscando *hibernate config xml*.

~~~
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE hibernate-configuration SYSTEM "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>


<session-factory>

<property name="hibernate.dialect"> org.hibernate.dialect.MySQLDialect </property>

<property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>

<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/NOMBREDELABASE</property>

<property name="hibernate.connection.username">NOMBREDEUSUARIO</property>

<property name="hibernate.connection.password">PASSWORD</property>

</session-factory>

</hibernate-configuration>
~~~

Ya que tenemos el archivo configurado correctamente, lo que sigue es representar nuestra tabla en una clase.

**@Entity** con esta anotación le estamos diciendo a JPA que esta clase es una Entidad y deberá ser administrada por EntityManager.

**@Table** con esta anotación le decimos que tabla de la base de datos estamos representando.

**@Id** con esta anotación le decimos a JPA que atributo será nuestra llave primaria, esta sera administrada automaticamente por el gestor de la base de datos.

Es necesario definir el constructor por default, en el caso de que querramos consultar todos los registros de la base. 

~~~
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class User {
    
    @Id
    @Column(name="id")
    private int id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellidos")
    private String apellidos;

    @Column(name="direccion")
    private String direccion;

    public User() {

    }
    
    public User(String nombre, String apellidos, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "User [apellidos=" + apellidos + ", direccion=" + direccion + ", id=" + id + ", nombre=" + nombre + "]";
    }
}

~~~

Ya que tenemos nuestra tabla y sus columnas representadas en una clase con sus atributos, creamos una clase que nos servira para activar Hibernate y poder manipular nuestros datos.

Para todos los métodos que nos brinda hibernate para guardar, eliminar, actualizar y consultar nuestros datos necesitamos esta estructura.

**SessionFactory** es un objeto de configuración, se utiliza para crear un objeto para nuestro programa que utiliza el archivo de configuración suministrado, y permite que objeto de tipo **Session** sea ejecutado. Basicamente arranca o activa Hibernate.

**Session** Se utiliza para obtener una conexión fisica con la base de datos. Esta clase nos proporciona los métodos necesarios para la manipulación de los registros con la base.

~~~
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CRUD {
    
    public static void main(String[] args) {
        
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).configure().buildSessionFactory();
        Session mySession = sessionFactory.openSession();

        try{
            
            //Aqui van las instrucciones para guardar, eliminar, actualizar o consultar los datos
            
        }finally {
            sessionFactory.close();
        }
    }
}
~~~

### Guardar

Usaremos el método **save(Object object)** de la sesión pasándole como argumento el objeto a guardar.
~~~
User user = new User("Andrei", "Torres", "Fco de Montejo");  //Creamos el objeto
 
Session session = sessionFactory.openSession();
session.beginTransaction();
 
session.save(user); //<|--- Aqui guardamos el objeto en la base de datos.
 
session.getTransaction().commit();
session.close();
~~~

### Actualizar

El método a utilizar es **update(Object object)**, al que le debemos pasar el objeto a actualizar en la base de datos.
~~~
int idUser = 1;    
mySession.beginTransaction();
User user = mySession.get(User.class, idUser);

user.setDireccion("Las Americas");

mySesssion.update(user);
session.getTransaction().commit();
session.close();

~~~

### Borrar

Ahora pasemos a borrar un objeto desde la base de datos.El método que debemos usar es **delete(Object object)**, al que le deberemos pasar el objeto a borrar de la base de datos
~~~

int idUser = 1;    
mySession.beginTransaction();
User user = mySession.get(User.class, idUser);

mySession.delete(user);

mySesssion.update(user);
session.getTransaction().commit();
session.close();
~~~

### Leer
~~~
mySession.beginTransaction();
List<User> allUsers = mySession.createQuery("from User).getResultList();
            
allUsers.stream().forEach(user -> System.out.println(user));

mySession.getTransaction().commit();
mySession.close();
~~~

También se pueden consultar datos específicos usando el lenguaje de consulta de Hibernate **HQL**.

Pueden consultar el lenguaje en el siguiente enlace https://docs.jboss.org/hibernate/orm/3.5/reference/es-ES/html/queryhql.html

#### Referencias

- https://ifgeekthen.everis.com/es/que-es-java-hibernate-por-que-usarlo
- http://www.cursohibernate.es/doku.php?id=unidades:02_hibernate:04_usando_hibernate

