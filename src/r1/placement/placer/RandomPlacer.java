package r1.placement.placer;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import r1.Position;
import r1.PositionedArea;
import r1.placement.PlacerComponentMemory;
import r1.placement.ShipPlacement;

public class RandomPlacer implements Placer {

    private final PlacerComponentMemory memory;
    private final Random random = new Random();
    private final PositionedArea boardArea;
    private List<PositionedArea> placedShips;

    public RandomPlacer(PlacerComponentMemory memory) {
        this.memory = memory;
        this.boardArea = new PositionedArea(memory.getSizeX(), memory.getSizeY(), new Position(0, 0));
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {

        System.out.println("RandomPlacerTactic::placeShips");
        System.out.println(memory);

        int numberOfShips = fleet.getNumberOfShips();
        this.placedShips = new ArrayList<>();

        for (int i = 0; i < numberOfShips; i++) {
            Ship ship = fleet.getShip(i);
            ShipPlacement placement = getRandomShipPlacement(ship);
            board.placeShip(placement.getPosition(), ship, placement.getRotation().toBoolean());
        }
    }

    private ShipPlacement getRandomShipPlacement(Ship ship) {

        int randomX = random.nextInt(memory.getSizeX());
        int randomY = random.nextInt(memory.getSizeY());
        boolean randomRotation = random.nextBoolean();

        PositionedArea possibleShipArea;

        if (randomRotation) {
            possibleShipArea = new PositionedArea(1, ship.size(), new r1.Position(randomX, randomY));
        } else {
            possibleShipArea = new PositionedArea(ship.size(), 1, new r1.Position(randomX, randomY));
        }

        if (possibleShipArea.overlaps(placedShips) || !boardArea.contains(possibleShipArea)) {
            return getRandomShipPlacement(ship);
        }

        placedShips.add(possibleShipArea);
        return new ShipPlacement(ship, possibleShipArea.getPosition(), randomRotation);
    }
}
