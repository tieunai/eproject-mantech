/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Articles;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface ArticlesFacadeRemote {
    void create(Articles entity);

    void edit(Articles entity);

    void remove(Articles entity);

    Articles find(Object id);

    List<Articles> findAll();

    List<Articles> findRange(int[] range);

    List<Articles> findByEnable(boolean isEnable);

    List<Articles> emplFindAll();

    int count();
}
