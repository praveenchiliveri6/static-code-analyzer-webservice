/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation.casestudyv2.something;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class StaticToolDaoImpl implements StaticToolDao {
  @Autowired
  JdbcTemplate jdbctemplate;

  @Override
  public void insert(String projectname) {
    final String sql="insert into results values (?,?,?,?,?)";
    jdbctemplate.update(sql,new Object[] {projectname,-1,-1,-1,-1});

  }

  @Override
  public Results get(String projectname) {
    final String sql="select * from results where projectname=?";
    return jdbctemplate.queryForObject(sql,new Object[] {projectname},new ResultsMapper());
  }

  @Override
  public void update(String projectname,int value) {
    final String sql="update results set coverage=? where projectname=?";
    jdbctemplate.update(sql,new Object[] {value,projectname });
  }

  @Override
  public boolean getallrows(String projectname) {
    final String sql="select * from results where projectname=?";
    final Results result;
    try {
      result=jdbctemplate.queryForObject(sql,new Object[] {projectname},new ResultsMapper());
    }
    catch(final EmptyResultDataAccessException e) {
      return false;
    }
    return result.getProjectname().equals(projectname);
  }

}
