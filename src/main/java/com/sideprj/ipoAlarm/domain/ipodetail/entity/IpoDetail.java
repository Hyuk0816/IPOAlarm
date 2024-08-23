package com.sideprj.ipoAlarm.domain.ipodetail.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "ipo_detail")
public class IpoDetail {

    @Id
    @Column(name = "ipo_name", nullable = false)
    private String ipoName;

    @Column(name = "industry", nullable = false)
    private String industry;

    @Column(name = "representative", nullable = false)
    private String representative;

    @Column(name = "revenue", nullable = false)
    private String revenue;

    @Column(name = "netprofit", nullable = false)
    private String netProfit;

    @Column(name = "total_offerd_shares", nullable = false)
    private String totalOfferedShares;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        IpoDetail that = (IpoDetail) obj;
        return ipoName.equals(that.ipoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipoName);
    }
}
