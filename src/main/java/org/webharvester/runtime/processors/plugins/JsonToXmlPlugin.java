package org.webharvester.runtime.processors.plugins;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.webharvester.exception.PluginException;
import org.webharvester.runtime.Scraper;
import org.webharvester.runtime.ScraperContext;
import org.webharvester.runtime.processors.WebHarvestPlugin;
import org.webharvester.runtime.variables.NodeVariable;
import org.webharvester.runtime.variables.Variable;

/**
 * Converter from JSON to XML
 */
public class JsonToXmlPlugin extends WebHarvestPlugin {

    public String getName() {
        return "json-to-xml";
    }

    public Variable executePlugin(Scraper scraper, ScraperContext context) {
        Variable body = executeBody(scraper, context);
        try {
            JSONObject jsonObject = new JSONObject(body.toString());
            return new NodeVariable( XML.toString(jsonObject) );
        } catch (JSONException e) {
            throw new PluginException("Error converting JSON to XML: " + e.getMessage());
        }
    }

}