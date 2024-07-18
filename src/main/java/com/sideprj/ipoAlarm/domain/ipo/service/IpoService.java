package com.sideprj.ipoAlarm.domain.ipo.service;

import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;

import java.io.FileNotFoundException;
import java.util.List;

public interface IpoService {

    public void save() throws FileNotFoundException;
}
