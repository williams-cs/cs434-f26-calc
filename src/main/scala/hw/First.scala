package hw

/**
 * HW 3: the nullable and FIRST computations from lecture (Dragon
 * 4.4) over a tiny stand-alone BNF representation.
 *
 * A grammar maps each nonterminal (a String) to its productions;
 * each production is a list of symbols; a symbol is a terminal
 * character or a nonterminal name.  The empty production list
 * `Nil` is epsilon.
 *
 * Example -- S ::= A B 'd';  A ::= 'a' | epsilon;  B ::= 'b' | epsilon:
 *
 *   Map("S" -> List(List(N("A"), N("B"), T('d'))),
 *       "A" -> List(List(T('a')), Nil),
 *       "B" -> List(List(T('b')), Nil))
 *
 * The fixpoint drivers (`nullable`, `first`, at the bottom) are
 * provided: each asks a per-production question over and over until
 * the answer stops changing.  You implement the two questions,
 * `seqNullable` and `seqFirst`.  scup performs exactly these
 * computations when it checks one of your grammars -- its
 * `Analysis.scala` has the same two helpers feeding the same
 * fixpoints.  After your tests pass, compare your results against
 * `scup.check -dump` on the same grammar.
 */
enum Sym:
  case T(c: Char)       // a terminal
  case N(name: String)  // a nonterminal

type BNF = Map[String, List[List[Sym]]]

object First:

  import Sym.*

  /**
   * TODO (HW 3): can a production with these symbols derive epsilon,
   * given the nonterminals known to be nullable so far?  (In
   * particular, an epsilon production -- `Nil` -- can.)
   */
  def seqNullable(syms: List[Sym], nullable: Set[String]): Boolean =
    ???

  /**
   * TODO (HW 3): FIRST of a production with these symbols, given the
   * nullable set and the FIRST sets computed so far.  Walk left to
   * right, accumulating each symbol's FIRST, and stop after the
   * first symbol that is not nullable.
   */
  def seqFirst(syms: List[Sym], nullable: Set[String],
               first: Map[String, Set[Char]]): Set[Char] =
    ???

  /*----------------- the fixpoint drivers (provided) -----------------*
   * Ask the per-production question about every production, fold the
   * answers in, and repeat until an entire pass changes nothing.
   *-------------------------------------------------------------------*/

  /** The nullable nonterminals: every A such that A =>* epsilon. */
  def nullable(g: BNF): Set[String] =
    var known = Set[String]()
    var changed = true
    while changed do
      changed = false
      for (a, prods) <- g if !known(a) do
        if prods.exists(p => seqNullable(p, known)) then
          known += a
          changed = true
    known

  /** FIRST(A) for every nonterminal A: the terminals that can begin
   *  a string derived from A. */
  def first(g: BNF): Map[String, Set[Char]] =
    val nul = nullable(g)
    var sets = g.map((a, _) => a -> Set[Char]())
    var changed = true
    while changed do
      changed = false
      for (a, prods) <- g do
        val f = prods.foldLeft(sets(a))((acc, p) => acc ++ seqFirst(p, nul, sets))
        if f != sets(a) then
          sets = sets.updated(a, f)
          changed = true
    sets
