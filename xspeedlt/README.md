# XspeedIt

A bin packing algorithm that optimizes how items (digits 1-9) are packed into boxes with a capacity of 10.

## Run

```bash
./gradlew run
```

## Test

```bash
./gradlew test
```

## Example

Input: `163841689525773`

Output: `163/81/46/82/9/55/73/7` (8 boxes)

Each group separated by `/` is a box. The sum of digits in each box never exceeds 10.

## Strategy

Uses a best-fit strategy: each item goes into the box where it leaves the least remaining space.
The complexity is quadratic and it's the best solution mathematically.