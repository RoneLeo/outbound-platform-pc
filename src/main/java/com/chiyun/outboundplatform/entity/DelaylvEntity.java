package com.chiyun.outboundplatform.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "delaylv", schema = "ajjcxx", catalog = "")
public class DelaylvEntity {
    private Integer id;
    private String level;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelaylvEntity that = (DelaylvEntity) o;
        return id == that.id &&
                Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level);
    }
}
