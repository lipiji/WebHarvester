package org.webharvester.runtime.processors;

import org.webharvester.definition.BaseElementDef;
import org.webharvester.definition.IElementDef;
import org.webharvester.runtime.Scraper;
import org.webharvester.runtime.ScraperContext;
import org.webharvester.runtime.variables.*;

/**
 * Processor which executes only body and returns variables list.
 */
public class BodyProcessor extends BaseProcessor {

    public BodyProcessor(BaseElementDef elementDef) {
        super(elementDef);
    }

    public Variable execute(Scraper scraper, ScraperContext context) {
        IElementDef[] defs = elementDef.getOperationDefs();
        ListVariable result = new ListVariable();

        if (defs.length > 0) {
            for (int i = 0; i < defs.length; i++) {
                BaseProcessor processor = ProcessorResolver.createProcessor( defs[i], scraper.getConfiguration(), scraper );
                result.addVariable( processor.run(scraper, context) );
            }
        } else {
            result.addVariable( new NodeVariable(elementDef.getBodyText()) );
        }

        return result;
    }

}