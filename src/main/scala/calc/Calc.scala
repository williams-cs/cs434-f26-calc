package calc

import scup.{GrammarSpec, ParseError, Token}
import slex.{LexError, LexSpec}

/** The kinds of tokens in the calculator language. */
enum TokenKind:
  case NUM, PLUS, MINUS, STAR, SLASH, LPAREN, RPAREN, COMMA

/**
 * The calculator: the tokens come from calc.slex (provided in full),
 * the grammar and its evaluation actions from calc.scup -- which is
 * where your work goes.  This object is the host: it loads the two
 * specs and runs them.  Errors are the libraries' own -- slex's
 * LexError, scup's ParseError -- and Main handles each of them,
 * along with runtime errors like dividing by zero.
 *
 * Loading happens when Calc is first used; if calc.scup is not
 * LL(1), the load reports the conflicting rules and terminals --
 * exactly the FIRST/FOLLOW reasoning of HW 3, which you can also
 * check by hand against `sbt "runMain scup.check calc.scup"`.
 */
object Calc:

  private lazy val lexSpec = LexSpec.load[TokenKind]("calc.slex")
  private lazy val grammar = GrammarSpec.load("calc.scup", lexSpec.binding)

  def lex(source: String): IndexedSeq[Token[TokenKind]] =
    lexSpec.tokenize(source)

  /** Parse and evaluate one expression. */
  def eval(source: String): Int =
    grammar.parse(grammar.ruleFor("expr"), lex(source)).asInstanceOf[Int]

  /**
   * Parse and evaluate a comma-separated list of expressions.
   *
   * TODO (HW 3): a program is %list(expr, ",") -- add a program rule
   * to calc.scup, make it the %start, and parse it here with
   * grammar.parseAs[List[Int]].
   */
  def evalList(source: String): List[Int] =
    ???

object Main:
  def main(args: Array[String]): Unit =
    if args.isEmpty then println("usage: run \"1 + 2 * 3\"")
    else
      val src = args.mkString(" ")
      try println(if src.contains(',') then Calc.evalList(src) else Calc.eval(src))
      catch
        case e: LexError            => println(s"lex error at ${e.pos}: ${e.message}")
        case e: ParseError          => println(e.getMessage)
        case e: ArithmeticException => println(s"arithmetic error: ${e.getMessage}")
