package com.example.mongodb.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * The type Mongo file utils.
 *
 * @author loquy
 */
@Component
public class MongoFileUtils {

    private static String database;

    /**
     * Sets database.
     *
     * @param database the database
     */
    @Value("${spring.data.mongodb.database}")
    public void setDatabase(String database) {
        MongoFileUtils.database = database;
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public static MongoDatabase getMongoDatabase() {
        MongoClient mongoClient = MongoClients.create();
        return mongoClient.getDatabase(database);
    }

    /**
     * Upload file to grid fs string.
     *
     * @param filename the filename
     * @param in       the in
     * @return the string
     * @throws Exception the exception
     */
    public static String uploadFileToGridFs(String filename, InputStream in) throws Exception {
        GridFSBucket bucket = GridFSBuckets.create(getMongoDatabase());
        ObjectId fileId = bucket.uploadFromStream(filename, in);
        in.close();
        return fileId.toHexString();
    }

    /**
     * Download to stream.
     *
     * @param objectId the object id
     * @param out      the out
     */
    public static void downloadToStream(String objectId, OutputStream out) {
        GridFSBucket bucket = GridFSBuckets.create(getMongoDatabase());
        bucket.downloadToStream(new ObjectId(objectId), out);
    }

    /**
     * Download file.
     *
     * @param objectId the object id
     * @param response the response
     * @throws Exception the exception
     */
    public static void downloadFile(String objectId, HttpServletResponse response) throws Exception {
        OutputStream os;
        String outFileName = findFileNameById(objectId);
        response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(outFileName, "UTF-8"));
        os = response.getOutputStream();
        downloadToStream(objectId, os);
        os.close();
    }

    /**
     * Show image.
     *
     * @param objectId the object id
     * @param response the response
     * @throws Exception the exception
     */
    public static void showImage(String objectId, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();
        downloadToStream(objectId, out);
        out.flush();
        out.close();
    }

    /**
     * Delete by object id.
     *
     * @param objectId the object id
     */
    public static void deleteByObjectId(String objectId) {
        GridFSBucket bucket = GridFSBuckets.create(getMongoDatabase());
        bucket.delete(new ObjectId(objectId));
    }

    /**
     * Find file name by id string.
     *
     * @param objectId the object id
     * @return the string
     */
    public static String findFileNameById(String objectId) {
        GridFSBucket bucket = GridFSBuckets.create(getMongoDatabase());
        GridFSDownloadStream stream;
        stream = bucket.openDownloadStream(new ObjectId(objectId));
        GridFSFile file = stream.getGridFSFile();
        return file.getFilename();
    }
}
