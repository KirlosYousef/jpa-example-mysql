package user;

import com.github.javafaker.Faker;
import user.model.UserQLBVZH;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    private static Faker faker = new Faker();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("legoset-mysql");
        EntityManager em = emf.createEntityManager();

        UserQLBVZH userQLBVZH = UserQLBVZH.builder().username("test").password("123123").build();
        em.getTransaction().begin();
        em.persist(userQLBVZH);
        em.getTransaction().commit();
        System.out.println(userQLBVZH);

        em.getTransaction().begin();
        userQLBVZH.setPassword("testPass");
        em.getTransaction().commit();

        System.out.println(em.find(UserQLBVZH.class, "test"));
        System.out.println(userQLBVZH == em.find(UserQLBVZH.class, "test"));

        em.clear();

        System.out.println(em.find(UserQLBVZH.class, "test"));
        System.out.println(userQLBVZH == em.find(UserQLBVZH.class, "test"));

        em.getTransaction().begin();
        UserQLBVZH managedUser = em.merge(userQLBVZH);
        managedUser.setPassword("password");
        em.getTransaction().commit();
        System.out.println(em.find(UserQLBVZH.class, "test"));

        em.close();
        emf.close();
    }

}
