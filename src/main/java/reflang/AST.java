package reflang;

import java.util.ArrayList;
import java.util.List;

/**
 * This class hierarchy represents expressions in the abstract syntax tree
 * manipulated by this interpreter.
 *
 * @author hridesh
 */
public interface AST {
    interface Visitor<T> {
        // This interface should contain a signature for each concrete AST node.
        T visit(AST.AddExp e, Env env);

        T visit(AST.UnitExp e, Env env);

        T visit(AST.NumExp e, Env env);

        T visit(AST.BoolExp e, Env env); // New for reflang

        T visit(AST.DivExp e, Env env);

        T visit(AST.MultExp e, Env env);

        T visit(AST.Program p, Env env);

        T visit(AST.SubExp e, Env env);

        T visit(AST.VarExp e, Env env); // New for varlang

        T visit(AST.LetExp e, Env env); // New for varlang

        T visit(AST.DefineDecl d, Env env); // New for reflang

        T visit(AST.LambdaExp e, Env env); // New for the reflang

        T visit(AST.CallExp e, Env env); // New for the reflang

        T visit(AST.IfExp e, Env env); // Additional expressions for convenience

        T visit(AST.LessExp e, Env env); // Additional expressions for convenience

        T visit(AST.EqualExp e, Env env); // Additional expressions for convenience

        T visit(AST.GreaterExp e, Env env); // Additional expressions for convenience

        T visit(AST.CarExp e, Env env); // Additional expressions for convenience

        T visit(AST.CdrExp e, Env env); // Additional expressions for convenience

        T visit(AST.ConsExp e, Env env); // Additional expressions for convenience

        T visit(AST.ListExp e, Env env); // Additional expressions for convenience

        T visit(AST.NullExp e, Env env); // Additional expressions for convenience

        T visit(AST.RefExp e, Env env);    // new for reflang

        T visit(AST.AssignExp e, Env env); // new for reflang

        T visit(AST.DerefExp e, Env env);  // new for reflang

        T visit(AST.FreeExp e, Env env);   // new for reflang

        T visit(AST.RefEqExp e, Env env);   // new for reflang
    }

    abstract class ASTNode implements AST {
        public abstract <T> T accept(Visitor<T> visitor, Env env);
    }

    class Program extends ASTNode {
        final List<DefineDecl> _decls;
        final Exp _e;

        public Program(List<DefineDecl> decls, Exp e) {
            _decls = decls;
            _e = e;
        }

        public Exp e() {
            return _e;
        }

