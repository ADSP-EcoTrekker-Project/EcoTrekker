# Vehicle Config

For the vehicle consumption service it is necessary to define vehicletypes and their properties

## Vehicle Properties

A vehicle element can have the following properties:

- The vehicle name
- The co2 emissions (optional)
- The kw/h consumption per km (optional)
- The parent element (optional)

## TOML Format

```toml
include = [
    'relative/path/to/file.toml',
    '/absolute/path/to/file.toml'
]
[[vehicle]]
name = "vehicle"
co2 = 0
wh = 0
```
