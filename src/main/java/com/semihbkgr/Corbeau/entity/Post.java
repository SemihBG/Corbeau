package com.semihbkgr.Corbeau.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Table(name="posts")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "title",nullable = false)
    private String title;

    @Lob
    @Column(name="content",nullable = false)
    private String content;

    @Column(name="url_endpoint",nullable = false,unique = true)
    private String urlEndpoint;

    @Column(name="view_count")
    private int viewCount;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;

}
