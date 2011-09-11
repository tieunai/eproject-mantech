/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Complaints;
import entities.Threads;
import entities.Users;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface ComplaintsFacadeRemote {

    void create(Complaints entity);

    void edit(Complaints entity);

    void remove(Complaints entity);

    Complaints find(Object id);

    List<Complaints> findAll();

    List<Complaints> findRange(int[] range);

    int count();

    int countbyem(Users user);

    int countbytech(Users user);

    List<Complaints> findByPriority(int priority);

    List<Complaints> findByUserID(Users userID);

    List<Complaints> findByUserRef(Users userRef);

    List<Complaints> emplFindByPriority(int priority);

    List<Complaints> emplFindByUserID(Users userID);

    List<Complaints> emplFindByUserRef(Users userRef);

    List<Complaints> findByThread(Threads thread);

    List<Complaints> findBetweenTime(Threads threadID, Date fromTime, Date toTime);

    List<Complaints> findBetweenTime(Date fromTime, Date toTime);

    int countBetweenTime(Date fromTime, Date toTime);
}
