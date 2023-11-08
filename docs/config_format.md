To enable operators of the API to easily configure the CO2 emissions for different vehicles,
we plan to implement a simple but expandable config system for Vehicles / Modes of Transportation.

We expect in some cases the need to configure hundreds of vehicles, which is why we provide two ways to split config files.

Firstly, operators will be able to specify multiple files or folders to be read.
And secondly, config files can include other files.

A simple config file could look like this:

```yaml

- name: car
  emissions: 192
- name: ecar
  kwh: 30
  parent: car
- name: icecar
  emissions: 313
  parent: car

```

The config file defines a parent vehicle named 'car', with an emissions attribute and two children, ICE (Internal Combustion Engine) cars and Electric Cars.
We will be able to convert between kwh used per 100km driving and co2 emissions in gram per km, so it is not required (but possible) to provide emissions for electric vehicles.

You can add an `include: <relative/path/to/config>` to tell the parser for the config files where to find more config files.

For example, we can split the config above into:

`config.yaml`

```yaml

- name: car
  emissions: 192
- include: cars.yaml

```

`cars.yaml`

```yaml

- name: ecar
  kwh: 30
  parent: car
- name: icecar
  emissions: 313
  parent: car

```