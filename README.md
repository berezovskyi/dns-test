Simple Java snippet test turned into madness
============================================


I was working on another project and wanted to see how easy it is to check if the domain exists (i.e. it's not just the server that is down now), but w/o doing all those WHOIS request to all the different registries.

I found http://www.xbill.org/dnsjava/dnsjava-current/examples.html and wanted to try it out. Let's run this Java snippet quickly. After all, Java development is my daily job. Right?

Wrong! To write a (proper) minimal Java program that takes a single command-line argument, I had to do the following:

1. Use `maven-archetype-quickstart`. Sets up the `pom.xml` properly and creates a class with a `main` method. I do not mention a ton of fields that need to be filled.
2. Add maven dependency for the library I want to play with. The most pleasant step of the this process.
3. Adjust the snippet to my needs. Nothing special here.
4. Try automating the execution of the `main` method with `exec-maven-plugin`. Partial success from the second try, but program output is surrounded with maven log statements.
5. Try using `maven-jar-plugin` for the same purpose. Fail because the jar does not have the necessary classes on the classpath.
6. `maven-shade-plugin` was used to make a proper jar file that can be executed. Still, you need the java idiosyncrasy `java -jar target/xxx.jar`. Worse, you can't put that into README because it will break when the version changes.
7. Finally, we add the `appassembler-maven-plugin` plugin to the mix to produce binary wrappers.

P.S. I also had to look up a `.gitignore` file for Java, Maven, and Intellij. 

## Usage

    $ mvn clean package appassembler:assemble
    $ sh target/appassembler/bin/dns-test google.com
    Host google.com has nameserver ns2.google.com.
    Host google.com has nameserver ns1.google.com.
    Host google.com has nameserver ns4.google.com.
    Host google.com has nameserver ns3.google.com.
    $ sh target/appassembler/bin/dns-test dwvwdv3r.ed
    Host dwvwdv3r.ed has no nameservers


Or

    mvn clean compile exec:java -Dexec.args="facebook.com"
    [INFO] Scanning for projects...
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] Building dns-test 0.0.1-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO]
    [INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ dns-test ---
    [INFO] Deleting /Users/andrew/code/dns-test/target
    [INFO]
    [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ dns-test ---
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] skip non existing resourceDirectory /Users/andrew/code/dns-test/src/main/resources
    [INFO]
    [INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ dns-test ---
    [INFO] Changes detected - recompiling the module!
    [INFO] Compiling 1 source file to /Users/andrew/code/dns-test/target/classes
    [INFO]
    [INFO] --- exec-maven-plugin:1.5.0:java (default-cli) @ dns-test ---
    Host facebook.com has nameserver a.ns.facebook.com.
    Host facebook.com has nameserver b.ns.facebook.com.
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 1.952 s
    [INFO] Finished at: 2016-11-06T02:05:35+01:00
    [INFO] Final Memory: 15M/212M
    [INFO] ------------------------------------------------------------------------
