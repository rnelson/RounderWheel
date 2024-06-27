# RounderWheel

For my graduate distributed systems class, we had to work with a distributed system: DNS. Specifically, we 
were required to write a recursive DNS client that started at a random root server until it found the 
requested answer. Only a few record types were in scope.

[SSSilverLink](https://twitch.tv/SSSilverLink) is going through a bunch of [TryHackMe](https://tryhackme.com) 
stuff and one of the networking courses talked about how recursive DNS servers work. This reminded me of the 
assignment from years ago and made me grab the source so I could look at it, and I figured I might as well 
just upload it for others to laugh at.

## Details

RounderWheel requires at least Java 8 (1.8). It has been tested on FreeBSD 14.1/amd64 with OpenJDK 8, 
building with Ant 1.10.

As of 2024, it does not function. I'm not sure when it broke and have not diagnosed what is broken.

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