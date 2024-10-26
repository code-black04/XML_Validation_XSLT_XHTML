<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <!-- Output as HTML -->
    <xsl:output method="html" doctype-public="XSLT-compat"/>

    <!-- Template for the root element -->
    <xsl:template match="/service">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Styled Table</title>
                <link rel="stylesheet" type="text/css" href="src/main/resources/style.css"/>
            </head>
            <body>
                <table>
                    <!-- Table caption -->
                    <caption>
                        <xsl:value-of select="@id"/>
                    </caption>
                    <tr>
                        <th>Method(s)</th>
                        <th>Parameters(s)</th>
                        <th>Return</th>
                        <th>Number of Exception(s)</th>
                    </tr>

                    <!-- Iterate over each abstract_method -->
                    <xsl:for-each select="abstract_method">
                        <tr>
                            <!-- Method name -->
                            <td>
                                <xsl:value-of select="@name"/>
                            </td>

                            <!-- Parameters -->
                            <td>
                                <xsl:for-each select="arguments/parameter">
                                    <xsl:value-of select="."/>:
                                    <xsl:value-of select="@type"/>
                                    <xsl:if test="position() != last()">,
                                        <br/>
                                    </xsl:if>
                                </xsl:for-each>
                                <br/>
                            </td>

                            <!-- Return type -->
                            <td>
                                <xsl:value-of select="return"/>
                            </td>

                            <!-- Count exceptions -->
                            <td>
                                <xsl:value-of select="count(exceptions/exception)"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
