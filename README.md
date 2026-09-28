# Calculator

A warm-up exercise for LL(1) grammar specs with the course's scup
library, before you build the full IC parser in PA 2.

The calculator is two spec files at the root of this repository:
`calc.slex` (the tokens, provided in full) and `calc.scup` (the
grammar and its evaluation actions) -- follow the TODOs in
`calc.scup`, and see the HW 3 handout and the
[scup tutorial](https://www.cs.williams.edu/~freund/cs434/scup.html).
`src/main/scala/calc/Calc.scala` is the small host that loads the
specs; only `evalList` needs your attention there.

The library source is included under `src/main/scala/scup` (and
`slex`) -- read it, it is short.  To check a grammar's FIRST/FOLLOW
sets and LL(1) table against your hand computation:

    sbt "runMain scup.check -dump calc.scup"

# To Compile:

    sbt compile

# To Run:

    sbt 'run "1 + 2 * 3"'

# To Test:

    sbt test

Some of the tests fail with the starter code; they pass when you have
completed the TODOs (in `calc.scup` and in `hw/First.scala`).
