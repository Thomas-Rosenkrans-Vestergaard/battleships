/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package r1;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig
 */
public class R1 implements PlayerFactory<BattleshipsPlayer>
{

    public R1(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new AI();
    }

    @Override
    public String getID()
    {
        return "R1";
    }

    @Override
    public String getName()
    {
        return "T3";
    }

    @Override
    public String[] getAuthors()
    {
        String[] res = {"Thomas Rosenkrans Vestergaard, Thomas Fritzbøger, Tobias Leon Frehr"};
        return res;
    }
    
}
