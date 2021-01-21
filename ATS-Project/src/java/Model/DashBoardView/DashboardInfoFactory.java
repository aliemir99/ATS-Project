/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DashBoardView;

/**
 *
 * @author jber5
 */
public abstract class DashboardInfoFactory {
    public static IDashboardInfo createInstance(){
        return new DashboardInfo();
    }
}