        public List<DefineDecl> decls() {
            return _decls;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    abstract class Exp extends ASTNode {
        /* no-op */
    }

    class VarExp extends Exp {
        final String _name;

        public VarExp(String name) {
            _name = name;
        }

        public String name() {
            return _name;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class UnitExp extends Exp {

        public UnitExp() {
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

    }

    class NumExp extends Exp {
        final double _val;

        public NumExp(double v) {
            _val = v;
        }

        public double v() {
            return _val;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    abstract class CompoundArithExp extends Exp {
        final List<Exp> _rest = new ArrayList<>();

        public CompoundArithExp(List<Exp> args) {
            _rest.addAll(args);
        }

        public List<Exp> all() {
            return _rest;
        }

        public void add(Exp e) {
            _rest.add(e);
        }

    }

    class AddExp extends CompoundArithExp {

        public AddExp(List<Exp> args) {
            super(args);
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class SubExp extends CompoundArithExp {

        public SubExp(List<Exp> args) {
            super(args);
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class DivExp extends CompoundArithExp {

        public DivExp(List<Exp> args) {
            super(args);
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class MultExp extends CompoundArithExp {

        public MultExp(List<Exp> args) {
            super(args);
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class BoolExp extends Exp {
        final boolean _val;

        public BoolExp(boolean b) {
            _val = b;
        }

        public boolean b() {
            return _val;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    /**
     * A let expression has the syntax
     * <p>
     * (let ((name expression)* ) expression)
     *
     * @author hridesh
     */
    class LetExp extends Exp {
        final List<String> _names;
        final List<Exp> _value_exps;
        final Exp _body;

        public LetExp(List<String> names, List<Exp> value_exps, Exp body) {
            _names = names;
            _value_exps = value_exps;
            _body = body;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

        public List<String> names() {
            return _names;
        }

        public List<Exp> value_exps() {
            return _value_exps;
        }

        public Exp body() {
            return _body;
        }

    }

    class DefineDecl extends Exp {
        final String _name;
        final Exp _value_exp;

        public DefineDecl(String name, Exp value_exp) {
            _name = name;
            _value_exp = value_exp;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

        public String name() {
            return _name;
        }

        public Exp value_exp() {
            return _value_exp;
        }

    }

    class LambdaExp extends Exp {
        List<String> _formals;
        Exp _body;

        public LambdaExp(List<String> formals, Exp body) {
            _formals = formals;
            _body = body;
        }

        public List<String> formals() {
            return _formals;
        }

        public Exp body() {
            return _body;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class CallExp extends Exp {
        Exp _operator;
        List<Exp> _operands;

        public CallExp(Exp operator, List<Exp> operands) {
            _operator = operator;
            _operands = operands;
        }

        public Exp operator() {
            return _operator;
        }

        public List<Exp> operands() {
            return _operands;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class IfExp extends Exp {
        Exp _conditional;
        Exp _then_exp;
        Exp _else_exp;

        public IfExp(Exp conditional, Exp then_exp, Exp else_exp) {
            _conditional = conditional;
            _then_exp = then_exp;
            _else_exp = else_exp;
        }

        public Exp conditional() {
            return _conditional;
        }

        public Exp then_exp() {
            return _then_exp;
        }

        public Exp else_exp() {
            return _else_exp;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class LessExp extends BinaryComparator {
        public LessExp(Exp first_exp, Exp second_exp) {
            super(first_exp, second_exp);
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    abstract class BinaryComparator extends Exp {
        private final Exp _first_exp;
        private final Exp _second_exp;

        BinaryComparator(Exp first_exp, Exp second_exp) {
            _first_exp = first_exp;
            _second_exp = second_exp;
        }

        public Exp first_exp() {
            return _first_exp;
        }

        public Exp second_exp() {
            return _second_exp;
        }
    }

    class EqualExp extends BinaryComparator {
        public EqualExp(Exp first_exp, Exp second_exp) {
            super(first_exp, second_exp);
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class GreaterExp extends BinaryComparator {
        public GreaterExp(Exp first_exp, Exp second_exp) {
            super(first_exp, second_exp);
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class CarExp extends Exp {
        private final Exp _arg;

        public CarExp(Exp arg) {
            _arg = arg;
        }

        public Exp arg() {
            return _arg;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class CdrExp extends Exp {
        private final Exp _arg;

        public CdrExp(Exp arg) {
            _arg = arg;
        }

        public Exp arg() {
            return _arg;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class ConsExp extends Exp {
        private final Exp _fst;
        private final Exp _snd;

        public ConsExp(Exp fst, Exp snd) {
            _fst = fst;
            _snd = snd;
        }

        public Exp fst() {
            return _fst;
        }

        public Exp snd() {
            return _snd;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class ListExp extends Exp {
        private final List<Exp> _elems;

        public ListExp(List<Exp> elems) {
            _elems = elems;
        }

        public List<Exp> elems() {
            return _elems;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    class NullExp extends Exp {
        private final Exp _arg;

        public NullExp(Exp arg) {
            _arg = arg;
        }

        public Exp arg() {
            return _arg;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }
    }

    /**
     * A ref expression has the syntax
     * <p>
     * (ref expression)
     *
     * @author hridesh
     */
    class RefExp extends Exp {
        private final Exp _value_exp;

        public RefExp(Exp value_exp) {
            _value_exp = value_exp;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

        public Exp value_exp() {
            return _value_exp;
        }

    }

    /**
     * A deref expression has the syntax
     * <p>
     * (deref expression)
     *
     * @author hridesh
     */
    class DerefExp extends Exp {
        private final Exp _loc_exp;

        public DerefExp(Exp loc_exp) {
            _loc_exp = loc_exp;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

        public Exp loc_exp() {
            return _loc_exp;
        }

    }

    /**
     * An assign expression has the syntax
     * <p>
     * (set! expression expression)
     *
     * @author hridesh
     */
    class AssignExp extends Exp {
        private final Exp _lhs_exp;
        private final Exp _rhs_exp;

        public AssignExp(Exp lhs_exp, Exp rhs_exp) {
            _lhs_exp = lhs_exp;
            _rhs_exp = rhs_exp;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

        public Exp lhs_exp() {
            return _lhs_exp;
        }

        public Exp rhs_exp() {
            return _rhs_exp;
        }

    }

    /**
     * A free expression has the syntax
     * <p>
     * (free expression)
     *
     * @author hridesh
     */
    class FreeExp extends Exp {
        private final Exp _value_exp;

        public FreeExp(Exp value_exp) {
            _value_exp = value_exp;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

        public Exp value_exp() {
            return _value_exp;
        }

    }

    /**
     * A free expression has the syntax
     * <p>
     * (free expression)
     *
     * @author hridesh
     */
    class RefEqExp extends Exp {
        private final Exp exp1, exp2;

        public RefEqExp(Exp e1, Exp e2) {
            exp1 = e1;
            exp2 = e2;
        }

        public <T> T accept(Visitor<T> visitor, Env env) {
            return visitor.visit(this, env);
        }

        public Exp exp1() {
            return exp1;
        }        
        
        public Exp exp2() {
            return exp2;
        }
    }
}