package com.sideprj.ipoAlarm.domain.ipocomments.entity;

import com.sideprj.ipoAlarm.domain.ipodetail.entity.IpoDetail;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "ipo_comments")
public class IpoComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipo_comments_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipo_name", referencedColumnName = "ipo_name", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private IpoDetail ipoName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User userId;

    private String regDate;

    private String comment;


}
