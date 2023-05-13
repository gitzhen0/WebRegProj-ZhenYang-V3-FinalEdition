package com.beaconfire.dao;


import com.beaconfire.domain.jdbc.ClassManagementDisplay;

import java.util.List;

public interface ClassManagementDisplayDao {

    List<ClassManagementDisplay> getClassManagementDisplayByStudentId(int userId);


}
