package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.ClassToSemesterDisplay;

public interface ClassToSemesterDisplayDao {

    ClassToSemesterDisplay getSemesterbyClassId(int classId);
}
