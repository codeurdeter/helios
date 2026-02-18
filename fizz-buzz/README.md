# FizzBuzz REST API

An API built with Spring Boot, Java, sqlite and hibernate.

I choose to use SQLITE to ensure that the application is self-contained and easy to run without requiring an external database server installation and to be prod ready as it was required.

The sql file where the date is keeped is fizzbuzz.db under data folder


Given two divisors (`int1`, `int2`) and two replacement strings (`str1`, `str2`), returns the FizzBuzz sequence from 1 to `limit`. A statistics endpoint tracks the most frequently used parameters.

## Run

```bash
./gradlew bootRun
```

or

```bash
docker build -t fizz-buzz-api .
docker run --rm -p 8080:8080 -v "$(pwd)/data:/app/data" fizz-buzz-api
```

## Test

```bash
./gradlew test
```

## Endpoints

### `GET /api/fizzbuzz`

| Param | Type   | Description                          |
|-------|--------|--------------------------------------|
| int1  | int    | First divisor (1-1000)               |
| int2  | int    | Second divisor (1-1000)              |
| limit | int    | Upper bound of the sequence (1-1000) |
| str1  | string | Replacement for multiples of int1    |
| str2  | string | Replacement for multiples of int2    |

```bash
curl "http://localhost:8080/api/fizzbuzz?int1=3&int2=5&limit=15&str1=fizz&str2=buzz"
```

```json
["1","2","fizz","4","buzz","fizz","7","8","fizz","buzz","11","fizz","13","14","fizzbuzz"]
```

### `GET /api/statistics`

Returns the most frequently requested parameter combination.

```bash
curl "http://localhost:8080/api/statistics"
```

```json
{"int1":3,"int2":5,"limit":15,"str1":"fizz","str2":"buzz","count":5}
```
