/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Task;


import java.util.ArrayList;

/**
 *
 * @author Ali Emir Samlioglu
 */
public abstract class TaskFactory {
         public static ITask createInstance(){
        return new Task();
    }
    public static ArrayList<ITask> createListInstance(){
            return new ArrayList<ITask>();
    }
}
