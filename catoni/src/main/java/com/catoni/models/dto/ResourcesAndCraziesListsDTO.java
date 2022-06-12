package com.catoni.models.dto;

import com.catoni.models.enums.CrazyTypes;
import com.catoni.models.enums.ResourceTypes;

import java.util.List;

public class ResourcesAndCraziesListsDTO {

    private List<ResourceTypes> resources;
    private List<CrazyTypes> crazies;

    public ResourcesAndCraziesListsDTO() {}

    public ResourcesAndCraziesListsDTO(List<ResourceTypes> resources, List<CrazyTypes> crazies) {
        this.resources = resources;
        this.crazies = crazies;
    }

    public List<ResourceTypes> getResources() {
        return resources;
    }

    public void setResources(List<ResourceTypes> resources) {
        this.resources = resources;
    }

    public List<CrazyTypes> getCrazies() {
        return crazies;
    }

    public void setCrazies(List<CrazyTypes> crazies) {
        this.crazies = crazies;
    }
}
