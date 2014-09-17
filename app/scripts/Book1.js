/**
 * Created by BlueFire on 6/21/14.
 */
var Sect = {};
Sect['Sect1'] = makeRules(1);
Sect['Sect2'] = makeRules(2);
Sect['Sect3'] = makeRules(3);
Sect['Sect4'] = makeRules(4);
Sect['Sect5'] = makeRules(5);
Sect['Sect6'] = makeRules(6);
Sect['Sect7'] = makeRules(7);
Sect['Sect8'] = makeRules(8);
Sect['Sect9'] = makeRules(9);
Sect['Sect10'] = makeRules(10);

function makeRules(arg) {
    var Sect1 = {
        '3.1' : {name : 'Associativity of ===', equation : parser.parse('((p === q) === r) === (p === (q === r))')},
        '3.2' : {name : 'Symmetry of ===', equation : parser.parse('p === q === q === p')},
        '3.3' : {name : 'Identity of ===', equation : parser.parse('true === q === q')},
        '3.4' : {name : '', equation : parser.parse('true')},
        '3.5' : {name : 'Reflexivity of ===', equation : parser.parse('p === p')}
    };

    var Sect2 = {
        '3.8' : {name : 'Definition if false', equation : parser.parse('false === ! true')},
        '3.9' : {name : 'Distribution of ! over ===', equation : parser.parse('! (p === q) === ! p === q')},
        //   '3.10' : {name : 'Definition of !===', equation : parser.parse('(p !=== q) === ! (p == q)')},
        '3.11' : {name : '', equation : parser.parse('! p === q === p === ! q')},
        //    '3.12' : {name : 'Double Negation', equation : parser.parse('!! p === p')},
        '3.13' : {name : 'Negation of false', equation : parser.parse('! false === true')},
        //   '3.14' : {name : '', equation : parser.parse('(p !=== q) === ! p === q')},
        '3.15' : {name : '', equation : parser.parse('! p === p === false')}
        //  '3.16' : {name : 'Symmetry of !===', equation : parser.parse('(p !=== q) === (q !=== p)')},
        //    '3.17' : {name : 'Associativity of !===', equation : parser.parse('((p !=== q) !=== r) === (p !=== (q !=== r))")},
        //    '3.18' : {name : 'Mutual associativity', equation : parser.parse('((p !=== q) === r) === (p !=== (q === r))')},
        //    '3.19' : {name : 'Mutual interchangeability', equation : parser.parse('p !=== q === r ===  p === q !=== r')}
    };

    var Sect3 = {
        '3.24' : {name : 'Symmetry of |', equation : parser.parse('p | q === q | p')},
        '3.25' : {name : 'Associativity of |', equation : parser.parse('(p | q) | r === p | (q | r)')},
        '3.26' : {name : 'Idempotency of |', equation : parser.parse('p | p === p')},
        '3.27' : {name : 'Distributivity of | over ===', equation : parser.parse('p | (q === r) === p | q === p | r')},
        '3.28' : {name : 'Excluded Middle', equation : parser.parse('p | ! p')},
        '3.29' : {name : 'Zero of |', equation : parser.parse('p | true === true')},
        '3.30' : {name : 'Identity of |', equation : parser.parse('p | false === p')},
        '3.31' : {name : 'Distributivity of | over |', equation : parser.parse('p | (q | r) === (p | q) | (p | r)')},
        '3.32' : {name : '', equation : parser.parse('p | q === p | ! q === p')}
    };

    var Sect4 = {
        '3.35' : {name : 'Golden rule', equation : parser.parse('p & q === p === q === p | q')},
        '3.36' : {name : 'Symmetry of &', equation : parser.parse('p & q === q & p')},
        '3.37' : {name : 'Associativity of &', equation : parser.parse('(p & q) & r === p & (q & r)')},
        '3.38' : {name : 'Idempotency of &', equation : parser.parse('p & p === p')},
        '3.39' : {name : 'Identity of &', equation : parser.parse('p & true === p')},
        '3.40' : {name : 'Zero of &', equation : parser.parse('p & false === false')},
        '3.41' : {name : 'Distributivity of & over &', equation : parser.parse('p & (q & r) === (p & q) & (p & r)')},
        '3.42' : {name : 'Contradiction', equation : parser.parse('p & ! p === false')},
        '3.43a' : {name : 'Absorption (a)', equation : parser.parse('p & (p | q) === p')},
        '3.43b' : {name : 'Absorption (b)', equation : parser.parse('p | (p & q ) === p')},
        '3.44a' : {name : 'Absorption (a)', equation : parser.parse('p & (! p | q) === p & q')},
        '3.44b' : {name : 'Absorption (b)', equation : parser.parse('p | (! p & q) === p | q')},
        '3.45' : {name : 'Distributivity of | over &', equation : parser.parse('p | (q & r) === (p | q) & (p | r)')},
        '3.46' : {name : 'Distributivity of & over |', equation : parser.parse('p & (q | r) === (p &q) | (p & r)')},
        '3.47a' : {name : 'De Morgan (a)', equation : parser.parse('! (p & q) === ! p | !q')},
        '3.47b' : {name : 'De Morgan (b)', equation : parser.parse('! (p | q) === ! p & ! q')},
        '3.48' : {name : '', equation : parser.parse('p & q === p & ! q === ! p')},
        '3.49' : {name : '', equation : parser.parse('p & (q === r) === p & q === p & r === p')},
        '3.50' : {name : '', equation : parser.parse('p & (q === p) === p & q')},
        '3.51' : {name : 'Replacement', equation : parser.parse('(p === q) & (r === p) === (p === q) & (r === q)')},
        '3.52' : {name : 'Definition of ===', equation : parser.parse('p === q === (p & q) | (! p & ! q)')},
        '3.53' : {name : 'Exclusive or', equation : parser.parse('p ! === q === (! p & q) | (p & ! q')},
        '3.55' : {name : '', equation : parser.parse('(p & q) & r === p === q === r === p | q === q | r === r | p === p | q | r')}
    };

    var Sect5 = {
        '3.57' : {name : "Definition of Implication", equation : parser.parse("")},
        '3.58' : {name : "Consequence", equation : parser.parse("")},
        '3.59' : {name : "Definition of Implication", equation : parser.parse("")},
        '3.60' : {name : "Definition of implication", equation : parser.parse("")},
        '3.61' : {name : "Contrapositive", equation : parser.parse("")},
        '3.62' : {name : "", equation : parser.parse("")},
        '3.63' : {name : "", equation : parser.parse("")},
        '3.64' : {name : "", equation : parser.parse("")},
        '3.65' : {name : "Shunting", equation : parser.parse("")},
        '3.66' : {name : "", equation : parser.parse("")},
        '3.67' : {name : "", equation : parser.parse("")},
        '3.68' : {name : "", equation : parser.parse("")},
        '3.69' : {name : "", equation : parser.parse("")},
        '3.70' : {name : "", equation : parser.parse("")},
        '3.71' : {name : "", equation : parser.parse("")},
        '3.72' : {name : "", equation : parser.parse("")},
        '3.73' : {name : "", equation : parser.parse("")},
        '3.74' : {name : "", equation : parser.parse("")},
        '3.75' : {name : "", equation : parser.parse("")},
        '3.76a' : {name : "Weakening/strengthening (a)", equation : parser.parse("")},
        '3.76b' : {name : "Weakening/strengthening (b)", equation : parser.parse("")},
        '3.76c' : {name : "Weakening/strengthening (c)", equation : parser.parse("")},
        '3.76d' : {name : "Weakening/strengthening (d)", equation : parser.parse("")},
        '3.76e' : {name : "Weakening/strengthening (e)", equation : parser.parse("")},
        '3.77' : {name : "Modus ponens", equation : parser.parse("")},
        '3.78' : {name : "", equation : parser.parse("")},
        '3.79' : {name : "", equation : parser.parse("")},
        '3.80' : {name : "Mutual implication", equation : parser.parse("")},
        '3.81' : {name : "Antisymmetry", equation : parser.parse("")},
        '3.82a' : {name : "Transitivity (a)", equation : parser.parse("")},
        '3.82b' : {name : "Transitivity (b)", equation : parser.parse("")},
        '3.82c' : {name : "Transitivity (c)", equation : parser.parse("")}
    };

    var Sect6 = {
        '3.83' : {name : "Leibniz", equation : parser.parse("")},
        '3.84a' : {name : "Substitution (a)", equation : parser.parse("")},
        '3.84b' : {name : "Substitution (b)", equation : parser.parse("")},
        '3.84c' : {name : "Substitution (c)", equation : parser.parse("")},
        '3.85a' : {name : "Replace by true (a)", equation : parser.parse("")},
        '3.85b' : {name : "Replace by true (b)", equation : parser.parse("")},
        '3.86a' : {name : "Replace by false (a)", equation : parser.parse("")},
        '3.86b' : {name : "Replace by false (b)", equation : parser.parse("")},
        '3.87' : {name : "Replace by true", equation : parser.parse("")},
        '3.88' : {name : "Replace by false", equation : parser.parse("")},
        '3.89' : {name : "Shannon", equation : parser.parse("")},
        '4.1' : {name : "", equation : parser.parse("")},
        '4.2' : {name : "Monotonicity of |", equation : parser.parse("")},
        '4.3' : {name : "Monotonicity of &", equation : parser.parse("")}
    };

    var Sect7 = {
        '4.4' : {name : "Deduction", equation : parser.parse("")},
        '4.5' : {name : "Case analysis", equation : parser.parse("")},
        '4.6' : {name : "Case analysis", equation : parser.parse("")},
        '4.7' : {name : "Mutual implication", equation : parser.parse("")},
        '4.9' : {name : "Proof by contradiction", equation : parser.parse("")},
        '4.12' : {name : "Proof by contrapositive", equation : parser.parse("")}
    };

    var Sect8 = {
        '8.13' : {name : "Empty range", equation : parser.parse("")},
        '8.14' : {name : "One-point rule", equation : parser.parse("")},
        '8.15' : {name : "Distributivity", equation : parser.parse("")},
        '8.16' : {name : "Range split", equation : parser.parse("")},
        '8.17' : {name : "Range split", equation : parser.parse("")},
        '8.18' : {name : "Range split for idempotent", equation : parser.parse("")},
        '8.19' : {name : "Interchange of dummies", equation : parser.parse("")},
        '8.20' : {name : "Nesting", equation : parser.parse("")},
        '8.21' : {name : "Dummy renaming", equation : parser.parse("")},
        '8.22' : {name : "Change of dummy", equation : parser.parse("")},
        '8.23' : {name : "Split off term", equation : parser.parse("")}
    };

    var Sect9 = {
        '9.2' : {name : "Trading", equation : parser.parse("")},
        '9.3a' : {name : "Trading (a)", equation : parser.parse("")},
        '9.3b' : {name : "Trading (b)", equation : parser.parse("")},
        '9.3c' : {name : "Trading (c)", equation : parser.parse("")},
        '9.4a' : {name : "Trading (a)", equation : parser.parse("")},
        '9.4b' : {name : "Trading (b)", equation : parser.parse("")},
        '9.4c' : {name : "Trading (c)", equation : parser.parse("")},
        '9.4d' : {name : "Trading (d)", equation : parser.parse("")},
        '9.5' : {name : "", equation : parser.parse("")},
        '9.6' : {name : "", equation : parser.parse("")},
        '9.7' : {name : "", equation : parser.parse("")},
        '9.8' : {name : "", equation : parser.parse("")},
        '9.9' : {name : "", equation : parser.parse("")},
        '9.10' : {name : "Range weakening/strengthening", equation : parser.parse("")},
        '9.11' : {name : "Body weakening/strengthening", equation : parser.parse("")},
        '9.12' : {name : "", equation : parser.parse("")},
        '9.13' : {name : "Instantiation", equation : parser.parse("")},
        '9.16' : {name : "", equation : parser.parse("")}
    };

    var Sect10 = {
        '9.17' : {name : "Generalized De Morgan", equation : parser.parse("")},
        '9.18a' : {name : "Generalized De Morgan (a)", equation : parser.parse("")},
        '9.18b' : {name : "Generalized De Morgan (b)", equation : parser.parse("")},
        '9.18c' : {name : "Generalized De Morgan (c)", equation : parser.parse("")},
        '9.19' : {name : "Trading", equation : parser.parse("")},
        '9.20' : {name : "Trading", equation : parser.parse("")},
        '9.21' : {name : "", equation : parser.parse("")},
        '9.22' : {name : "", equation : parser.parse("")},
        '9.23' : {name : "", equation : parser.parse("")},
        '9.24' : {name : "", equation : parser.parse("")},
        '9.25' : {name : "Range weakening/strengthening", equation : parser.parse("")},
        '9.26' : {name : "Body weakening/strengthening", equation : parser.parse("")},
        '9.27' : {name : "", equation : parser.parse("")},
        '9.28' : {name : "", equation : parser.parse("")},
        '9.29' : {name : "Interchange of quantifications", equation : parser.parse("")},
        '9.30' : {name : "", equation : parser.parse("")}
    };

    if (arg == 1) {return Sect1;}
    else if (arg == 2) {return Sect2;}
    else if (arg == 3) {return Sect3;}
    else if (arg == 4) {return Sect4;}
    else if (arg == 5) {return Sect5;}
    else if (arg == 6) {return Sect6;}
    else if (arg == 7) {return Sect7;}
    else if (arg == 8) {return Sect8;}
    else if (arg == 9) {return Sect9;}
    else if (arg == 10) {return Sect10;}
    return null;
}