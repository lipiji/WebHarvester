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
 * Converter from XML to JSON
 */
public class XmlToJsonPlugin extends WebHarvestPlugin {

    public String getName() {
        return "xml-to-json";
    }

    public Variable executePlugin(Scraper scraper, ScraperContext context) {
        Variable body = executeBody(scraper, context);
        try {
            JSONObject jsonObject = XML.toJSONObject(body.toString());
            return new NodeVariable(jsonObject.toString());
        } catch (JSONException e) {
            throw new PluginException("Error converting XML to JSON: " + e.getMessage());
        }
    }

}