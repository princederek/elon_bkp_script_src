/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author princ
 */
public class Zip {
    
    public static void backUpFiles(final Path folder, final Path zipFilePath) throws IOException {
        try (
            FileOutputStream fos = new FileOutputStream(zipFilePath.toFile());
            ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(folder.relativize(file).toString()));
                Files.copy(file, zos);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(folder.relativize(dir).toString() + "/"));
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        }
    }
    
}
