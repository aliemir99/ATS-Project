/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Job;

import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public abstract class JobFactory {
    public static IJob createInstance(){
        return new Job();
    }
    public static ArrayList<IJob> createListInstance(){
            return new ArrayList<>();
    }
}
