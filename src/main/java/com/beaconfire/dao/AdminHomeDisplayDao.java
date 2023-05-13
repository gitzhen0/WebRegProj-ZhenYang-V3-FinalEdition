package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.AdminHomeDisplay;

import java.util.List;

public interface AdminHomeDisplayDao {

    List<AdminHomeDisplay> findPaginated(int page, int limit);

    int getTotalPages(int limit);
}
