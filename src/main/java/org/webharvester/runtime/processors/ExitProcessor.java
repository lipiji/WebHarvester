package org.webharvester.runtime.processors;

import org.webharvester.definition.ExitDef;
import org.webharvester.runtime.Scraper;
import org.webharvester.runtime.ScraperContext;
import org.webharvester.runtime.templaters.BaseTemplater;
import org.webharvester.runtime.variables.EmptyVariable;
import org.webharvester.runtime.variables.Variable;
import org.webharvester.utils.CommonUtil;

/**
 * Exit processor.
 */
public class ExitProcessor extends BaseProcessor {

    private ExitDef exitDef;

    public ExitProcessor(ExitDef exitDef) {
        super(exitDef);
        this.exitDef = exitDef;
    }

    public Variable execute(Scraper scraper, ScraperContext context) {
        String condition = BaseTemplater.execute( exitDef.getCondition(), scraper.getScriptEngine());
        if ( condition == null || "".equals(condition) ) {
            condition = "true";
        }

        if (CommonUtil.isBooleanTrue(condition)) {
            String message = BaseTemplater.execute( exitDef.getMessage(), scraper.getScriptEngine());
            if (message == null) {
                message = "";
            }
            scraper.exitExecution(message);
            if ( scraper.getLogger().isInfoEnabled() ) {
                scraper.getLogger().info("Configuration exited: " + message);
            }
        }

        return new EmptyVariable();
    }

}
