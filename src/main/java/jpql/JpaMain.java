package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //*************JPQL 기본문법
//            Member member = new Member();
//            member.setUsername("member1");
//            em.persist(member);
//
//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
//
//            System.out.println("result: " + result.getUsername());

            //*************프로젝션
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            //엔티티 타입
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            //임베디드 타입
            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            //스칼라 타입
            Query query = em.createQuery("select m.username, m.age from Member m");
//            List<Object[]> resultList = query.getResultList();

//            Object resultObject = resultList.get(0);
//            Object[] resultArr = (Object[]) resultObject; //Query타입 사용 시 변환 과정

//            Object[] resultArr = resultList.get(0); //제네릭 사용 시 이것만
//            System.out.println("username: " + resultArr[0]);
//            System.out.println("age: " + resultArr[1]);

            List<MemberDto> resultList = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            MemberDto memberDto = resultList.get(0)
;
            System.out.println("memberDto: " + memberDto.getUsername());
            System.out.println("memberDto: " + memberDto.getAge());


            Member findMember = result.get(0);
            findMember.setAge(20);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
