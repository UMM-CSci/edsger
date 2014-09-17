/**
 * Created by mcphee on 2/25/14.
 */

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
            var opt = new Option(sel[sect].name,sect, false, false);
            sectList.options.add(opt);
        }
    }
}

function History() {
    var NewFirst = document.getElementById('FirstExpression').value;
    var NewSecond = document.getElementById('SecondExpression').value;
    var OldSecond = null;   //always null at the moment

    var recent = NewFirst + NewSecond;
    var old = document.getElementById('history').value;

    if (NewFirst === OldSecond || OldSecond === null)
        {document.getElementById('history').value = old + recent;}
    else
        {document.getElementById('history').value = null;}
}

function addJavascript(jsname,pos) {
    var th = document.getElementsByTagName(pos)[0];
    var s = document.createElement('script');
    s.setAttribute('type','text/javascript');
    s.setAttribute('src',jsname);
    th.appendChild(s);
}

addJavascript('../js/Book1.js','head');
addJavascript('../js/Book2.js','head');