package calc

/**
 * Tests for the calculator.  The first group passes with the starter
 * code; the second group will fail until you complete the TODOs in
 * Calc.scala.
 *
 * Run these with `sbt test`.
 */
class CalcTests extends munit.FunSuite {

  /*------------------ these pass with the starter --------------------*/

  test("Numbers and addition") {
    assertEquals(Calc.eval("42"), 42)
    assertEquals(Calc.eval("1 + 2"), 3)
  }

  test("Precedence: * binds tighter than +") {
    assertEquals(Calc.eval("1 + 2 * 3"), 7)
    assertEquals(Calc.eval("(1 + 2) * 3"), 9)
  }

  /*------------- these fail until you finish the TODOs ---------------*/

  test("Subtraction and division") {
    assertEquals(Calc.eval("7 - 3"), 4)
    assertEquals(Calc.eval("8 / 2"), 4)
  }

  test("Left associativity") {
    assertEquals(Calc.eval("9 - 2 - 3"), 4)   // (9-2)-3, not 9-(2-3)
    assertEquals(Calc.eval("16 / 4 / 2"), 2)  // (16/4)/2, not 16/(4/2)
  }

  test("Programs: comma-separated expressions") {
    assertEquals(Calc.evalList("1+1, 2*3, 4"), List(2, 6, 4))
    assertEquals(Calc.evalList("5"), List(5))
  }
}
