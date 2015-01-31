package org.pictoss;

import java.io.*;
import java.util.*;
import com.google.common.io.ByteStreams;
import static spark.Spark.*;

public class FileServlet {
    public static final String IMAGE_PATH = "images";

    public static void main(String[] args) {
        String relpath = System.getProperty("user.dir") + "/images";
        System.out.println(relpath);

        externalStaticFileLocation(relpath);

        post("/upload/:name", (request, response) -> {
            System.out.println(request.params(":name"));
            String name = request.params(":name");

            try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File(relpath + "/" + name), "rw")) {
                //String file = request.raw().getPart("file").toString();
                //ByteStreams.toByteArray(request.raw().getReader());
                //return s.hasNext() ? s.next() : "";
                randomAccessFile.write(ByteStreams.toByteArray(request.raw().getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Okay";
        });
    }
}
