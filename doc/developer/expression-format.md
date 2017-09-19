# Expression Format

This document describes the representations we use for the logic expressions
that Edsger manipulates.

Expressions are represented as lists. The first element is the operator and the
operands follow. Operators will be represented as Clojure keywords. The
following is a list of operations that Edsger should handle properly.

```
:equiv -- boolean equivalance   -- any number of booleans
:equal -- equality              -- any number of booleans
:and   -- logical AND           -- any number of booleans
:or    -- logical OR            -- any number of booleans
:not   -- logical NOT           -- one boolean
:implies -- logical implication -- first operand implies the second
```

That list should be enough to get us going, but we'll want the following in the
future.

```
:greater-than  -- greater than (>)      -- any number of integers, in decending order
:less-than     -- less than (<)         -- any number of integers, in ascending order
:element-of    -- self-explanetory name -- two operands, second is a set
:strict-subset -- subset, but not equal -- first operand is a strict subset of the second
:subset-of     --                       -- first operand is a subset of the second
:union         --                       -- two sets
:intersection  --                       -- two sets
```

It is not (yet) clear how aware of the semantics of these operators our core
logic will need to be.

