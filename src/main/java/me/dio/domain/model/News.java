package me.dio.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity(name = "tb_news")
public class News extends BaseItem {

    @Column(name = "icon")
    private String icon;

    @Column(name = "description")
    private String description;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
