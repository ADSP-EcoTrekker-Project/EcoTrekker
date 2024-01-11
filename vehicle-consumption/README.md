# Vehicle Consumption Service

## What

The Vehicle Consumption Service provides information about vehicles using their name.
It takes input files and build a representation in memory.

## Config Format

The service currently supports config files only in the .toml format.

Read more about the config format [here](../docs/VEHICLE%20CONFIG%20FORMAT.md)

In memory a Tree structure is used.

### Extend It!

The service containers Abstract Classes and Interfaces to allow us to simply extend it with alternative config file formats.
In theory all formats supported by the parsers of the [Jackson Project](https://github.com/FasterXML/jackson) are possible.
The information in each format stays the same, because we use the [VehicleDataFile.java](.\src\main\java\com\ecotrekker\vehicleconsumption\parser\VehicleDataFile.java) class that represents a file, to parse the Data into.

## Config Files

The current config files can be found in the vehicle-config folder, they will also be copied to the container which will run the service.

The current tree:

```powershell
vehicles.toml
├── cars.toml
├── trains.toml
├── bus.toml
├── tram.toml
├── metro.toml
├── bike.toml
└── scooter.toml
```

## Endpoints

The Vehicle Consumption service currently has two sets of Endpoints.

A status set, which includes an `/status/alive` and a `/status/ready` Endpoint.

And the first version of the API, which can be accessed using `/v1/consumption`.

### Test Endpoint using CURL

To test the Endpoint using CURL try:

```powershell
curl localhost:8080/v1/consumption -X POST -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"vehicle_name\":\"car\"}'
```