package com.beaconfire.Utils;

import com.beaconfire.domain.jdbc.AdminApplication;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdminApplicationUtil {

    public static void sortApplicationsByCreationTimeDescending(List<AdminApplication> applications) {
        Collections.sort(applications, new Comparator<AdminApplication>() {
            @Override
            public int compare(AdminApplication a1, AdminApplication a2) {
                // since we want descending order, a2 comes before a1
                return a2.getCreation_time().compareTo(a1.getCreation_time());
            }
        });
    }
}
