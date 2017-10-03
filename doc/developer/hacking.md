# Hacking

This document should contain various hints and tips for working on the project.

## Using figwheel with Spacemacs

If you've used CIDER with Spacemacs or vanilla Emacs, you know how nice it is to
be able to evaluate snippets of code directly from the editor. The basic idea is
that CIDER creates a connection to a REPL to which it can send forms and receive
the value that it evaluates to.

This project is a Clojurescript project using Figwheel. Figwheel is designed to
be running the whole time you are working on the project and provides a few
things for us. First, it watches all the files in the project and rebuilds
whenever the sources change. Second, it runs a server hosting the project so
that you can view the project in a browser. Finally, Figwheel maintains a
connection to the browser and provides a REPL in which you can type code, have
it compiled to JavaScript, have the JavaScript run in the browser, and have the
result returned to the REPL.

It would be nice if we could have Spacemacs send Clojurescript to the Figwheel
REPL just as easily as we can send Clojure to a JVM based REPL. Here is how to
do it:

1. Make sure that the `clojure` layer is included in your
   `dotspacemacs-configuration-layers` in your Spacemacs config file.

2. Open up one of the `.cljs` files in the project.

3. Launch a normal REPL with `SPC m s i`.

4. In the REPL, run the following commands in order.
```
(use 'figwheel-sidecar.repl-api)
(start-figwheel!)
(cljs-repl)
```

The first call imports the necessary functions. The second call launches
Figwheel and then returns. The third one launches a REPL that we can put
Clojurescript in, just like the Figwheel REPL.

Back in the `.cljs`, you can now use shortcuts like `, e f` to evaluate code
directly in that file!
