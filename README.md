edsger: A simple proof checker for introductory logic
=====================================================

A simple proof checker for use in introductory logic courses.

The idea is to provide a simple checker for basic logic proofs that students
generate in the sort of discrete math/ logic course that is often required in
computer science programs. In these courses, students often struggle with the
basic question of "is this correct?" in a ways that don't come up in an intro
programming course _because there the compiler/interpreter gives them hard
feedback_. So while students in an intro programming course are forced to face
confusion about things like syntax right away, in our logic/discrete course
students can flail on basic syntactic issues for weeks, and are often very
frustrated because they just don't know if "they've done it right".

Our goal here is to write a web-based ClojureScript tool that will allow
students to check the correctness of each step in their proofs, at least for
simple propositional calculus. It's likely to be _very_ simple, at least until
there's some evidence that the students find it useful.
