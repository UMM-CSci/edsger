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
}