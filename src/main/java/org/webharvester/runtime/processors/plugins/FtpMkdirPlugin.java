package org.webharvester.runtime.processors.plugins;

import org.apache.commons.net.ftp.*;
import org.webharvester.runtime.*;
import org.webharvester.runtime.processors.*;
import org.webharvester.runtime.variables.*;
import org.webharvester.utils.*;

import java.io.*;

/**
 * Ftp Mkdir plugin - can be used only inside ftp plugin for creating directory on remote directory.
 */
public class FtpMkdirPlugin extends WebHarvestPlugin {

    public String getName() {
        return "ftp-mkdir";
    }

    public Variable executePlugin(Scraper scraper, ScraperContext context) {
        FtpPlugin ftpPlugin = (FtpPlugin) scraper.getRunningProcessorOfType(FtpPlugin.class);
        if (ftpPlugin != null) {
            FTPClient ftpClient = ftpPlugin.getFtpClient();

            String path = CommonUtil.nvl( evaluateAttribute("path", scraper), "" );

            setProperty("Path", path);

            try {
                boolean succ = ftpClient.makeDirectory(path);
                if (!succ) {
                    throw new FtpPluginException("Cannot create directory \"" + path + "\" on FTP server!");
                }
            } catch (IOException e) {
                throw new FtpPluginException(e);
            }
        } else {
            throw new FtpPluginException("Cannot use ftp mkdir plugin out of ftp plugin context!");
        }

        return new EmptyVariable();
    }

    public String[] getValidAttributes() {
        return new String[] {"path"};
    }

    public String[] getRequiredAttributes() {
        return new String[] {"path"};
    }

    public boolean hasBody() {
        return false;
    }

}