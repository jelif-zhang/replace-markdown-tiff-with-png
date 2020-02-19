package com.jelif.rmtwp.task

import org.gradle.api.tasks.TaskAction

class MarkdownTask extends CommonTask {
    def rewrite = false

    @TaskAction
    markdown () {
        def rewriteFile = { File file, List<String> lines ->
            file.withPrintWriter(UTF8, {writer ->
                lines.each {
                    writer.println(it)
                }
            })
        }
        def inParsing = { File file, List<String> lines ->
            def resultLines = []
            def flag = false
            lines.each { line ->
                if (GET_TIFF_LINE_MATCHER(line)) {
                    flag = true
                    resultLines.add(line.replaceAll(/\.tiff/, ".png"))
                } else {
                    resultLines.add(line)
                }
            }
            if (flag) {
                logger.info("TIFF USING DETECTED: {}", file.name)
                if (rewrite) {
                    logger.info("REWRITING FILE: {}", file.name)
                    rewriteFile(file, resultLines)
                } else {
                    logger.info("WON'T REWRITE")
                }
            }
        }
        common inParsing
    }
}