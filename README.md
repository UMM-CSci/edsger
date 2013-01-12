edsger
======

A simple proof checker for use in introductory logic courses.

This is currently in a _very_ early state; I basically have the parser and substitution 
working, but there's no actual checking yet.

The idea is to provide a simple checker for basic logic proofs that students generate in the sort of discrete math/
logic course that is often required in computer science programs. My experience teaching such courses is that students
often struggle with the basic question of "is this correct?" in a ways that don't come up in an intro programming
course _because there the compiler/interpreter gives them hard feedback_. So while students in an intro programming
course are forced to face confusion about things like syntax right away, in our logic/discrete course students can
flail on basic syntactic issues for weeks, and are often very frustrated because they just don't know if "they've 
done it right".

My goal here is to write a web-based JavaScript tool that will allow students to check the correctness of each step
in their proofs, at least for simple propositional calculus. It's likely to be _very_ simple, at least until there's
some evidence that the students find it useful; for the moment I'm ignoring things like automatic simplification 
of commutative and associative operations and predicate calculus.

I'm basing the syntax and proof styles on the material in the textbook I use, _A logical approach to discrete math_
by David Gries and Fred Schneider. The name of the project is in honor of Edsger Dijkstra, who did much to develop
and popularize the notations and proof styles used here, and had a profound influence on me. That said, I'm not at
all sure that Edsger would approve of this – he'd probably be horrified that I'm helping/encouraging students to
use a tool like this as a crutch instead of simply coming to grips with the (fairly simple) rules involved.

Oh, well.

I'm going to try to build this thing anyway and see what happens.
