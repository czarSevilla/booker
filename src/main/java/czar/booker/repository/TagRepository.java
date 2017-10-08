package czar.booker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import czar.booker.entities.Tag;

@Repository("tagRepository")
public interface TagRepository extends JpaRepository<Tag, Long> {

	@Query("select t.name from Tag t where t.name like ? order by t.name")
	List<String> findTagNames(String likeValue);
	
	Tag findOneByName(String name);
}
