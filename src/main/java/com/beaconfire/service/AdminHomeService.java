package com.beaconfire.service;

import com.beaconfire.dao.DAOinterface.AdminHomeDisplayDao;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


@Service
public class AdminHomeService {

    @Autowired
    private AdminHomeDisplayDao adminHomeDisplayDao;

    public List<AdminHomeDisplay> displayAdminHomeStudents(int page, int limit){
        List<AdminHomeDisplay> adminHomeStudents = adminHomeDisplayDao.findPaginated(page, limit);

        adminHomeStudents.stream().forEach(ahs -> {
            ahs.setStudent_id(null);
            ahs.setFull_name(ahs.getFirst_name() + " " + ahs.getLast_name());
            ahs.setFirst_name(null);
            ahs.setLast_name(null);
            String ac = ahs.getIs_active();
            if(ac.equals("1")){
                ahs.setIs_active("Active");
            }else{
                ahs.setIs_active("Inactive");
            }
        });

        return adminHomeStudents;
    }


}
