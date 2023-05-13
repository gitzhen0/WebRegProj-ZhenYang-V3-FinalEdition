package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.ClassToLectureDisplay;

public interface ClassToLectureDisplayDao {

    ClassToLectureDisplay getLectureByClassId(int classId);
}
