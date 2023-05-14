package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.jdbc.ClassToSemesterDisplay;

public interface ClassToSemesterDisplayDao {

    ClassToSemesterDisplay getSemesterbyClassId(int classId);
}
