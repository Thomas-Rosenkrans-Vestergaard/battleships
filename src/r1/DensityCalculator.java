/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapOutOfBoundsException;
import r1.heatmap.HeatMapView;
import r1.heatmap.NoActiveHeatMapVersionException;

/**
 *
 * @author Skole
 */
public class DensityCalculator {

    private Collection<Position> blockers = new HashSet<>();
    ;
    private List<Area> ships = new ArrayList<>();

    public void addBlocker(battleship.interfaces.Position position) {
        this.blockers.add(new Position(position));
    }

    public void addBlocker(Position position) {
        this.blockers.add(position);
    }

    public void addBlocker(PositionedArea area) {
        for (Position position : area.getPositions()) {
            this.blockers.add(position);
        }
    }

    public void addBlocker(int x, int y) {
        this.blockers.add(new Position(x, y));
    }

    public void addShip(int length) {
        this.ships.add(new Area(1, length));
        this.ships.add(new Area(length, 1));
    }

    public void calculate(HeatMap hm) throws NoActiveHeatMapVersionException {

        try {

            int sizeX = hm.getSizeX();
            int sizeY = hm.getSizeY();

            for (Area ship : ships) {
                for (int x = 0; x <= sizeX - ship.sizeX; x++) {
                    for (int y = 0; y <= sizeY - ship.sizeY; y++) {
                        Position position = new Position(x, y);
                        PositionedArea placement = ship.position(position);
                        if (!placement.containsAny(blockers)) {
                            for (Position areaPosition : placement.getPositions()) {
                                hm.addToValue(areaPosition, 1);
                            }
                        }
                    }
                }
            }

        } catch (HeatMapOutOfBoundsException e) {
            throw new IllegalStateException(e);
        }
    }
}
