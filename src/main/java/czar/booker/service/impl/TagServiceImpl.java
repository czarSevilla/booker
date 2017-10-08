package czar.booker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import czar.booker.dtos.TagCloudItemDto;
import czar.booker.entities.Tag;
import czar.booker.repository.CustomTagRepository;
import czar.booker.repository.TagRepository;
import czar.booker.service.TagService;

@Service("tagService")
@Transactional
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private CustomTagRepository customTagRepository;

	@Override
	public List<String> findTagNames(String likeValue) {
		return tagRepository.findTagNames(String.format("%%%s%%", likeValue));
	}

	@Override
	public List<Tag> findAll() {
		return tagRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

    @Override
    public List<TagCloudItemDto> listTagCloud(Long idUser) {
        int minCount = customTagRepository.getMinCountTag(idUser);
        int maxCount = customTagRepository.getMaxCountTag(idUser);
        int maxSize = TagService.MAX_TAG_SIZE;
        Map<String, Integer> map = customTagRepository.getTagCloudItems(minCount, maxCount, maxSize, idUser);
        List<TagCloudItemDto> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(new TagCloudItemDto(entry.getKey(), entry.getValue(), null));
        }
        return result;
    }
}
