package org.webharvester.runtime.processors.plugins;

import org.webharvester.runtime.*;
import org.webharvester.runtime.processors.*;
import org.webharvester.runtime.variables.*;

import java.io.*;
import java.util.zip.*;

/**
 * ZIP processor
 */
public class ZipPlugin extends WebHarvestPlugin {

    private ZipOutputStream zipOutStream = null;

    public String getName() {
        return "zip";
    }

    public Variable executePlugin(Scraper scraper, ScraperContext context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        zipOutStream = new ZipOutputStream(byteArrayOutputStream);
        executeBody(scraper, context);
        try {
            zipOutStream.close();
        } catch (IOException e) {
            throw new ZipPluginException(e);
        }
        return new NodeVariable(byteArrayOutputStream.toByteArray());
    }

    public String[] getValidAttributes() {
        return new String[] {};
    }

    public String[] getRequiredAttributes() {
        return new String[] {};
    }

    public Class[] getDependantProcessors() {
        return new Class[] {
            ZipEntryPlugin.class
        };
    }

    public ZipOutputStream getZipOutStream() {
        return zipOutStream;
    }
    
}