package czar.booker.dtos;

import org.primefaces.model.tagcloud.TagCloudItem;

public class TagCloudItemDto implements TagCloudItem {
    
    private String label;
    private int strength;
    private String url;

    public TagCloudItemDto() {
        
    }
    
    public TagCloudItemDto(String pLabel, int pStrength, String pUrl) {
        this.label = pLabel;
        this.strength = pStrength;
        this.url = pUrl;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

}
