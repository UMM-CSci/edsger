# Hitchhiker's Guide to Edsger Development

Edsger is a front-end only application fully written in ClojureScript. It is currently hosted in GitHub pages. The following are the structure of the project:

### Build & Testing
* leiningen: build & dependency management system.
* doo, karma: test runner.
* clojure.test: clojure's default testing framework
* Travis CI: CI & build system. When the unit tests passed in the `master` branch, Travis CI automatically pushes the changes to the branch for deployment.
* Figwheel: used for live code reloading.

### Current Project Set-up
* ClojureScript is the main development language.
* `edsger.core` is the controller containing all input handlers.
* `edsger.parsing` is a model for paring user input strings into ClojureScript data structures using [`instaparser`](https://github.com/Engelberg/instaparse) library.
* `edsger.unification` is a model containing validation functions using [`core.logic`](https://github.com/clojure/core.logic) library.
