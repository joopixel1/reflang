package reflang;

import reflang.AST.Program;

/**
 * This main class implements the Read-Eval-Print-Loop of the interpreter with
 * the help of Reader, Evaluator, and Printer classes.
 *
 * @author hridesh
 */
public class Interpreter {
    public static void main(String[] args) {
        System.out.println("""
                RefLang: Type a program to evaluate and press the enter key,
                e.g. (ref 342)
                or try (deref (ref 342))
                or try (let ((class (ref 342))) (deref class))
                or try (let ((class (ref 342))) (set! class 541))
                or try  (let ((r (ref 342))) (let ((d (free r))) (deref r)))
                Press Ctrl + C to exit.
                """);
        try (Reader reader = new Reader()) {
            Evaluator eval = new Evaluator();
            Printer printer = new Printer();
            // Read-Eval-Print-Loop (also known as REPL)
            while (true) {
                Program p;
                try {
                    p = reader.read();
                    if (p == null) {
                        System.out.println();
                        break;
                    } else if (p._e == null) {
                        System.out.println();
                    } else {
                        Value val = eval.valueOf(p);
                        printer.print(val);
                    }
                } catch (Env.LookupException e) {
                    printer.print(e);
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error closing input stream: " + e.getMessage());
        }
    }
}