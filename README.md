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

It builds on FreeBSD 14.1/amd64 with OpenJDK 8 and Ant 1.10.

## Building

You can build a jar by simply running `ant` from the `RounderWheel` directory. The resulting jar file will be 
placed in `dist`.

## Usage

From within `RounderWheel`, you can run the jar file as follows:

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