/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Answers;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface AnswersFacadeRemote {

    void create(Answers entity);

    void edit(Answers entity);

    void remove(Answers entity);

    Answers find(Object id);

    List<Answers> findAll();

    List<Answers> findRange(int[] range);

    int count();
}
