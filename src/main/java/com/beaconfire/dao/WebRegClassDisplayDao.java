package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.WebRegClassDisplay;

public interface WebRegClassDisplayDao {

    WebRegClassDisplay getWebRegClassDisplayById(Integer id);

    WebRegClassDisplay getWebRegClassDisplayByClassId(Integer id);
}
