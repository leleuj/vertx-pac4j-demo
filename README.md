vertx-pac4j-demo
================

To build the demo, you first need to install in your local repository the [vertx-pac4j binding](https://github.com/pac4j/vertx-pac4j).

After, just run in this demo project:
<pre><code>mvn install</code></pre>

You can then run the stateful demo from maven with:
<pre><code>mvn vertx:runMod</code></pre>

or run the stateless demo:
<pre><code>mvn vertx:runMod -Prest</code></pre>

If you have vert.x installed, run the module from the repository:
<pre><code>vertx runMod org.pac4j~vertx-pac4j-demo~1.0.0</code></pre>
