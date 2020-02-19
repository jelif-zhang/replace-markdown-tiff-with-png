# replace-markdown-tiff-with-png

The purpose of this project is to replace tiff image links in markdown files with png ones. Of course, the source tiff files will be converted to png files too.

This project depends on Groovy programming language, Gradle build tool and ImageMagick image processing tool. To make it work, you need to make sure:

1. `gradle` command can be accessed via your command line tool
1. `magick` command can be accessed via your command line tool

Note that in default condition, this project WON'T update your markdown files or convert your tiff files to png. To make it do so, you need to change `build.gradle` who's in the root path of this project to open options for task `markdown` and `magick`.

To use this project, make this project as your working directory, and then
type:

`gradle markdown -i -PfolderPath=<your folder containg md files>`

`gradle magick -i -PfolderPath=<your folder containg md files>`

## known limitations

1. Won't process folder recursively
2. Assume tiff links are relative when generating ImageMagick commands 