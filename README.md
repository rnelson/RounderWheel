# RounderWheel

For my graduate distributed systems class, we had to work with a distributed system: DNS. Specifically, we 
were required to write a recursive DNS client that started at a random root server until it found the 
requested answer. Only a few record types were in scope.

[SSSilverLink](https://twitch.tv/SSSilverLink) is going through a bunch of [TryHackMe](https://tryhackme.com) 
stuff and one of the networking courses talked about how recursive DNS servers work. This reminded me of the 
assignment from years ago and made me grab the source so I could look at it, and I figured I might as well 
just upload it for others to laugh at.

## Details

This was written on Mac OS X 10.5 (Intel x64) in Java 5 with Ant 1.7 as the build system. It might work with 
newer stuff, but it may not.

## Usage

`ant dist` might build a jar for you? I haven't even attempted to build this, just looked through the code a 
bit.

But once you _somehow_ have a jar, you can run it as follows from the appropriate directory (`RounderWheel`?):

```shell
$ java -jar dist/RounderWheel.jar
C:\>google.com
```

The dumb prompt is because that was what the assignment provided, an oversight from the professor that I opted 
to implement as a requirement. You can, however, specify an argument for

```shell
$ java -jar dist/RounderWheel.jar "rw> "
rw> google.com
```