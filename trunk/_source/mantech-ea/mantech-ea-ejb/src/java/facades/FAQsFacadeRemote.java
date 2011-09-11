/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FAQs;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface FAQsFacadeRemote {
    void create(FAQs entity);

    void edit(FAQs entity);

    void remove(FAQs entity);

    FAQs find(Object id);

    List<FAQs> findAll();

    List<FAQs> findRange(int[] range);

    int count();

    List<FAQs> emplFindAll();
}
