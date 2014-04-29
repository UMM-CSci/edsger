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

    console.log(JSON.stringify(makeRules()["3.8"]))
}

var Sect = {};
Sect['Equivalence and True'] = makeRules();

function makeRules() {
    var Sect1 = {
        "3.8" : parser.parse("false === ! true"),
        "3.9" : parser.parse("! (p === q) === ! p === q"),
        "3.10" : parser.parse("(p !=== q) === ! (p == q)"),
        "3.11" : parser.parse("! p === q === p === ! q"),
        "3.12" : parser.parse("!! p === p"),
        "3.13" : parser.parse("! false === true"),
        "3.14" : parser.parse("(p !=== q) === ! p === q"),
        "3.15" : parser.parse("! p === p === false"),
        "3.16" : parser.parse("(p !=== q) === (q !=== p)")
    };
    return Sect1
}

function ChangeList() {
    var sectList = document.getElementById("section");
    var ruleList = document.getElementById("rule");
    var select = sectList.options[sectList.selectedIndex].value;

    while (ruleList.options.length) {
        ruleList.remove(0);
    }

    var sel = Sect[select];
    if (sel) {
        for (var i=0; i<sel.length; i++) {
            var sect = new Option(sel[i],i);
            ruleList.options.add(sect);
        }
    }
}