package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.jdbc.ClassToClassroomDisplay;

public interface ClassToClassroomDisplayDao {

    ClassToClassroomDisplay getClassroomByClassId(int class_id);
}
