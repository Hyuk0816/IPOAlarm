package com.sideprj.ipoAlarm.domain.ipo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "ipo")
public class Ipo {

    @Id
    @Column(name = "ipo_name", nullable = false)
    private String ipoName;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "confirm_price")
    private String confirmPrice;

    @Column(name = "ipo_price", nullable = false)
    private String ipoPrice;

    @Column(name = "competition_rate")
    private String competitionRate;

    @Column(name = "securities", nullable = false)
    private String securities;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Ipo that = (Ipo) obj;
        return ipoName.equals(that.ipoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipoName);
    }
}
