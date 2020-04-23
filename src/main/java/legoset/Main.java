package legoset;

import java.time.Year;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import legoset.model.LegoSetQLBVZH;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {

    private static Logger logger = LogManager.getLogger();

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("legoset-mysql");

    private static void createLegoSets() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(new LegoSetQLBVZH("60073", "Service Truck", Year.of(2015), 233));
            em.persist(new LegoSetQLBVZH("75211", "Imperial TIE Fighter", Year.of(2018), 519));
            em.persist(new LegoSetQLBVZH("21034", "London", Year.of(2017), 468));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static List<LegoSetQLBVZH> getLegoSets() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery("SELECT l FROM LegoSetQLBVZH l ORDER BY l.number", LegoSetQLBVZH.class).getResultList();
        } finally {
            em.close();
        }
    }

    private static Long getTotalPieces() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery("SELECT SUM(pieces) FROM LegoSetQLBVZH l", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    private static void deleteLegoSets() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            long count = em.createQuery("DELETE FROM LegoSetQLBVZH").executeUpdate();
            logger.info("Deleted {} Lego set(s)", count);
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        createLegoSets();
        getLegoSets().forEach(logger::info);
        logger.info("Total pieces: {}", getTotalPieces());
        deleteLegoSets();
        emf.close();
    }

}
