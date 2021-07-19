package hellojpa;

import java.util.List;
import java.util.Set;
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

            // 값 타입 저장 예제
            Member member = new Member();
            member.setUserName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavorateFoods().add("치킨");
            member.getFavorateFoods().add("족발");
            member.getFavorateFoods().add("피자");

//            member.getAddressHistory().add(new Address("old1", "street", "10000"));
//            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            // 값 타입 조회 예제
            System.out.println("========= START ==========");
            Member findMember = em.find(Member.class, member.getId());
//            List<Address> addressHistory = findMember.getAddressHistory();
//            for (Address address : addressHistory) {
//                System.out.println("address = " + address.getCity());
//            }

            Set<String> favorateFoods = findMember.getFavorateFoods();
            for (String favorateFood : favorateFoods) {
                System.out.println("favorateFood = " + favorateFood);
            }

            // 값 타입 수정 예제 (homeCity -> newCity)
            System.out.println("========= START ==========");
//            findMember.getHomeAddress().setCity("newCity"); // 이렇게 하면 안된다. 이전시간에 배웠듯이 side effect 가 발생할 수 있기 때문에 값 타입은 immutable 해야 한다.
            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipcode()));

            // 값 타입 수정 예제 (치킨 -> 한식)
            findMember.getFavorateFoods().remove("치킨");
            findMember.getFavorateFoods().add("한식");
                // delete -> insert

            // 값 타입 수정 예제 (old1 -> newCity1)
//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000")); // equals 를 기반으로 찾아서 제거해준다.
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));
            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000")); // equals 를 기반으로 찾아서 제거해준다.
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "10000"));
                // delete -> insert, insert (테이블을 통으로 업데이트 했다!!)

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
