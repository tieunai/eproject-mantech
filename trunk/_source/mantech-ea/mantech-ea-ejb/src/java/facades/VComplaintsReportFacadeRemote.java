/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Departments;
import entities.Threads;
import entities.VcomplaintsReport;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface VComplaintsReportFacadeRemote {

    VcomplaintsReport find(Object id);

    List<VcomplaintsReport> findAll();

    List<VcomplaintsReport> findBetweenTime(int threadid, Date fromTime, Date toTime);

    List<VcomplaintsReport> findBetweenTime(int department, int threadid, Date fromTime, Date toTime);

    List<VcomplaintsReport> findBetweenTime(int department, int threadid, int user, Date fromTime, Date toTime);

    List<VcomplaintsReport> findBetweenTimeUser(int userref, Date fromTime, Date toTime);

    List<VcomplaintsReport> findBetweenTime(Date fromTime, Date toTime);

    List<VcomplaintsReport> findAll(int department);

    List<VcomplaintsReport> findAll(int department, Date start, Date end);

    int count();
}
