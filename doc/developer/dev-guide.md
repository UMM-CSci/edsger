# Hitchhiker's Guide to Edsger Development 

Edsger is a front-end only application fully written in ClojureScript. It is currently hosted in GitHub pages. The following are the structure of the project:

### Build & Testing
* `leiningen`: build & dependency management system.
* `doo, karma`: test runner.
* `clojure.test`: testing framework.
* `Travis CI`: CI & build system. When the unit tests passed in the `master` branch, `Travis CI` automatically pushes the changes to the branch for deployment.
* `Figwheel`: used for live code reloading.

### Current Project Set-up
* ClojureScript is the main development language. We also use [`Google Closure`](https://github.com/google/closure-library) for dom manipulation.
* `edsger.core` is the controller containing all input handlers.
  The `validate-handler` function is the only place that the frontend calls out to code in our other namespaces.
* `edsger.parsing` is a namespace for parsing user input strings into ClojureScript data structures using the [`instaparse`](https://github.com/Engelberg/instaparse) library.
  The function `check-match-recursive` is currently the only one intended for use outside the namespace.
* `edsger.unification` is a namespace containing validation functions using [`core.logic`](https://github.com/clojure/core.logic) library.
   The functions `parse` and `rulify` are the only ones intended for use outside the namespace.

### Known Issues & TODOs
* #39 Currently, the cursor shows unexpected behavior. This is because we manually reset the cursor location after we replace symbols in the input box.
* #36 An easy way to type rules is desirable. (e.g. rule dropdown)
* #32, #33 More helpful error messages are needed when parsing or evaluation fails.
