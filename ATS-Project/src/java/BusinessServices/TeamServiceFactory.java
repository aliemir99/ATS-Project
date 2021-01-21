/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

/**
 *
 * @author jber5
 */
public abstract class TeamServiceFactory {
    public static ITeamService createInstance(){
        return new TeamService();
    } 
}
