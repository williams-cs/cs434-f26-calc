package hw

import Sym.*

/**
 * Tests for the HW 3 nullable/FIRST computations.  All of these fail
 * until you implement First.seqNullable and First.seqFirst.
 *
 * After they pass, build the same grammars with scup and compare
 * your sets against `scup.check -dump`.
 */
class FirstTests extends munit.FunSuite:

  // S ::= A B 'd';  A ::= 'a' | eps;  B ::= 'b' | eps
  val g: BNF = Map(
    "S" -> List(List(N("A"), N("B"), T('d'))),
    "A" -> List(List(T('a')), Nil),
    "B" -> List(List(T('b')), Nil))

  test("nullable finds exactly A and B") {
    assertEquals(First.nullable(g), Set("A", "B"))
  }

  test("FIRST walks through nullable prefixes") {
    assertEquals(First.first(g),
      Map("S" -> Set('a', 'b', 'd'), "A" -> Set('a'), "B" -> Set('b')))
  }

  // The Dragon book's running example, left recursion eliminated:
  // E ::= T E';  E' ::= '+' T E' | eps;  T ::= 'n' | '(' E ')'
  val dragon: BNF = Map(
    "E"  -> List(List(N("T"), N("E'"))),
    "E'" -> List(List(T('+'), N("T"), N("E'")), Nil),
    "T"  -> List(List(T('n')), List(T('('), N("E"), T(')'))))

  test("the Dragon expression grammar") {
    assertEquals(First.nullable(dragon), Set("E'"))
    assertEquals(First.first(dragon)("E"), Set('n', '('))
    assertEquals(First.first(dragon)("E'"), Set('+'))
  }

  // Chained nullability: C and D have no epsilon production of their
  // own -- they are nullable only because A and B are, so checking
  // each production once is not enough.  The fixpoint must iterate
  // until nothing changes.
  // S ::= D 'z';  D ::= C C;  C ::= A B;  A ::= 'a' | eps;  B ::= 'b' | eps
  val chained: BNF = Map(
    "S" -> List(List(N("D"), T('z'))),
    "D" -> List(List(N("C"), N("C"))),
    "C" -> List(List(N("A"), N("B"))),
    "A" -> List(List(T('a')), Nil),
    "B" -> List(List(T('b')), Nil))

  test("nullability chains through C and D (the fixpoint must iterate)") {
    assertEquals(First.nullable(chained), Set("A", "B", "C", "D"))
    assertEquals(First.first(chained)("D"), Set('a', 'b'))
    assertEquals(First.first(chained)("S"), Set('a', 'b', 'z'))
  }
