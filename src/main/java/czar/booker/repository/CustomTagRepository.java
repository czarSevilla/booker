package czar.booker.repository;

import java.util.Map;

public interface CustomTagRepository {
    int getMinCountTag(Long idUser);
    int getMaxCountTag(Long idUser);
    Map<String, Integer> getTagCloudItems(int minCount, int maxCount, int maxSize, Long idUser);
}
