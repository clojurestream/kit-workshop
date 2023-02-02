# Kit Workshop

## Table of Contents

TODO

## Prerequisites

macOS or Linux recommended.

### Setup

Make sure you have the following dependencies installed:

- Clojure CLI https://clojure.org/guides/install_clojure
- Java 11+ (17 recommended)
- Calva, or editor of preference as long as nREPL compatible (Cursive, Emacs, etc.)

Clone this repository and make sure you can run the project by doing the following

```bash
git clone git@github.com:nikolap/kit-workshop.git
cd kit-workshop
clj -M:dev:nrepl
```

This should start up a REPL prompt, it might output something like:

```
nREPL server started on port 50354 on host localhost - nrepl://localhost:50354
nREPL 0.9.0
Clojure 1.11.1
OpenJDK 64-Bit Server VM 17.0.1+12-LTS
Interrupt: Control+C
Exit:      Control+D or (exit) or (quit)
```

Inside the prompt, test that you can start the application server by running:

```clojure
(go)
```

Then go to [http://localhost:3000/api/health](http://localhost:3000/api/health) to check that the server is running.

### Intro to Clojure

This workshop assumes a basic familiarity with Clojure and functional programming. If you are new to Clojure, we can recommend the following resources to get started:

- High level overview https://yogthos.github.io/ClojureDistilled.html
- An Animated Introduction to Clojure https://markm208.github.io/cljbook/
- Clojure from the Ground Up https://aphyr.com/tags/Clojure-from-the-ground-up
- Clojure for the Brave and True https://www.braveclojure.com/foreword/

Of course you are also welcome to search around and find other resources suited to your learning style or interests.

We will **not** be going over the basics of Clojure during the workshop.

### Conduct During the Workshop

Some general guidelines during the workshop

TODO

## Kit Workshop

### Creating a Project

Kit uses [clj-new](https://github.com/seancorfield/clj-new) to create projects from the template. If you don't already have it installed on your local machine, you can pull it in by running

```bash
clojure -Ttools install com.github.seancorfield/clj-new '{:git/tag "v1.2.381"}' :as new
```

Now we can create our new project by running. Feel free to replace `yourname` with your name too!

```bash
clojure -Tnew create :template io.github.kit-clj :name yourname/gif2html
cd gif2html
```

You should now have a project with the following folders

```
├── env
│   ├── dev
│   │   ├── clj
│   │   │   └── yourname
│   │   │       └── gif2html
│   │   └── resources
│   ├── prod
│   │   ├── clj
│   │   │   └── yourname
│   │   │       └── gif2html
│   │   └── resources
│   └── test
│       └── resources
├── log
├── resources
├── src
│   └── clj
│       └── yourname
│           └── gif2html
│               └── web
│                   ├── controllers
│                   ├── middleware
│                   └── routes
└── test
    └── clj
        └── yourname
            └── gif2html
```

TODO: explain folder structure

### kit.edn

TODO explain what this is

TODO: run REPL, connect to it in Calva

TODO: sync modules

TODO: run server, see that it works

### CHECKPOINT 1

At this point you should have your project setup, are able to run and connect to the REPL, and run the web server successfully.
