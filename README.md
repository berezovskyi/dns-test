Simple Java snippet test turned into madness
============================================


I was working on another project and wanted to see how easy it is to check if the domain exists (i.e. it's not just the server that is down now), but w/o doing all those WHOIS request to all the different registries.

I found http://www.xbill.org/dnsjava/dnsjava-current/examples.html and wanted to try it out. Let's run this Java snippet quickly. After all, Java development is my daily job. Right?

Wrong! To write a (proper) minimal Java program that takes one command-line arguments, I had to do the following:

1. Use `maven-archetype-quickstart`. Sets up the `pom.xml` properly and creates a class with a `main` method. I do not mention a ton of fields that need to be filled.
2. Add maven dependency for the library I want to play with. The most pleasant step of the this process.
3. Adjust the snippet to my needs. Nothing special here.
4. Try automating the execution of the `main` method with `exec-maven-plugin`. Partial success from the second try, but output of the program is surrounded my maven log.
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
