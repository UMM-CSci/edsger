/**
 * Created by mcphee on 2/25/14.
 */

function checkStep() {
    alert("Checking a step!");
    var first = document.getElementById("FirstExpression").value;
    var second = document.getElementById("SecondExpression").value;

    var firstParsed = parser.parse(first);
    console.log(JSON.stringify(firstParsed));
    var secondParsed = parser.parse(second);
    console.log(JSON.stringify(secondParsed));

    if (JSON.stringify(firstParsed) === JSON.stringify(secondParsed)) {
        alert("They're equal!");
    } else {
        alert("They're not equal!");
    }

    console.log(JSON.stringify(makeRules(2)["3.8"]));
}

var Sect = {};
Sect["Sect1"] = makeRules(1);
Sect["Sect2"] = makeRules(2);
Sect["Sect3"] = makeRules(3);
Sect["Sect4"] = makeRules(4);
Sect["Sect5"] = makeRules(5);
Sect["Sect6"] = makeRules(6);

function makeRules(arg) {
    var Sect1 = {
        '3.1' : {name : "Associativity of ===", equation : parser.parse("((p === q) === r) === (p === (q === r))")},
        '3.2' : {name : "Symmetry of ===", equation : parser.parse("p === q === q === p")},
        '3.3' : {name : "Identity of ===", equation : parser.parse("true === q === q")},
        '3.4' : {name : "", equation : parser.parse("true")},
        '3.5' : {name : "Reflexivity of ===", equation : parser.parse("p === p")}
        };

    var Sect2 = {
        '3.8' : {name : "Definition if false", equation : parser.parse("false === ! true")},
        '3.9' : {name : "Distribution of ! over ===", equation : parser.parse("! (p === q) === ! p === q")},
    //   '3.10' : {name : "Definition of !===", equation : parser.parse("(p !=== q) === ! (p == q)")},
        '3.11' : {name : "", equation : parser.parse("! p === q === p === ! q")},
    //    '3.12' : {name : "Double Negation", equation : parser.parse("!! p === p")},
        '3.13' : {name : "Negation of false", equation : parser.parse("! false === true")},
    //   '3.14' : {name : "", equation : parser.parse("(p !=== q) === ! p === q")},
        '3.15' : {name : "", equation : parser.parse("! p === p === false")}
    //  '3.16' : {name : "Symmetry of !===", equation : parser.parse("(p !=== q) === (q !=== p)")},
    //    '3.17' : {name : "Associativity of !===", equation : parser.parse("((p !=== q) !=== r) === (p !=== (q !=== r))")},
    //    '3.18' : {name : "Mutual associativity", equation : parser.parse("((p !=== q) === r) === (p !=== (q === r))")},
    //    '3.19' : {name : "Mutual interchangeability", equation : parser.parse("p !=== q === r ===  p === q !=== r")}
    };

    var Sect3 = {
        '3.24' : {name : "Symmetry of |", equation : parser.parse("p | q === q | p")},
        '3.25' : {name : "Associativity of |", equation : parser.parse("(p | q) | r === p | (q | r)")},
        '3.26' : {name : "Idempotency of |", equation : parser.parse("p | p === p")},
        '3.27' : {name : "Distributivity of | over ===", equation : parser.parse("p | (q === r) === p | q === p | r")},
        '3.28' : {name : "Excluded Middle", equation : parser.parse("p | ! p")},
        '3.29' : {name : "Zero of |", equation : parser.parse("p | true === true")},
        '3.30' : {name : "Identity of |", equation : parser.parse("p | false === p")},
        '3.31' : {name : "Distributivity of | over |", equation : parser.parse("p | (q | r) === (p | q) | (p | r)")},
        '3.32' : {name : "", equation : parser.parse("p | q === p | ! q === p")}
    };

    var Sect4 = {
        '3.35' : {name : "Golden rule", equation : parser.parse("p & q === p === q === p | q")},
        '3.36' : {name : "Symmetry of &", equation : parser.parse("p & q === q & p")},
        '3.37' : {name : "Associativity of &", equation : parser.parse("(p & q) & r === p & (q & r)")},
        '3.38' : {name : "Idempotency of &", equation : parser.parse("p & p === p")},
        '3.39' : {name : "Identity of &", equation : parser.parse("p & true === p")},
        '3.40' : {name : "Zero of &", equation : parser.parse("p & false === false")},
        '3.41' : {name : "Distributivity of & over &", equation : parser.parse("p & (q & r) === (p & q) & (p & r)")},
        '3.42' : {name : "Contradiction", equation : parser.parse("p & ! p === false")},
        '3.43a' : {name : "Absorption (a)", equation : parser.parse("p & (p | q) === p")},
        '3.43b' : {name : "Absorption (b)", equation : parser.parse("p | (p & q ) === p")},
        '3.44a' : {name : "Absorption (a)", equation : parser.parse("p & (! p | q) === p & q")},
        '3.44b' : {name : "Absorption (b)", equation : parser.parse("p | (! p & q) === p | q")},
        '3.45' : {name : "Distributivity of | over &", equation : parser.parse("p | (q & r) === (p | q) & (p | r)")},
        '3.46' : {name : "Distributivity of & over |", equation : parser.parse("p & (q | r) === (p &q) | (p & r)")},
        '3.47a' : {name : "De Morgan (a)", equation : parser.parse("! (p & q) === ! p | !q")},
        '3.47b' : {name : "De Morgan (b)", equation : parser.parse("! (p | q) === ! p & ! q")},
        '3.48' : {name : "", equation : parser.parse("p & q === p & ! q === ! p")},
        '3.49' : {name : "", equation : parser.parse("p & (q === r) === p & q === p & r === p")},
        '3.50' : {name : "", equation : parser.parse("p & (q === p) === p & q")},
        '3.51' : {name : "Replacement", equation : parser.parse("(p === q) & (r === p) === (p === q) & (r === q)")},
        '3.52' : {name : "Definition of ===", equation : parser.parse("p === q === (p & q) | (! p & ! q)")},
        '3.53' : {name : "Exclusive or", equation : parser.parse("p ! === q === (! p & q) | (p & ! q")},
        '3.55' : {name : "", equation : parser.parse("(p & q) & r === p === q === r === p | q === q | r === r | p === p | q | r")}
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
        '3.65' : {name : "", equation : parser.parse("")},
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
        '3.76a' : {name : "", equation : parser.parse("")},
        '3.76b' : {name : "", equation : parser.parse("")},
        '3.76c' : {name : "", equation : parser.parse("")},
        '3.76d' : {name : "", equation : parser.parse("")},
        '3.76e' : {name : "", equation : parser.parse("")},
        '3.77' : {name : "", equation : parser.parse("")},
        '3.78' : {name : "", equation : parser.parse("")},
        '3.79' : {name : "", equation : parser.parse("")},
        '3.80' : {name : "", equation : parser.parse("")},
        '3.81' : {name : "", equation : parser.parse("")},
        '3.82a' : {name : "", equation : parser.parse("")},
        '3.82b' : {name : "", equation : parser.parse("")},
        '3.82c' : {name : "", equation : parser.parse("")}
    };

    var Sect6 = {
        '3.83' : {name : "", equation : parser.parse("")},
        '3.84a' : {name : "", equation : parser.parse("")},
        '3.84b' : {name : "", equation : parser.parse("")},
        '3.84c' : {name : "", equation : parser.parse("")},
        '3.85' : {name : "", equation : parser.parse("")},
        '3.86' : {name : "", equation : parser.parse("")},
        '3.87' : {name : "", equation : parser.parse("")},
        '3.88' : {name : "", equation : parser.parse("")},
        '3.89' : {name : "", equation : parser.parse("")},
        '4.1' : {name : "", equation : parser.parse("")},
        '4.2' : {name : "", equation : parser.parse("")},
        '4.3' : {name : "", equation : parser.parse("")},
        '4.4' : {name : "", equation : parser.parse("")},
        '4.5' : {name : "", equation : parser.parse("")},
        '4.6' : {name : "", equation : parser.parse("")},
        '4.7' : {name : "", equation : parser.parse("")},
        '4.9' : {name : "", equation : parser.parse("")},
        '4.12' : {name : "", equation : parser.parse("")}
    };

    if (arg == 1) {return Sect1;}
    else if (arg == 2) {return Sect2;}
    else if (arg == 3) {return Sect3;}
    else if (arg == 4) {return Sect4;}
    else if (arg == 5) {return Sect5;}
    else if (arg == 6) {return Sect6;}
    return null;
}

function ChangeList() {
    var sectList = document.getElementById("section");
    var ruleList = document.getElementById("rule");
    var select = sectList.options[sectList.selectedIndex].value;

    while (ruleList.options.length) {
        ruleList.remove(0);
    }

    var sel = Sect[select];
    console.log(JSON.stringify(select));
    console.log(JSON.stringify(sel));
    if (sel) {
        ruleList.options.add(new Option("Rule"));
        for (var rule in sel) {
            var sect = new Option(rule + " " + sel[rule].name);
            ruleList.options.add(sect);
        }
    }
}