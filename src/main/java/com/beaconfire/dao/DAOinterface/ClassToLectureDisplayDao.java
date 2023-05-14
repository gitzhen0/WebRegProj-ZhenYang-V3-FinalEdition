package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.jdbc.ClassToLectureDisplay;

public interface ClassToLectureDisplayDao {

    ClassToLectureDisplay getLectureByClassId(int classId);
}
