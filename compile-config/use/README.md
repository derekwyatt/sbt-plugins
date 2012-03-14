# "New Compiler Config" SBT Plugin Use #

Using the plugin requires that you have first published it locally.  If you
haven't then do what's discussed in `../plugin/`.  Assuming you did that, then
you can:

    sbt ~new:compile

Now in another terminal go start playing with the files in `src/new/watching`.
Say:

    cd src/new/watching
    touch first
    touch second
    touch twentieth

And observe the terminal that's running the `~new:compile`

You can also modify the Scala code and get the expected behaviour:

    echo "class Thing" >> src/main/scala/Hithere.scala

You should get a compile of that file.
