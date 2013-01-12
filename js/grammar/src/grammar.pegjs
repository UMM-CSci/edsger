/*
 * Grammar for simple logical expressions using the precedence rules
 * from _A logical approach to discrete math_ by Gries & Schneider.
 * I'm not currently using "right" symbols for the operators since they're
 * primarily not in the ASCII set and thus not easily type-able.
 * 
 * This grammar is intended to be used by PEG.js (http://pegjs.majda.cz/online)
 *
 * Nic McPhee, Jan 2013
 */

// An example of a project using PEG.js so I can see how they did t
// he file layout: https://github.com/versatica/JsSIP

{
	var expr = function(spec) {
		var that = {};
		
		that.applySubstitutions = function(substitutions) {
			result = this;
			for (var i=0; i<substitutions.length; ++i) {
				result = result.applySubstitution(substitutions[i]);
			}
			return result;
		}
		
		return that;
	}

	var variable = function(spec) {
		var that = expr(spec);
		
		that.name = spec.name;
		
		// A substitution is a list of variables paired with a list of expressions.
		that.applySubstitution = function(substitution) {
			for (var i=0; i<substitution.varsToReplace.length; i+=1) {
				if (this.name === substitution.varsToReplace[i].name) {
					return substitution.replacementExprs[i];
				}
			}
			return this;
		}
		
		return that;
	}
	
	var unary = function(spec) {
		var that = expr(spec);
		
		that.op = spec.op;
		that.expr = spec.expr
		
		that.applySubstitution = function(substitution) {
			return unary({op : this.op, expr : this.expr.applySubstitution(substitution)});
		}
		
		return that;
	}

	var binary = function(spec) {
		var that = expr(spec);
		
		that.op = spec.op;
		that.left = spec.left;
		that.right = spec.right;
		
		that.applySubstitution = function(substitution) {
			return binary(
				{ op : this.op, 
				  left : this.left.applySubstitution(substitution),
				  right : this.right.applySubstitution(substitution)});
		}
		
		return that;
	}
	
	var normalizeOp = function(op) {
		if (op === "&") {
			return "∧";
		} else if (op === "|") {
			return "∨";
		} else if (op === "!") {
			return "¬";
		} else {
			return op;
		}
	}
	
	var makeList = function(items) {
		var result = [items[0]];
		if (items.length > 1) {
			var others = items[1];
			for (var i=0; i<others.length; i+=1) {
				result.push(others[i][1]);
			}
		}
		return result;
	}
}

expr
  = _ equiv:equiv _ { return equiv; }

_ "whitespace"
  = [ \t\r\n]*

equiv "expression"
  = left:andOrOr _ equivOp _ right:expr 
	{ return binary({ op : "≡", left : left, right : right }); }
  / andOrOr

equivOp = "===" / "≡"

andOrOr "AND/OR"
  = left:unary _ binOp:[|&∧∨] _ right:andOrOr 
	{ return binary({ op : normalizeOp(binOp), left : left, right : right }); }
  / unary

unary
  = op:[¬!] _ e:primary { return unary({ op : normalizeOp(op), expr : e }); }
  / substitution

substitution "substitution"
  = expr:primary _ subst:substExpr*
      { return expr.applySubstitutions(subst); }
  / primary

substExpr
  = "[" _ replacedVars:(variable((_","_)variable)*) _ ":=" _ replacements:(expr((_","_)expr)*) _ "]" _
      { return { "varsToReplace" : makeList(replacedVars), "replacementExprs" : makeList(replacements) }; }

primary
  = variable
  / integer
  / "(" e:expr ")" { return e; }

variable "variable"
  = chars:([A-Za-z][A-Za-z0-9_]*) { return variable({name : chars[0] + chars[1].join("")}); }

integer "integer"
  = digits:[0-9]+ { return parseInt(digits.join(""), 10); }

// (p|(!p&q))===(p|q)
// p|!p&q===p|q
// (p&q)[p:=z|w]
// (x & (!y | x))[ x,y := a,b ] [b,a:=x,y]
// (p|!p&q===p|q)[p:=r]
// (p ∨ (¬p ∧ q) ≡ p ∨ q)[p := r]