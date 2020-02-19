package com.jelif.rmtwp.task


import org.gradle.api.DefaultTask
import groovy.io.FileType

abstract class CommonTask extends DefaultTask{
    final GET_TIFF_LINE_MATCHER = {it =~ /!\[.*]\((.*\.tiff)\)/}
    final UTF8 = "utf-8"

    void common(Closure handleMarkdownFile) {
        if (project.folderPath) {
            def processFolder = {File folder ->
                List<File> mdFiles = []
                folder.eachFile(FileType.FILES, { file ->
                    if (file.name ==~ /.*\.md$/) {
                        mdFiles.add(file)
                    }
                })
                int i = 1
                mdFiles.each {
                    logger.info("MARKDOWN FILE HANDLING[{}/{}]: {}", i, mdFiles.size(), it.name)
                    handleMarkdownFile(it, it.readLines())
                    i++
                }
            };
            processFolder(new File(project.folderPath))
        }
    }
}