# Usage

This file contains a listing of possible future changes for the plugin.  A given change being listed does not mean that the change will be made in a particular time frame, or even that it will ever be made.  Changes are separated into 3 groups based loosely on [GTD].

## Now

* Update documentation to recommend installation in "gradle" directory used by wrapper.

Items in this group are either under active development, or will likely be under active development soon.  Most items in this list should make it into the next feature release.

## Soon

Items in this group are not under active development, but are intended to be included in one of the next two feature releases.

## Maybe Someday

Items in this group are not under active development, and it's unclear of when (if ever) they will be.

* Support for some easier syntax to declare Bintray repositories for uploading/publishing
    * This will likely not happen until Gradle 1.5 is released, as it looks like it will be a lot easier to integrate with the `maven-publish` plugin rather than the `maven` plugin.
* Inclusion of the plugin (or similar functionality) with the main Gradle distribution

[gtd]: http://www.davidco.com/about-gtd
