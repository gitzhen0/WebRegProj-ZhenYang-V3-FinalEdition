package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.ClassToProfessorDisplay;

public interface ClassToProfessorDisplayDao {

    ClassToProfessorDisplay getProfessorByClassId(int class_id);


}
