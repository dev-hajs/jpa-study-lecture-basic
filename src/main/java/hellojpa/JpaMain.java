package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 영속
            Member findMember = em.find(Member.class, 150L);
            findMember.setName("AAAAA");

            // 준영속 => findMember 객체를 준영속 상태로 만들었으므로 update query 가 날라가지 않는다.
//            em.detach(findMember);
//            System.out.println("===========");

            // 준영속 => 영속성 컨텍스트를 통으로 날렸으므로 이때도 update query 가 날라가지 않는다.
            em.clear();
            Member fineMember2 = em.find(Member.class, 150L);
            System.out.println("fineMember2.id = " + fineMember2.getId());
            System.out.println("fineMember2.name = " + fineMember2.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
