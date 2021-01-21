/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

/**
 *
 * @author jber5
 */
public abstract class SqlTeamFactory {
    public static ISqlTeam createInstance(){
        return new SqlTeam();
    }
}
