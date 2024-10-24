package org.transcom.enums;

import lombok.Getter;

@Getter
public enum TruckType {
    DRY_VAN("DRYV", "Dry Van", 16.0, 50.0, "https://example.com/dry_van.jpg"),
    VAN("VAN", "Standard Van", 14.0, 40.0, "https://example.com/van.jpg"),
    CITYVAN("CITYVAN", "City Van", 6.0, 3.0, "https://example.com/city_van.jpg"),
    CURTAIN_SIDE_TRAILER("CURT", "Curtain Side Trailer", 18.0, 55.0, "https://example.com/curtain_side.jpg"),
    TAIL_LIFT_TRAILER("TAIL", "Tail Lift Trailer", 15.0, 48.0, "https://example.com/tail_lift.jpg"),
    FLATBED("FLAT", "Flatbed", 17.0, 60.0, "https://example.com/flatbed.jpg"),
    STEP_DECK_TRAILER("STEP", "Step Deck Trailer", 18.5, 65.0, "https://example.com/step_deck.jpg"),
    LOWBOY_TRAILER("LOWB", "Lowboy Trailer", 13.0, 45.0, "https://example.com/lowboy.jpg"),
    DOUBLE_DROP_TRAILER("DBLE", "Double Drop Trailer", 20.0, 70.0, "https://example.com/double_drop.jpg"),
    EXTENDABLE_FLATBED_TRAILER("EXTD", "Extendable Flatbed Trailer", 22.0, 75.0, "https://example.com/extendable_flatbed.jpg"),
    CONTAINER_TRUCK("CONT", "Container Truck", 12.0, 50.0, "https://example.com/container_truck.jpg"),
    SIDE_WALL_TRAILER("SIDE", "Side Wall Trailer", 14.0, 55.0, "https://example.com/side_wall.jpg"),
    FLAT_BODY_WITH_VERTICAL_SIDES("FBVS", "Flat Body with Vertical Sides", 16.0, 50.0, "https://example.com/flat_body_vertical_sides.jpg"),
    REFRIGERATED_TRAILER("REFR", "Refrigerated Trailer", 16.0, 45.0, "https://example.com/refrigerated_trailer.jpg"),
    REEFER_TRAILER("REEF", "Reefer Trailer", 17.0, 50.0, "https://example.com/reefer_trailer.jpg"),
    INSULATED_TRAILER("INSU", "Insulated Trailer", 16.0, 47.0, "https://example.com/insulated_trailer.jpg"),
    MULTICAR_TRAILER("MCTR", "Multicar Trailer", 18.0, 55.0, "https://example.com/multicar_trailer.jpg"),
    CAR_HAULER("CARR", "Car Hauler", 17.0, 50.0, "https://example.com/car_hauler.jpg"),
    DUMP_TRUCK("DUMP", "Dump Truck", 12.0, 40.0, "https://example.com/dump_truck.jpg"),
    TANK_TRAILER("TANK", "Tank Trailer", 15.0, 60.0, "https://example.com/tank_trailer.jpg"),
    TANK_FOR_EDIBLE_LIQUIDS("EDIB", "Tank for Edible Liquids", 14.0, 50.0, "https://example.com/tank_edible_liquids.jpg"),
    GENERAL_CHEMICAL_TRAILER("CHEM", "General Chemical Trailer", 15.0, 55.0, "https://example.com/general_chemical_trailer.jpg"),
    FUEL_TANKER("FUEL", "Fuel Tanker", 13.0, 45.0, "https://example.com/fuel_tanker.jpg"),
    GAS_LNG_LPG_TANK_TRUCK("LNGG", "LNG/LPG Gas Tank Truck", 14.0, 52.0, "https://example.com/lng_lpg_truck.jpg"),
    GAS_CYLINDER_TRUCK("CYLN", "Gas Cylinder Truck", 12.0, 40.0, "https://example.com/gas_cylinder_truck.jpg"),
    BITUMEN_TANK_TRAILER("BTMN", "Bitumen Tank Trailer", 16.0, 50.0, "https://example.com/bitumen_tank_trailer.jpg"),
    FISH_CARRIER("FISH", "Fish Carrier", 14.0, 48.0, "https://example.com/fish_carrier.jpg"),
    LIVE_FISH_CARRIER("LFIS", "Live Fish Carrier", 14.0, 48.0, "https://example.com/live_fish_carrier.jpg"),
    POLE_TYPE_TRAILER("POLE", "Pole Type Trailer", 18.0, 60.0, "https://example.com/pole_type_trailer.jpg"),
    LOGGING_TRUCK("LOGG", "Logging Truck", 17.0, 55.0, "https://example.com/logging_truck.jpg"),
    TIMBER_LORRY("TIMB", "Timber Lorry", 17.0, 55.0, "https://example.com/timber_lorry.jpg"),
    DRY_BULK_TRAILER("DRBK", "Dry Bulk Trailer", 16.0, 50.0, "https://example.com/dry_bulk_trailer.jpg"),
    CEMENT_TANK_TRAILER("CEMT", "Cement Tank Trailer", 15.0, 48.0, "https://example.com/cement_tank_trailer.jpg"),
    FLOUR_TRUCK("FLUR", "Flour Truck", 13.0, 42.0, "https://example.com/flour_truck.jpg"),
    GRAIN_TRUCK("GRAI", "Grain Truck", 15.0, 50.0, "https://example.com/grain_truck.jpg"),
    CONCRETE_PANEL_TRAILER("CNCP", "Concrete Panel Trailer", 16.0, 53.0, "https://example.com/concrete_panel_trailer.jpg"),
    TRAILER_FOR_GLASS_TRANSPORTING("GLAS", "Trailer for Glass Transporting", 15.0, 45.0, "https://example.com/glass_trailer.jpg"),
    LIVESTOCK_TRAILER("LIVE", "Livestock Trailer", 17.0, 60.0, "https://example.com/livestock_trailer.jpg"),
    CATTLE_TRAILER("CATL", "Cattle Trailer", 17.0, 60.0, "https://example.com/cattle_trailer.jpg"),
    POULTRY_TRUCK_TRAILER("PULT", "Poultry Truck/Trailer", 16.0, 55.0, "https://example.com/poultry_trailer.jpg"),
    COIL_TRAILER("COIL", "Coil Trailer", 14.0, 50.0, "https://example.com/coil_trailer.jpg"),
    ROLL_TRAILER("ROLL", "Roll Trailer", 14.0, 50.0, "https://example.com/roll_trailer.jpg"),
    WALKING_FLOOR("WALK", "Walking Floor", 15.0, 55.0, "https://example.com/walking_floor.jpg"),
    MOVING_FLOOR("MOVE", "Moving Floor", 15.0, 55.0, "https://example.com/moving_floor.jpg");

    private final String shortName;
    private final String fullName;
    private final double lengthMeters;
    private final double capacityCubicUnits;
    private final String imageUrl;

    TruckType(String shortName, String fullName, double lengthMeters, double capacityCubicUnits, String imageUrl) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.lengthMeters = lengthMeters;
        this.capacityCubicUnits = capacityCubicUnits;
        this.imageUrl = imageUrl;
    }

}