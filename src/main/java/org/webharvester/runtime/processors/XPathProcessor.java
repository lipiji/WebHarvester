/*  Copyright (c) 2006-2007, Vladimir Nikic
    All rights reserved.

    Redistribution and use of this software in source and binary forms,
    with or without modification, are permitted provided that the following
    conditions are met:

    * Redistributions of source code must retain the above
      copyright notice, this list of conditions and the
      following disclaimer.

    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the
      following disclaimer in the documentation and/or other
      materials provided with the distribution.

    * The name of Web-Harvest may not be used to endorse or promote
      products derived from this software without specific prior
      written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
    AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
    IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
    ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
    LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
    CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
    SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
    INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
    CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
    ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.

    You can contact Vladimir Nikic by sending e-mail to
    nikic_vladimir@yahoo.com. Please include the word "Web-Harvest" in the
    subject line.
*/
package org.webharvester.runtime.processors;

import net.sf.saxon.trans.XPathException;

import org.webharvester.definition.XPathDef;
import org.webharvester.exception.ScraperXPathException;
import org.webharvester.runtime.RuntimeConfig;
import org.webharvester.runtime.Scraper;
import org.webharvester.runtime.ScraperContext;
import org.webharvester.runtime.templaters.BaseTemplater;
import org.webharvester.runtime.variables.Variable;
import org.webharvester.utils.XmlUtil;

/**
 * XQuery processor.
 */
public class XPathProcessor extends BaseProcessor {

    private XPathDef xpathDef;

    public XPathProcessor(XPathDef xpathDef) {
        super(xpathDef);
        this.xpathDef = xpathDef;
    }

    public Variable execute(Scraper scraper, ScraperContext context) {
        Variable xml = getBodyTextContent(xpathDef, scraper, context);
        String expression = BaseTemplater.execute( xpathDef.getExpression(), scraper.getScriptEngine() );
        this.setProperty("Expression", expression);

        try {
            RuntimeConfig runtimeConfig = scraper.getRuntimeConfig();
            return XmlUtil.evaluateXPath(expression, xml.toString(), runtimeConfig);
        } catch (XPathException e) {
            throw new ScraperXPathException("Error parsing XPath expression (XPath = [" + expression + "])!", e);
        }
    }

}