/**
 * Created by mcphee on 2/25/14.
 */

//Says unused function for checkStep(),
// however ProofChecker.html uses it in <form>
// inside <div class="content"> as <form action="javascript:checkStep()".
function checkStep() {
    alert('Checking a step!');
    var first = document.getElementById('FirstExpression').value;
    var second = document.getElementById('SecondExpression').value;

    var firstParsed = parser.parse(first);
    console.log(JSON.stringify(firstParsed));
    var secondParsed = parser.parse(second);
    console.log(JSON.stringify(secondParsed));

    if (JSON.stringify(firstParsed) === JSON.stringify(secondParsed)) {
        alert('Theyre equal!');
    } else {
        alert('Theyre not equal!');
    }
}

var Book = {};
Book['Book1'] = makeSections(1);
Book['Book2'] = makeSections(2);

function makeSections(arg) {
    var Book1 = {
        'Sect1' : {name : 'Equivalence and True', value : 'Sect1'},
        'Sect2' : {name : 'Negation, Inequivalence, and False', value : 'Sect2'},
        'Sect3' : {name : 'Disjunction', value : 'Sect3'},
        'Sect4' : {name : 'Conjunction', value : 'Sect4'},
        'Sect5' : {name : 'Implication', value : 'Sect5'},
        'Sect6' : {name : 'Leibniz as an Axiom', value : 'Sect6'},
        'Sect7' : {name : 'Proof Techniques', value : 'Sect7'},
        'Sect8' : {name : 'General Laws of Quantification', value : 'Sect8'},
        'Sect9' : {name : 'Universal Quantification', value : 'Sect9'},
        'Sect10' : {name : 'Existential Quantification', value : 'Sect10'}
    };

    var Book2 = {};

    if (arg == 1) {return Book1;}
    else if (arg == 2) {return Book2;}
    return null;
}

function ChangeBook() {
    var bookList = document.getElementById('book');
    var sectList = document.getElementById('section');
    var select = bookList.options[bookList.selectedIndex].value;

    while (sectList.options.length) {
        sectList.remove(0);
    }

    var sel = Book[select];
    console.log(JSON.stringify(select));
    console.log(JSON.stringify(sel));
    if (sel) {
        sectList.options.add(new Option('Section'));
        for (var sect in sel) {
            if (sel.hasOwnProperty(sect)) {
                var opt = new Option(sel[sect].name,sect);
                sectList.options.add(opt);
            }
        }
    }
}

function ChangeSection() {
    var sectList = document.getElementById('section');
    var ruleList = document.getElementById('rule');
    var select = sectList.options[sectList.selectedIndex].value;

    while (ruleList.options.length) {
        ruleList.remove(0);
    }

    console.log(JSON.stringify(Sect['Sect2']));
    var sel = Sect[select];
    console.log(JSON.stringify(select));
    console.log(JSON.stringify(sel));
    if (sel) {
        ruleList.options.add(new Option('Rule'));
        for (var rule in sel) {
            if (sel.hasOwnProperty(rule)) {
                var opt = new Option(sel[rule].name,rule);
                ruleList.options.add(opt);
            }
        }
    }
}

var hist = {first: "", second: "", rule: "", sub: ""};

function History() {
    var first = document.getElementById('FirstExpression').value;
    var second = document.getElementById('SecondExpression').value;
    var rule = document.getElementById('rule').selectedIndex.value;
    var sub = document.getElementById('extra').value;

    if (first === "" || second === "") {
        alert("First and/or second expression has to have an input.");
    }
    else {
        hist.push({first:first, second:second, rule:"rule", sub:"sub"});
    }

    for (var i=0; i < hist.length; i++) {
        if (hist.hasOwnProperty(h)) {
            var fst = hist[i].first + " ";
            var sec = hist[i].second + " ";
            var rl = hist[i].rule;
            var sb = hist[i].sub + " ";

            document.getElementById('history').value = fst + rl + sb + sec;
        }
    }
}

function addJavascript(jsname,pos) {
    var th = document.getElementsByTagName(pos)[0];
    var s = document.createElement('script');
    s.setAttribute('type','text/javascript');
    s.setAttribute('src',jsname);
    th.appendChild(s);
}

addJavascript('../app/scripts/Book1.js','head');
addJavascript('../app/scripts/Book2.js','head');