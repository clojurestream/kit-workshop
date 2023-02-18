# Kit Workshop

## Table of Contents

* [Prerequisites](#prerequisites)
  * [Setup](#setup)
  * [Intro to Clojure](#intro-to-clojure)
  * [Conduct During the Workshop](#conduct-during-the-workshop)
* [Kit Workshop](#kit-workshop-1)
  * [Creating a Project](#creating-a-project)
  * [kit.edn](#kitedn)
  * [Starting the REPL](#starting-the-repl)
  * [Using Modules](#using-modules)
  * [CHECKPOINT 1](#checkpoint-1)

## Prerequisites

macOS or Linux recommended.

### Setup

Make sure you have the following dependencies installed:

- Clojure CLI https://clojure.org/guides/install_clojure
- Java 11+ (17 recommended)
- Calva (+ clj-kondo extension), or editor of preference as long as nREPL compatible (Cursive, Emacs, etc.)

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

- By attentding this workshop, you agree to the [Code of Conduct](/CONDUCT.md)
- If you get stuck, questions are encouraged
- Checkpoints and branches are available to make sure everyone's on the same page

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

Let's take a look at what these folders are and their purpose.

* `env` - This folder contains environment dependent code.
  * `dev` - The code in this folder will only be run during development.
  * `prod` - The code in this folder will be compiled into the uberjar when the application is packaged for deployment. 
* `resources` - This folder contains static assets such as configuration files, HTML templates, and so on.
* `src/clj` - This folder contains the application code.
  * `controllers` - This package contains namespaces that handle your application business logic.
  * `middleware` - This package contains Ring routing middleware that encapsulates cross-cutting logic shared across the routes.
  * `routes` - This package is where server endpoints are defined.
* `test` - This folder contains the tests.

### kit.edn

Kit uses a module system that allows adding new functionality to existing Kit projects by installing modules from the REPL.
This file contains metadata about the project and referenes to module repositories that will be used to add new modules in the project.

Kit modules are templates that get injected in the project and generate code within exisitng project files. The metadata in `kit.edn` is
used to specify the paths and namespaces for the generated code.

### Starting the REPL

The REPL can be started by running the following command from the project folder:

    clj -M:dev:nrepl

Once the REPL starts you should see the following in the terminal, note that the PORT is selected at random:

```
nREPL server started on port 65110 on host localhost - nrepl://localhost:65110
nREPL 0.9.0
Clojure 1.11.1
OpenJDK 64-Bit Server VM 17.0.1+12-39
Interrupt: Control+C
Exit:      Control+D or (exit) or (quit)
user=>
```

Once you see the prompt, you can connect your editor to the REPL. We'll go through connecting Calva, but other editors should work similarly.

* Click on the `REPL` button at the bottom left.
* Select `Connect to a running REPL in your project`
* Select `deps.edn`
* Press `enter`, correct port should be detected automatically.

If everything went well then you should see the following prompt:

```clojure
; Connecting ...
; Hooking up nREPL sessions...
; Connected session: clj
; TIPS:
;   - You can edit the contents here. Use it as a REPL if you like.
;   - `alt+enter` evaluates the current top level form.
;   - `ctrl+enter` evaluates the current form.
;   - `alt+up` and `alt+down` traverse up and down the REPL command history
;      when the cursor is after the last contents at the prompt
;   - Clojure lines in stack traces are peekable and clickable.
clj꞉user꞉> 
```

### Using Modules

We'll need to pull the modules from the remote repository. This is accomplished by running the following commmand in the REPL:

```clojure
clj꞉user꞉> (kit/sync-modules)
:done
```
If the command ran successfully then you should see a new `modules` folder in the project containing the modules that were downloaded and are now available for use.
Let's list the available modules:

```clojure
clj꞉user꞉> (kit/list-modules)
:kit/html - adds support for HTML templating using Selmer
:kit/htmx - adds support for HTMX using hiccup
:kit/ctmx - adds support for HTMX using CTMX
:kit/metrics - adds support for metrics using prometheus through iapetos
:kit/sente - adds support for Sente websockets to cljs
:kit/sql - adds support for SQL. Available profiles [ :postgres :sqlite ]. Default profile :sqlite
:kit/cljs - adds support for cljs using shadow-cljs
:kit/nrepl - adds support for nREPL
:done
```

Finallly, let's try starting the server to make sure our application is working.

```clojure
clj꞉user꞉> (go)
:initiated
```
Let's navigate to `http://localhost:3000/api/health` and see if we have some health check information returned by the server:

```javascript
{"time":"Fri Feb 10 13:54:36 EST 2023","up-since":"Wed Jan 18 22:53:21 EST 2023","app":{"status":"up","message":""}}
```

### CHECKPOINT 1

At this point you should have your project setup, are able to run and connect to the REPL, and run the web server successfully.

[Click here to continue on to Checkpoint 2](https://github.com/nikolap/kit-workshop/tree/checkpoint-2)
