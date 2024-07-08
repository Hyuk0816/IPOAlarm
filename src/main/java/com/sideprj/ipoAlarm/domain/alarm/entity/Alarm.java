package com.sideprj.ipoAlarm.domain.alarm.entity;

import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id", nullable = false)
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "ipo_name", referencedColumnName = "ipo_name", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Ipo ipo;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;




}
