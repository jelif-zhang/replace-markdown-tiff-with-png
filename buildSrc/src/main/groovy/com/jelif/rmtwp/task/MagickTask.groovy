package com.jelif.rmtwp.task


import org.gradle.api.tasks.TaskAction

class MagickTask extends CommonTask {
    def execute = false

    @TaskAction
    magick () {
        if (project.folderPath) {
            List<String> commands = []
            def genMagickCommand = { String tiffPath ->
                /magick convert "${tiffPath}" "${tiffPath.replaceAll(/\.tiff/, '.png')}"/
            }
            def inParsing = { File file, List<String> lines ->
                lines.each { line ->
                    def matcher = GET_TIFF_LINE_MATCHER line
                    if (matcher) {
                        def parentPath = file.getParent().replaceAll($/\\/$, '/')
                        def command = genMagickCommand("${parentPath}/${URLDecoder.decode(matcher.group(1), UTF8)}")
                        commands.add(command)
                    }
                }
            }
            logger.info("PARSING BEGIN")
            common inParsing
            logger.info("PARSING END")
            logger.info("{} COMMANDS GENERATED", commands.size())
            if (execute) {
                int i = 1
                commands.each {
                    logger.info("COMMAND EXECUTING[{}/{}]: {}", i, commands.size(), it)
                    def process = it.execute();
                    process.waitFor()
                    if (!process.exitValue()) {
                        logger.info("COMMAND EXECUTED SUCCESSFULLY")
                    } else {
                        logger.error("COMMAND EXECUTION FAILED: {}", process.err.text)
                    }
                    i++
                }
            } else {
                logger.info("COMMANDS WON'T BE EXECUTED")
            }
        }
    }
}
