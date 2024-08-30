package com.sideprj.ipoAlarm.domain.listingalarm.entity;

import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.listingshares.entity.ListingShares;
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
@Table(name = "listing_shares_alarms")
public class ListingSharesAlarms {

    @Id
    @Column(name = "listing_shares_alarms_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingSharesAlarmId;

    @ManyToOne
    @JoinColumn(name = "ipo_name", referencedColumnName = "ipo_name", foreignKey =@ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ListingShares listingShares;

    private String listingDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

}
