package czar.booker.service;

import java.util.List;

import czar.booker.dtos.TagCloudItemDto;
import czar.booker.entities.Tag;

public interface TagService {
    int MAX_TAG_SIZE = 5;
	List<String> findTagNames(String likeValue);
	List<Tag> findAll();
	List<TagCloudItemDto> listTagCloud(Long idUser);
}
