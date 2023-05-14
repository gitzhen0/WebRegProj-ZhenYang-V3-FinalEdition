package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.jdbc.WebRegClassDisplay;

public interface WebRegClassDisplayDao {

    WebRegClassDisplay getWebRegClassDisplayById(Integer id);

    WebRegClassDisplay getWebRegClassDisplayByClassId(Integer id);

    Boolean classExistsById(Integer classId);
}
