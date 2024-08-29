package com.sideprj.ipoAlarm.domain.ipocomments.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.ipocomments.dto.IpoCommentsDto;
import com.sideprj.ipoAlarm.domain.ipocomments.entity.IpoComments;
import com.sideprj.ipoAlarm.domain.ipocomments.entity.QIpoComments;
import com.sideprj.ipoAlarm.domain.ipocomments.repository.IpoCommentsRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sideprj.ipoAlarm.domain.ipocomments.entity.QIpoComments.*;


public class IpoCommentsRepositoryImpl implements IpoCommentsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IpoCommentsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<IpoCommentsDto> findByIpoName(String ipoName) {
        return queryFactory.select(Projections.fields(IpoCommentsDto.class,
                ipoComments.comment.as("ipoComments"),
                        ipoComments.id.as("id"),
                        ipoComments.regDate.as("regDate")))
                .from(ipoComments)
                .where(ipoComments.ipoName.ipoName.eq(ipoName))
                .orderBy(ipoComments.id.asc())
                .fetch();

    }
}
