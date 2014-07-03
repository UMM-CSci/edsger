/**
 * Created by BlueFire on 6/21/14.
 */
var Sect = {};

function makeRules(arg){}

function changeSection(){
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
            var opt = new Option(rule + " " + sel[rule].name);
            ruleList.options.add(opt);
        }
    }
}