package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.AdminClassToStudentDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface StudentClassDisplayDao {

    List<StudentClassDisplay> findPaginated(int page, int limit, int id);

    int getTotalPages(int limit, int id);

    List<AdminClassToStudentDisplay> findStudentsByClassId(int classId);
}
