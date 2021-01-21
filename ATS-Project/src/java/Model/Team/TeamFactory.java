/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Team;

import java.util.ArrayList;

/**
 *
 * @author Ali Emir Samlioglu
 */
public abstract class TeamFactory {
     public static ITeam createInstance(){
        return new Team();
    }
    public static ArrayList<ITeam> createListInstance(){
            return new ArrayList<ITeam>();
    }
}
