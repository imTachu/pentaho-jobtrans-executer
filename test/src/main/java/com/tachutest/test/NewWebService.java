/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tachutest.test;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.Job;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.Trans;

@Path("/pentaho")
public class NewWebService {

    @POST
    @Path("/postTransformacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postTransformacion(String p) {
        long g = System.currentTimeMillis();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File jobFile = new File(classLoader.getResource("car.ktr").getFile());
            
            KettleEnvironment.init();
            TransMeta transMeta = new TransMeta(jobFile.getPath());
            Trans trans = new Trans(transMeta);
            trans.execute(null);
        } catch (KettleException ex) {
            Logger.getLogger(NewWebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - g));
        return "transformation executed";
    }

    @POST
    @Path("/postJob")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postJob(String p) {
        long g = System.currentTimeMillis();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File jobFile = new File(classLoader.getResource("eljob.kjb").getFile());
            
            KettleEnvironment.init();
            JobMeta jobMeta = new JobMeta(jobFile.getPath(), null, null);
            Job job = new Job(null, jobMeta);
            job.start();
        } catch (KettleException ex) {
            Logger.getLogger(NewWebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - g));
        return "job executed";
    }
}
