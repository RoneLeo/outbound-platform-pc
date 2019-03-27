package com.chiyun.outboundplatform.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "taskstate", schema = "ajjcxx", catalog = "")
public class TaskstateEntity {
    private int id;
    private String rwzt;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type")
    public String getRwzt() {
        return rwzt;
    }

    public void setRwzt(String rwzt) {
        this.rwzt = rwzt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskstateEntity that = (TaskstateEntity) o;
        return id == that.id &&
                Objects.equals(rwzt, that.rwzt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rwzt);
    }
}
