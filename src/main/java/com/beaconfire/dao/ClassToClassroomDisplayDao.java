package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.ClassToClassroomDisplay;

public interface ClassToClassroomDisplayDao {

    ClassToClassroomDisplay getClassroomByClassId(int class_id);
}
