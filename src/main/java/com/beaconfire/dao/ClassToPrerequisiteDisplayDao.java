package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.ClassToPrerequisiteDisplay;

public interface ClassToPrerequisiteDisplayDao {

    ClassToPrerequisiteDisplay getPrerequisiteByClassId(int classId);
}
