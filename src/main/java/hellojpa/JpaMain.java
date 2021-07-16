package hellojpa;

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

            // 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험한 case 테스트
            Address address = new Address("city", "street", "10000");
            Member member = new Member();
            member.setUserName("member1");
            member.setHomeAddress(address);
            em.persist(member);


//            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
//            Member member2 = new Member();
//            member2.setUserName("member2");
//            member2.setHomeAddress(address);
//            member2.setHomeAddress(copyAddress);
//            em.persist(member2);

            //
//            member.getHomeAddress().setCity("newCity"); // 불변객체로 만듦으로써 side effect 를 잡을 수 있다.

            // 그럼 기존에 address 값을 바꾸기 위해서는? 인스턴스를 새로 만들어서 통째로 바꿔줘야 한다.
            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("==================================================");
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(Member m1, Member m2) {
        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
        System.out.println("m1 instanceof Member: " + (m1 instanceof Member));
        System.out.println("m2 instanceof Member: " + (m2 instanceof Member));
    }

}
