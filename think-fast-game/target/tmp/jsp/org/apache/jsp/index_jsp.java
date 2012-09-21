package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Think Fast Game</title>\n");
      out.write("        <script type=\"text/javascript\" src=\"resources/js/jquery-1.7.2.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"resources/js/knockout-2.0.0.js\"></script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Think Fast Game</h1>\n");
      out.write("        <div id=\"participant\">\n");
      out.write("            <h2>Insira seu nome e clique no botão start para iniciar:</h2>\n");
      out.write("            <input type=\"text\" name=\"participant\" />\n");
      out.write("            <input type=\"button\" value=\"start\" data-bind=\"click: play\" />\n");
      out.write("        </div>\n");
      out.write("        <br/>\n");
      out.write("        <div id=\"survey\">\n");
      out.write("            <span data-bind=\"text: question\">Qual a capital da Rússia?</span>\n");
      out.write("            <ul data-bind=\"foreach: answers\">\n");
      out.write("                <li style=\"list-style: none;\">\n");
      out.write("                    <input type=\"radio\" name=\"answer\" data-bind=\"click: $root.answer\"/>\n");
      out.write("                    <span data-bind=\"text: $data\">Moscou</span>\n");
      out.write("                </li>\n");
      out.write("            </ul>\n");
      out.write("            <span id=\"message\" data-bind=\"text: message\"></span>\n");
      out.write("        </div>\n");
      out.write("        <script>\n");
      out.write("            var ThinkFast = function() {\n");
      out.write("                var self = this;\n");
      out.write("                self.participant = ko.observable();\n");
      out.write("                self.question = ko.observable();\n");
      out.write("                self.answers = ko.observableArray([]);\n");
      out.write("                self.message = ko.observable();\n");
      out.write("\n");
      out.write("\t\t\t\n");
      out.write("                self.play = function() {\n");
      out.write("                    $.getJSON(\"/thinkfast\", {action: \"play\", name: self.participant() }, function(data){\n");
      out.write("                        self.parseResult(data);\n");
      out.write("                        self.bind();\n");
      out.write("                    });\n");
      out.write("                }\n");
      out.write("                self.bind = function() {\n");
      out.write("                    $.getJSON(\"/thinkfast\", {action: \"bind\" }, function(data){\n");
      out.write("                        self.parseResult(data);\n");
      out.write("                    }).complete(function() { /*complete equivale a um finally*/\n");
      out.write("                        self.bind();\n");
      out.write("                    });                    \n");
      out.write("                }\n");
      out.write("                self.answer = function(answer) {\n");
      out.write("                    $.getJSON(\"/thinkfast\", {action: \"answer\", answer: answer }, function(data){\n");
      out.write("                        self.parseResult(data);\n");
      out.write("                    });                    \n");
      out.write("                }\n");
      out.write("                self.parseResult = function( data ) {\n");
      out.write("                    if ( data.question ){\n");
      out.write("                        self.question(data.question.description);\n");
      out.write("                        self.answers.removeAll();\n");
      out.write("                        $.map(data.answers, function(answer){\n");
      out.write("                            self.answers.push(answer);\n");
      out.write("                        });                        \n");
      out.write("                    }\n");
      out.write("                    self.message( data.message );\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            ko.applyBindings(new ThinkFast());\n");
      out.write("        </script>\n");
      out.write("    </body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
