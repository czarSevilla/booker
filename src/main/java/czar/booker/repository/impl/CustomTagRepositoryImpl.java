package czar.booker.repository.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import czar.booker.repository.CustomTagRepository;

@Repository("customTagRepository")
public class CustomTagRepositoryImpl implements CustomTagRepository {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public int getMinCountTag(Long idUser) {
        Query q = em.createNativeQuery("select min(t.total) from (select count(*) total from book_tag where id_user = :idUser group by id_tag) t");
        q.setParameter("idUser", idUser);
        Object obj = q.getSingleResult();
        if (obj != null && obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    @Override
    public int getMaxCountTag(Long idUser) {
        Query q = em.createNativeQuery("select max(t.total) from (select count(*) total from book_tag where id_user = :idUser group by id_tag) t");
        q.setParameter("idUser", idUser);
        Object obj = q.getSingleResult();
        if (obj != null && obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> getTagCloudItems(int minCount, int maxCount, int maxSize, Long idUser) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t2.name, "); 
        sb.append("case when t.total > :minCount then ceil((:maxSize * (t.total - :minCount)) / (:maxCount - :minCount)) else 1 end strength ");
        sb.append("from (select id_tag, count(*) total from book_tag where id_user = :idUser group by id_tag) t ");
        sb.append("left join tag t2 on t.id_tag = t2.id_tag ");
        
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("minCount", minCount);
        q.setParameter("maxSize", maxSize);
        q.setParameter("maxCount", maxCount);
        q.setParameter("idUser", idUser);
        
        List<Object[]> lst = q.getResultList();
        Map<String, Integer> map = new HashMap<>();
        for (Object[] row : lst) {
            map.put((String) row[0], ((BigDecimal)row[1]).intValue());
        }
        return map;        
    }

}
