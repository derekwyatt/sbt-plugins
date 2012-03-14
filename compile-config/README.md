# "New Compiler Config" SBT Plugin #

The idea here is to create a new "thing" that extends the compile.  The goal of
this plugin is to create a new compile operation that allow us to do the
following:

    > new:compile

or

    > ~new:compile

When we do either of those, we want the 'standard' compile to run first and then
our new compile.  If we do `compile:compile` (or just `compile`) then our 'new'
compile shouldn't run.

## plugin/ ##

The `plugin/` directory contains the SBT plugin.  Go there and read the
README.md file.

## use/ ##

The `use/` directory contains the example usage.  Go there and read the
README.md file.
