package reflang;

import java.util.ArrayList;
import java.util.List;

/**
 * This class hierarchy represents expressions in the abstract syntax tree
 * manipulated by this interpreter.
 * 
 * @author hridesh
 * 
 */
@SuppressWarnings("rawtypes")
public interface AST {
	public static abstract class ASTNode implements AST {
		public abstract Object accept(Visitor visitor, Env env);
	}
	public static class Program extends ASTNode {
		private Exp _e;

		public Program(Exp e) {
			_e = e;
		}

		public Exp e() {
			return _e;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}
	public static abstract class Exp extends ASTNode {

	}

	public static class VarExp extends Exp {
		private String _name;

		public VarExp(String name) {
			_name = name;
		}

		public String name() {
			return _name;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class Const extends Exp {
		private int _val;

		public Const(int v) {
			_val = v;
		}

		public int v() {
			return _val;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static abstract class CompoundArithExp extends Exp {
		private List<Exp> _rest;

		public CompoundArithExp() {
			_rest = new ArrayList<Exp>();
		}

		public CompoundArithExp(Exp fst) {
			_rest = new ArrayList<Exp>();
			_rest.add(fst);
		}

		public CompoundArithExp(List<Exp> args) {
			_rest = new ArrayList<Exp>();
			for (Exp e : args)
				_rest.add((Exp) e);
		}

		public CompoundArithExp(Exp fst, List<Exp> rest) {
			_rest = new ArrayList<Exp>();
			_rest.add(fst);
			_rest.addAll(rest);
		}

		public CompoundArithExp(Exp fst, Exp second) {
			_rest = new ArrayList<Exp>();
			_rest.add(fst);
			_rest.add(second);
		}

		public Exp fst() {
			return _rest.get(0);
		}

		public Exp snd() {
			return _rest.get(1);
		}

		public List<Exp> all() {
			return _rest;
		}

		public void add(Exp e) {
			_rest.add(e);
		}
		
	}

	public static class AddExp extends CompoundArithExp {
		public AddExp(Exp fst) {
			super(fst);
		}

		public AddExp(List<Exp> args) {
			super(args);
		}

		public AddExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public AddExp(Exp left, Exp right) {
			super(left, right);
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class SubExp extends CompoundArithExp {

		public SubExp(Exp fst) {
			super(fst);
		}

		public SubExp(List<Exp> args) {
			super(args);
		}

		public SubExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public SubExp(Exp left, Exp right) {
			super(left, right);
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class DivExp extends CompoundArithExp {
		public DivExp(Exp fst) {
			super(fst);
		}

		public DivExp(List<Exp> args) {
			super(args);
		}

		public DivExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public DivExp(Exp left, Exp right) {
			super(left, right);
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class MultExp extends CompoundArithExp {
		public MultExp(Exp fst) {
			super(fst);
		}

		public MultExp(List<Exp> args) {
			super(args);
		}

		public MultExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public MultExp(Exp left, Exp right) {
			super(left, right);
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}
	
	/**
	 * A let expression has the syntax 
	 * 
	 *  (let ((name expression)* ) expression)
	 *  
	 * @author hridesh
	 *
	 */
	public static class LetExp extends Exp {
		private List<String> _names;
		private List<Exp> _value_exps; 
		private Exp _body;
		
		public LetExp(List<String> names, List<Exp> value_exps, Exp body) {
			_names = names;
			_value_exps = value_exps;
			_body = body;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
		
		public List<String> names() { return _names; }
		
		public List<Exp> value_exps() { return _value_exps; }

		public Exp body() { return _body; }

	}
	
	/**
	 * An anonymous procedure declaration has the syntax
	 * 
	 * @author hridesh
	 *
	 */
	public static class LambdaExp extends Exp {		
		private List<String> _formals;
		private Exp _body;
		
		public LambdaExp(List<String> formals, Exp body) {
			_formals = formals;
			_body = body;
		}
		
		public List<String> formals() { return _formals; }
		
		public Exp body() { return _body; }
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}
	
	/**
	 * A call expression has the syntax
	 * 
	 * @author hridesh
	 *
	 */
	public static class CallExp extends Exp {
		private Exp _operator; 
		private List<Exp> _operands;
		
		public CallExp(Exp operator, List<Exp> operands) {
			_operator = operator; 
			_operands = operands;
		}
		
		public Exp operator() { return _operator; }

		public List<Exp> operands() { return _operands; }
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	/**
	 * An if expression has the syntax
	 * 
	 * (if conditional_expression true_expression false_expression)
	 * 
	 * @author hridesh
	 *
	 */
	public static class IfExp extends Exp {
		Exp _conditional; 
		Exp _then_exp; 
		Exp _else_exp; 
		
		public IfExp(Exp conditional, Exp then_exp, Exp else_exp) {
			_conditional = conditional;
			_then_exp = then_exp; 
			_else_exp = else_exp; 
		}
		
		public Exp conditional() { return _conditional; }
		public Exp then_exp() { return _then_exp; }
		public Exp else_exp() { return _else_exp; }
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}
	
	/**
	 * A less expression has the syntax
	 * 
	 * ( < first_expression second_expression )
	 * 
	 * @author hridesh
	 *
	 */
	public static class LessExp extends BinaryComparator {
		public LessExp(Exp first_exp, Exp second_exp) {
			super(first_exp, second_exp);
		}
				
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}
	
	public static abstract class BinaryComparator extends Exp {
		private Exp _first_exp; 
		private Exp _second_exp; 
		BinaryComparator(Exp first_exp, Exp second_exp) {
			_first_exp = first_exp;
			_second_exp = second_exp; 
		}
		public Exp first_exp() { return _first_exp; }
		public Exp second_exp() { return _second_exp; }
	}

	/**
	 * An equal expression has the syntax
	 * 
	 * ( == first_expression second_expression )
	 * 
	 * @author hridesh
	 *
	 */
	public static class EqualExp extends BinaryComparator {
		public EqualExp(Exp first_exp, Exp second_exp) {
			super(first_exp, second_exp);
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	/**
	 * A greater expression has the syntax
	 * 
	 * ( > first_expression second_expression )
	 * 
	 * @author hridesh
	 *
	 */
	public static class GreaterExp extends BinaryComparator {
		public GreaterExp(Exp first_exp, Exp second_exp) {
			super(first_exp, second_exp);
		}
				
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	/**
	 * A letrec expression has the syntax 
	 * 
	 *  (letrec ((name expression)* ) expression)
	 *  
	 * @author hridesh
	 *
	 */
	public static class LetrecExp extends Exp {
		private List<String> _names;
		private List<Exp> _fun_exps; 
		private Exp _body;
		
		public LetrecExp(List<String> names, List<Exp> fun_exps, Exp body) {
			_names = names;
			_fun_exps = fun_exps;
			_body = body;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
		
		public List<String> names() { return _names; }
		
		public List<Exp> fun_exps() { return _fun_exps; }

		public Exp body() { return _body; }

	}

	/**
	 * A ref expression has the syntax 
	 * 
	 *  (ref expression)
	 *  
	 * @author hridesh
	 *
	 */
	public static class RefExp extends Exp {
		private Exp _value_exp;
		
		public RefExp(Exp value_exp) {
			_value_exp = value_exp;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
		
		public Exp value_exp() { return _value_exp; }

	}

	/**
	 * A deref expression has the syntax 
	 * 
	 *  (deref expression)
	 *  
	 * @author hridesh
	 *
	 */
	public static class DerefExp extends Exp {
		private Exp _loc_exp;
		
		public DerefExp(Exp loc_exp) {
			_loc_exp = loc_exp;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
		
		public Exp loc_exp() { return _loc_exp; }

	}

	/**
	 * An assign expression has the syntax 
	 * 
	 *  (set! expression expression)
	 *  
	 * @author hridesh
	 *
	 */
	public static class AssignExp extends Exp {
		private Exp _lhs_exp;
		private Exp _rhs_exp;
		
		public AssignExp(Exp lhs_exp, Exp rhs_exp) {
			_lhs_exp = lhs_exp;
			_rhs_exp = rhs_exp;
		}
		
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
		
		public Exp lhs_exp() { return _lhs_exp; }
		public Exp rhs_exp() { return _rhs_exp; }

	}

	public static class ErrorExp extends Exp {
		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}
	
	public interface Visitor <T> {
		// This interface should contain a signature for each concrete AST node.
		public T visit(AST.AddExp e, Env env);
		public T visit(AST.Const e, Env env);
		public T visit(AST.DivExp e, Env env);
		public T visit(AST.ErrorExp e, Env env);
		public T visit(AST.MultExp e, Env env);
		public T visit(AST.Program p, Env env);
		public T visit(AST.SubExp e, Env env);
		public T visit(AST.VarExp e, Env env);
		public T visit(AST.LetExp e, Env env); // New for the varlang
		public T visit(AST.LambdaExp e, Env env); // New for the funclang
		public T visit(AST.CallExp e, Env env); // New for the funclang
		public T visit(AST.IfExp e, Env env); // Additional expressions for convenience
		public T visit(AST.LessExp e, Env env); // Additional expressions for convenience
		public T visit(AST.EqualExp e, Env env); // Additional expressions for convenience
		public T visit(AST.GreaterExp e, Env env); // Additional expressions for convenience
		public T visit(AST.LetrecExp e, Env env); // New for the Reclang
		public T visit(AST.RefExp e, Env env); // New for the Reflang
		public T visit(AST.DerefExp e, Env env); // New for the Reflang
		public T visit(AST.AssignExp e, Env env); // New for the Reflang
	}	
}