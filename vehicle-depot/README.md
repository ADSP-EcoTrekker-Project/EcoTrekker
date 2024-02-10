# Vehicle Depot Service

Takes a line as input and returns each known vehicle that runs on the line, as well as the share of them used.

## Input

The input is a json body, with the key `line` and value of the line.

```json
{
    "line": "169"
}
```

## Output

The output looks like this.

```json
{
    "line": "169",
    "vehicles": {
        "bus": 0.5,
        "e-bus": 0.5
    }
}
```

## Testing

You can use curl to test the Endpoint:

```powershell
curl -X GET localhost:8080/v1/depot -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"line\":\"169\"}'
```