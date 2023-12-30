## Timing request roundtrip

In some cases you might want to measure the roundtrip time when using curl.
Using `-o NUL` you can output to stdout.
`-s` silences the progress bar.
And `-w "@curl-format.txt"` tells curl to use the curl-format.txt file in the directory as template for the output.

An example curl command could be:

```powershell
curl -o NUL -s -w "@curl-format.txt" http://localhost:8080/v1/calc/co2 -i -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"routes\":[{\"steps\":[{\"vehicle\":\"car\",\"distance\":300},{\"vehicle\":\"bike\",\"distance\":500}]},{\"steps\":[{\"vehicle\":\"bicycle\",\"distance\":2000}]}]}'
```