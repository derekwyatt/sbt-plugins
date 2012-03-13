# "Watch Sources" SBT Plugin Use #

Using the plugin requires that you have first published it locally.  If you
haven't then do what's discussed in `../plugin/`.  Assuming you did that, then
you can:

    sbt ~compile

Now in another terminal go start playing with the files in `src/watching`.  Say:

    cd src/watching
    touch first
    touch second
    touch twentieth

And observe the terminal that's running the `~compile`
