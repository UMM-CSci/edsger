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
        '3.44b' : {name : "Absorption (b)", equation : parser.parse("p | (! p & q) === p | q")}
    };

    var Sect5 = {};

    if (arg == 1) {return Sect1;}
    else if (arg == 2) {return Sect2;}
    else if (arg == 3) {return Sect3;}
    else if (arg == 4) {return Sect4;}
    else if (arg == 5) {return Sect5;}
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